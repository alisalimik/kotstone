package ca.moheektech.capstone.internal.platform

import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.InternalInstruction
import ca.moheektech.capstone.api.DisassemblyPosition
import ca.moheektech.capstone.arch.*
import ca.moheektech.capstone.bit.BitField
import ca.moheektech.capstone.enums.*
import ca.moheektech.capstone.error.*
import ca.moheektech.capstone.exp.aarch64.*
import ca.moheektech.capstone.exp.arm.*
import ca.moheektech.capstone.exp.toArmConditionCode
import ca.moheektech.capstone.exp.toArmCpsFlagType
import ca.moheektech.capstone.exp.toArmCpsModeType
import ca.moheektech.capstone.exp.toArmMemoryBarrierOption
import ca.moheektech.capstone.exp.toArmOpType
import ca.moheektech.capstone.exp.toArmSetEndType
import ca.moheektech.capstone.exp.toArmShifter
import ca.moheektech.capstone.exp.toArmVectorDataType
import ca.moheektech.capstone.exp.x86.X86AvxBroadcast
import ca.moheektech.capstone.exp.x86.X86AvxConditionCode
import ca.moheektech.capstone.exp.x86.X86AvxRoundingMode
import ca.moheektech.capstone.exp.x86.X86OpType
import ca.moheektech.capstone.exp.x86.X86Prefix
import ca.moheektech.capstone.exp.x86.X86SseConditionCode
import ca.moheektech.capstone.internal.*
import ca.moheektech.capstone.model.*
import kotlinx.cinterop.*
import platform.posix.size_t
import platform.posix.size_tVar

/**
 * Native platform implementation using Kotlin/Native cinterop.
 *
 * This implementation directly calls Capstone C API via cinterop bindings.
 */
@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
internal actual fun createPlatformBinding(
    architecture: Architecture,
    mode: BitField<Mode>
): CapstoneBinding = NativeCapstoneBinding(architecture, mode)

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
internal actual fun getPlatformVersion(): Pair<Int, Int> {
  memScoped {
    val major = alloc<IntVar>()
    val minor = alloc<IntVar>()
    cs_version(major.ptr, minor.ptr)
    return Pair(major.value, minor.value)
  }
}

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
internal actual fun isPlatformSupported(arch: Architecture): Boolean {
  return cs_support(arch.value)
}

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
internal class NativeCapstoneBinding(
    private val architecture: Architecture,
    private val mode: BitField<Mode>
) : CapstoneBinding {

  private val handle: csh
  private var detailEnabled = false

  init {
    memScoped {
      val handlePtr = alloc<cshVar>()
      val err = cs_open(architecture.value.convert(), mode.value.convert(), handlePtr.ptr)
      if (err != CS_ERR_OK) {
        val errorMsg = cs_strerror(err)?.toKString() ?: "Unknown error"
        throw CapstoneError.UnsupportedArchitecture(
            architecture, "Failed to open Capstone: $errorMsg")
      }
      handle = handlePtr.value
    }
  }

  override fun disasm(
      code: ByteArray,
      address: Long,
      count: Int
  ): CapstoneResult<List<Instruction>> = runCatching {
    // Handle empty code array
    if (code.isEmpty()) {
      return@runCatching emptyList()
    }

    memScoped {
      val insnPtrPtr = alloc<CPointerVar<cs_insn>>()

      code.usePinned { pinnedCode ->
        val disasmCount =
            cs_disasm(
                handle,
                pinnedCode.addressOf(0).reinterpret(),
                code.size.convert(),
                address.convert(),
                count.convert(),
                insnPtrPtr.ptr)

        if (disasmCount == 0.convert<size_t>()) {
          val errno = cs_errno(handle)
          if (errno != CS_ERR_OK) {
            throw ErrorCode.fromValue(errno.convert()).toError(architecture, mode)
          }
          emptyList()
        } else {
          try {
            val instructions = buildList {
              val insnArray = insnPtrPtr.value!!
              for (i in 0 until disasmCount.toInt()) {
                val insn = insnArray[i]
                add(convertInstruction(insn))
              }
            }
            instructions
          } finally {
            cs_free(insnPtrPtr.value, disasmCount)
          }
        }
      }
    }
  }

  override fun disasmIter(
      code: ByteArray,
      position: DisassemblyPosition
  ): CapstoneResult<Instruction?> = runCatching {
    if (!position.hasRemaining(code.size)) {
      return@runCatching null
    }

    memScoped {
      val insn = cs_malloc(handle) ?: throw CapstoneError.OutOfMemory()

      try {
        code.usePinned { pinnedCode ->
          val codePtr = pinnedCode.addressOf(position.offset).reinterpret<UByteVar>()
          val size = position.remaining(code.size).convert<size_t>()
          val addr = position.address.convert<ULong>()

          val codePtrVar = alloc<CPointerVar<UByteVar>>()
          val sizeVar = alloc<size_tVar>()
          val addrVar = alloc<ULongVar>()

          codePtrVar.value = codePtr
          sizeVar.value = size
          addrVar.value = addr

          val result = cs_disasm_iter(handle, codePtrVar.ptr, sizeVar.ptr, addrVar.ptr, insn)

          if (result) {
            // Update position
            val insnSize = insn.pointed.size.toInt()
            position.advance(insnSize)

            // Convert and return instruction
            convertInstruction(insn.pointed)
          } else {
            null
          }
        }
      } finally {
        cs_free(insn, 1.convert())
      }
    }
  }

  override fun setOption(option: CapstoneOption): CapstoneResult<Unit> = runCatching {
    val err = cs_option(handle, option.optType.convert(), option.optValue.convert())

    if (err != CS_ERR_OK) {
      throw ErrorCode.fromValue(err.convert()).toError(architecture, mode)
    }

    // Track detail mode
    if (option is CapstoneOption.Detail) {
      detailEnabled = option.enabled
    }
  }

  override fun regName(regId: Int): String? {
    return cs_reg_name(handle, regId.convert())?.toKString()
  }

  override fun insnName(insnId: Int): String? {
    return cs_insn_name(handle, insnId.convert())?.toKString()
  }

  override fun groupName(groupId: Int): String? {
    return cs_group_name(handle, groupId.convert())?.toKString()
  }

  override fun lastError(): ErrorCode {
    val err = cs_errno(handle)
    return ErrorCode.fromValue(err.convert())
  }

  override fun close() {
    val _ = memScoped {
      val handlePtr = alloc<cshVar>()
      handlePtr.value = handle
      cs_close(handlePtr.ptr)
    }
  }

  /** Convert C cs_insn struct to Kotlin Instruction object */
  private fun convertInstruction(insn: cs_insn): Instruction {
    val id = insn.id.toInt()
    val address = insn.address.toLong()
    val size = insn.size.toInt()

    // Copy instruction bytes
    val bytes = ByteArray(size) { i -> insn.bytes[i].convert() }

    // Get mnemonic and operand string
    val mnemonic = insn.mnemonic.toKString()
    val opStr = insn.op_str.toKString()

    // Parse detail if available and enabled
    val detail =
        if (detailEnabled && insn.detail != null) {
          convertDetail(insn.detail!!.pointed)
        } else {
          null
        }

    return InternalInstruction(
        id = id,
        address = address,
        size = size,
        bytes = bytes,
        mnemonic = mnemonic,
        opStr = opStr,
        detail = detail)
  }

  /** Convert C cs_detail struct to Kotlin InstructionDetail object */
  private fun convertDetail(detail: cs_detail): InstructionDetail {
    // Parse registers read
    val regsRead = buildList {
      for (i in 0 until detail.regs_read_count.toInt()) {
        val regId = detail.regs_read[i].toInt()
        val regName = cs_reg_name(handle, regId.convert())?.toKString()
        add(Register(regId, regName))
      }
    }

    // Parse registers written
    val regsWritten = buildList {
      for (i in 0 until detail.regs_write_count.toInt()) {
        val regId = detail.regs_write[i].toInt()
        val regName = cs_reg_name(handle, regId.convert())?.toKString()
        add(Register(regId, regName))
      }
    }

    // Parse groups
    val groups = buildList {
      for (i in 0 until detail.groups_count.toInt()) {
        val groupId = detail.groups[i].toInt()
        InstructionGroup.fromValue(groupId)?.let { add(it) }
      }
    }

    // Parse architecture-specific details
    val archDetail = convertArchDetail(detail)

    return InstructionDetail(
        regsRead = regsRead,
        regsWritten = regsWritten,
        groups = groups,
        writeback = detail.writeback,
        archDetail = archDetail)
  }

  /** Convert architecture-specific details from the union */
  private fun convertArchDetail(detail: cs_detail): ArchDetail? {
    return when (architecture) {
      Architecture.ARM64 -> {
        val aarch64 = detail.aarch64
        ArchDetail.AArch64(convertAArch64Detail(aarch64))
      }
      Architecture.X86 -> {
        val x86 = detail.x86
        ArchDetail.X86(convertX86Detail(x86))
      }
      Architecture.ARM -> {
        val arm = detail.arm
        ArchDetail.ARM(convertArmDetail(arm))
      }
      else -> null // Generic for other architectures
    }
  }

  private fun convertArmDetail(arm: cs_arm): ArmInstructionDetail {
    val operands = buildList {
      for (i in 0 until arm.op_count.toInt()) {
        val op = arm.operands[i]
        val type = op.type.toInt().toArmOpType()

        var reg: Register? = null
        var imm: Int? = null
        var fp: Double? = null
        var mem: ArmMemoryOperand? = null
        var setend = ArmSetEndType.INVALID

        when (type) {
          ArmOpType.REG,
          ArmOpType.SYSREG,
          ArmOpType.BANKEDREG -> {
            val regId = op.reg
            val regName = cs_reg_name(handle, regId.convert())?.toKString()
            reg = Register(regId, regName)
          }
          ArmOpType.IMM,
          ArmOpType.CIMM,
          ArmOpType.PIMM -> {
            imm = op.imm.toInt()
          }
          ArmOpType.FP -> {
            fp = op.fp
          }
          ArmOpType.MEM -> {
            val m = op.mem
            val baseRegId = m.base
            val baseReg =
                if (baseRegId != 0u) {
                  Register(baseRegId.toInt(), cs_reg_name(handle, baseRegId.convert())?.toKString())
                } else null

            val indexRegId = m.index
            val indexReg =
                if (indexRegId != 0u) {
                  Register(
                      indexRegId.toInt(), cs_reg_name(handle, indexRegId.convert())?.toKString())
                } else null

            mem =
                ArmMemoryOperand(
                    base = baseReg,
                    index = indexReg,
                    scale = m.scale,
                    disp = m.disp,
                    lshift = 0 // m.lshift // Unresolved?
                    )
          }
          ArmOpType.SETEND -> {
            setend = op.setend.toInt().toArmSetEndType()
          }
          else -> {}
        }

        add(
            ArmOperand(
                type = type,
                vectorIndex = op.vector_index,
                shiftType = op.shift.type.toInt().toArmShifter(),
                shiftValue = op.shift.value.toInt(),
                reg = reg,
                imm = imm,
                fp = fp,
                mem = mem,
                setend = setend,
                subtracted = op.subtracted,
                access = AccessType.fromValue(op.access.toInt()),
                neonLane = op.neon_lane))
      }
    }

    return ArmInstructionDetail(
        usermode = arm.usermode,
        vectorSize = arm.vector_size,
        vectorData = arm.vector_data.convert<Int>().toArmVectorDataType(),
        cpsMode = arm.cps_mode.convert<Int>().toArmCpsModeType(),
        cpsFlag = arm.cps_flag.convert<Int>().toArmCpsFlagType(),
        cc = arm.cc.convert<Int>().toArmConditionCode(),
        updateFlags = arm.update_flags,
        writeback = false, // arm.writeback, // Unresolved?
        memBarrier = (arm.mem_barrier.value.toInt()).toArmMemoryBarrierOption(),
        operands = operands)
  }

  /** Convert AArch64-specific details */
  private fun convertAArch64Detail(aarch64: cs_aarch64): AArch64InstructionDetail {
    val operands = buildList {
      for (i in 0 until aarch64.op_count.toInt()) {
        val op = aarch64.operands[i]
        val typeRaw = op.type.toInt()
        val type =
            AArch64OpType.entries.firstOrNull { it.value.toInt() == typeRaw }
                ?: AArch64OpType.INVALID

        var reg: Register? = null
        var imm: Long? = null
        var fp: Double? = null
        var mem: AArch64MemoryOperand? = null
        val barrier: AArch64Db? = null
        val prefetch: AArch64Prfm? = null

        when (type) {
          AArch64OpType.REG,
          AArch64OpType.REG_MRS,
          AArch64OpType.REG_MSR -> {
            val regId = op.reg
            val regName = cs_reg_name(handle, regId.convert())?.toKString()
            reg = Register(regId.toInt(), regName)
          }
          AArch64OpType.IMM,
          AArch64OpType.CIMM -> {
            imm = op.imm
          }
          AArch64OpType.FP -> {
            fp = op.fp
          }
          AArch64OpType.MEM -> {
            val m = op.mem
            val baseRegId = m.base
            val baseReg =
                if (baseRegId != 0u) {
                  Register(baseRegId.toInt(), cs_reg_name(handle, baseRegId.convert())?.toKString())
                } else null

            val indexRegId = m.index
            val indexReg =
                if (indexRegId != 0u) {
                  Register(
                      indexRegId.toInt(), cs_reg_name(handle, indexRegId.convert())?.toKString())
                } else null

            mem = AArch64MemoryOperand(base = baseReg, index = indexReg, disp = m.disp.toLong())
          }
          AArch64OpType.DB -> {
            // val barrierRaw = op.barrier.toInt()
            // barrier = AArch64Db.entries.firstOrNull { it.value.toInt() == barrierRaw }
          }
          AArch64OpType.PRFM -> {
            // val prfmRaw = op.prefetch.toInt()
            // prefetch = AArch64Prfm.entries.firstOrNull { it.value.toInt() == prfmRaw }
          }
          else -> {}
        }

        add(
            AArch64Operand(
                type = type,
                access = AccessType.fromValue(op.access.toInt()),
                vectorIndex = op.vector_index,
                vas =
                    AArch64VectorLayout.entries.firstOrNull { it.value.toInt() == op.vas.toInt() }
                        ?: AArch64VectorLayout.INVALID,
                shifter =
                    AArch64Shifter.entries.firstOrNull { it.value.toInt() == op.shift.type.toInt() }
                        ?: AArch64Shifter.INVALID,
                shiftValue = op.shift.value.toInt(),
                extender =
                    AArch64Extender.entries.firstOrNull { it.value.toInt() == op.ext.toInt() }
                        ?: AArch64Extender.INVALID,
                reg = reg,
                imm = imm,
                fp = fp,
                mem = mem,
                barrier = barrier,
                prefetch = prefetch,
                isListMember = false // TODO: How to determine logical register list member?
                ))
      }
    }

    return AArch64InstructionDetail(
        cc =
            AArch64ConditionCode.entries.firstOrNull {
              it.value.toInt() == aarch64.cc.convert<Int>()
            } ?: AArch64ConditionCode.Invalid,
        updateFlags = aarch64.update_flags,
        writeback = aarch64.post_index,
        postIndex = aarch64.post_index,
        operands = operands)
  }

  /** Convert X86-specific details */
  private fun convertX86Detail(x86: cs_x86): X86InstructionDetail {
    val prefix = buildList {
      for (i in 0 until 4) {
        if (x86.prefix[i].toInt() != 0) {
          add(X86Prefix.fromValue(x86.prefix[i].toInt()))
        }
      }
    }

    val operands = buildList {
      for (i in 0 until x86.op_count.toInt()) {
        val op = x86.operands[i]
        val type = X86OpType.fromValue(op.type.toInt())

        var reg: Register? = null
        var imm: Long? = null
        var mem: X86MemoryOperand? = null

        when (type) {
          X86OpType.REG -> {
            val regId = op.reg
            val regName = cs_reg_name(handle, regId.convert())?.toKString()
            reg = Register(regId.toInt(), regName)
          }
          X86OpType.IMM -> {
            imm = op.imm
          }
          X86OpType.MEM -> {
            val m = op.mem
            val baseRegId = m.base
            val baseReg =
                if (baseRegId != 0u) {
                  Register(baseRegId.toInt(), cs_reg_name(handle, baseRegId.convert())?.toKString())
                } else null

            val indexRegId = m.index
            val indexReg =
                if (indexRegId != 0u) {
                  Register(
                      indexRegId.toInt(), cs_reg_name(handle, indexRegId.convert())?.toKString())
                } else null

            val segmentRegId = m.segment
            val segmentReg =
                if (segmentRegId != 0u) {
                  Register(
                      segmentRegId.toInt(),
                      cs_reg_name(handle, segmentRegId.convert())?.toKString())
                } else null

            mem =
                X86MemoryOperand(
                    segment = segmentReg,
                    base = baseReg,
                    index = indexReg,
                    scale = m.scale,
                    disp = m.disp)
          }
          else -> {}
        }

        add(
            X86Operand(
                type = type,
                access = AccessType.fromValue(op.access.toInt()),
                size = op.size.toInt(),
                reg = reg,
                imm = imm,
                mem = mem,
                avxBcast = X86AvxBroadcast.fromValue(op.avx_bcast.toInt()),
                avxZeroOpmask = op.avx_zero_opmask))
      }
    }

    return X86InstructionDetail(
        prefix = prefix,
        opcode = ByteArray(4) { i -> x86.opcode[i].toByte() },
        rex = x86.rex.toByte(),
        addrSize = x86.addr_size.toInt(),
        modrm = x86.modrm.toByte(),
        sib = x86.sib.toByte(),
        disp = x86.disp,
        sibIndex =
            if (x86.sib_index != 0u)
                Register(
                    x86.sib_index.toInt(),
                    cs_reg_name(handle, x86.sib_index.convert())?.toKString())
            else null,
        sibScale = x86.sib_scale.toInt(),
        sibBase =
            if (x86.sib_base != 0u)
                Register(
                    x86.sib_base.toInt(), cs_reg_name(handle, x86.sib_base.convert())?.toKString())
            else null,
        operands = operands,
        avxCC = X86AvxConditionCode.fromValue(x86.avx_cc.convert()),
        sseCC = X86SseConditionCode.fromValue(x86.sse_cc.convert()),
        avxRm = X86AvxRoundingMode.fromValue(x86.avx_rm.convert()),
        avxSae = x86.avx_sae,
        eflags = BitField.fromValue(x86.eflags),
        fpuFlags = x86.fpu_flags.convert())
  }
}
