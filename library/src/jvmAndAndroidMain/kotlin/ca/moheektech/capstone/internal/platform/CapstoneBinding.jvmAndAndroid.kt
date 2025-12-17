package ca.moheektech.capstone.internal.platform

import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.InternalInstruction
import ca.moheektech.capstone.api.DisassemblyPosition
import ca.moheektech.capstone.arch.AArch64InstructionDetail
import ca.moheektech.capstone.arch.AArch64MemoryOperand
import ca.moheektech.capstone.arch.AArch64Operand
import ca.moheektech.capstone.arch.ArchDetail
import ca.moheektech.capstone.arch.ArmInstructionDetail
import ca.moheektech.capstone.arch.ArmMemoryOperand
import ca.moheektech.capstone.arch.ArmOperand
import ca.moheektech.capstone.arch.X86InstructionDetail
import ca.moheektech.capstone.arch.X86MemoryOperand
import ca.moheektech.capstone.arch.X86Operand
import ca.moheektech.capstone.bit.BitField
import ca.moheektech.capstone.enums.AccessType
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.CapstoneOption
import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.enums.Mode
import ca.moheektech.capstone.error.CapstoneError
import ca.moheektech.capstone.error.CapstoneResult
import ca.moheektech.capstone.error.ErrorCode
import ca.moheektech.capstone.error.toError
import ca.moheektech.capstone.exp.aarch64.AArch64ConditionCode
import ca.moheektech.capstone.exp.aarch64.AArch64Extender
import ca.moheektech.capstone.exp.aarch64.AArch64OpType
import ca.moheektech.capstone.exp.aarch64.AArch64Shifter
import ca.moheektech.capstone.exp.aarch64.AArch64VectorLayout
import ca.moheektech.capstone.exp.arm.ArmOpType
import ca.moheektech.capstone.exp.arm.ArmSetEndType
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
import ca.moheektech.capstone.model.InstructionDetail
import ca.moheektech.capstone.model.Register
import com.sun.jna.Memory
import com.sun.jna.NativeLong
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.LongByReference
import com.sun.jna.ptr.NativeLongByReference
import com.sun.jna.ptr.PointerByReference

/**
 * JNA-based platform implementation for JVM and Android.
 *
 * This implementation uses JNA to call Capstone C API via native library loading.
 */
internal actual fun createPlatformBinding(
    architecture: Architecture,
    mode: BitField<Mode>
): CapstoneBinding = JnaCapstoneBinding(architecture, mode)

internal actual fun getPlatformVersion(): Pair<Int, Int> {
  val major = IntByReference()
  val minor = IntByReference()
  CapstoneLibrary.INSTANCE.cs_version(major, minor)
  return Pair(major.value, minor.value)
}

internal actual fun isPlatformSupported(arch: Architecture): Boolean {
  return CapstoneLibrary.INSTANCE.cs_support(arch.value)
}

/** JNA-based Capstone binding implementation */
internal class JnaCapstoneBinding(
    private val architecture: Architecture,
    private val mode: BitField<Mode>
) : CapstoneBinding {

  private val handleRef: PointerByReference = PointerByReference()
  private val handle: Pointer
  private var detailEnabled = false

  init {
    val err = CapstoneLibrary.INSTANCE.cs_open(architecture.value, mode.value.toInt(), handleRef)
    if (err != ErrorCode.OK.value) {
      val errorMsg = CapstoneLibrary.INSTANCE.cs_strerror(err) ?: "Unknown error"
      throw CapstoneError.UnsupportedArchitecture(
          architecture, "Failed to open Capstone: $errorMsg")
    }
    handle = handleRef.value
  }

  override fun disasm(
      code: ByteArray,
      address: Long,
      count: Int
  ): CapstoneResult<List<Instruction>> = runCatching {
    val insnPtrRef = PointerByReference()

    val disasmCount =
        CapstoneLibrary.INSTANCE.cs_disasm(
            handle,
            code,
            NativeLong(code.size.toLong()),
            address,
            NativeLong(count.toLong()),
            insnPtrRef)

    if (disasmCount.toLong() == 0L) {
      val errno = CapstoneLibrary.INSTANCE.cs_errno(handle)
      if (errno != ErrorCode.OK.value) {
        throw ErrorCode.fromValue(errno).toError(architecture, mode)
      }
      emptyList()
    } else {
      try {
        val instructions = mutableListOf<Instruction>()
        val insnPtr = insnPtrRef.value
        val insnSize = CsInsn().size()

        for (i in 0 until disasmCount.toInt()) {
          val insn = CsInsn(insnPtr.share((i * insnSize).toLong()))
          instructions.add(convertInstruction(insn))
        }
        instructions
      } finally {
        CapstoneLibrary.INSTANCE.cs_free(insnPtrRef.value, disasmCount)
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

    val insn = CapstoneLibrary.INSTANCE.cs_malloc(handle) ?: throw CapstoneError.OutOfMemory()

    try {
      // Create a memory buffer with the code
      val codeMem = Memory(code.size.toLong())
      codeMem.write(0, code, 0, code.size)

      // Create pointer to current position in code
      val currentCodePtr = codeMem.share(position.offset.toLong())
      val codePtrRef = PointerByReference(currentCodePtr)

      val sizeRef = NativeLongByReference(NativeLong(position.remaining(code.size).toLong()))
      val addrRef = LongByReference(position.address)

      val result =
          CapstoneLibrary.INSTANCE.cs_disasm_iter(handle, codePtrRef, sizeRef, addrRef, insn)

      if (result) {
        val csInsn = CsInsn(insn)

        // Update position - cs_disasm_iter already updated the refs, but we need to sync our
        // position
        position.advance(csInsn.size.toInt())

        // Convert and return instruction
        convertInstruction(csInsn)
      } else {
        null
      }
    } finally {
      CapstoneLibrary.INSTANCE.cs_free(insn, NativeLong(1))
    }
  }

  override fun setOption(option: CapstoneOption): CapstoneResult<Unit> = runCatching {
    val err =
        CapstoneLibrary.INSTANCE.cs_option(
            handle, option.optType, NativeLong(option.optValue.toLong()))

    if (err != ErrorCode.OK.value) {
      throw ErrorCode.fromValue(err).toError(architecture, mode)
    }

    // Track detail mode
    if (option is CapstoneOption.Detail) {
      detailEnabled = option.enabled
    }
  }

  override fun regName(regId: Int): String? {
    return CapstoneLibrary.INSTANCE.cs_reg_name(handle, regId)
  }

  override fun insnName(insnId: Int): String? {
    return CapstoneLibrary.INSTANCE.cs_insn_name(handle, insnId)
  }

  override fun groupName(groupId: Int): String? {
    return CapstoneLibrary.INSTANCE.cs_group_name(handle, groupId)
  }

  override fun lastError(): ErrorCode {
    val err = CapstoneLibrary.INSTANCE.cs_errno(handle)
    return ErrorCode.fromValue(err)
  }

  override fun close() {
    CapstoneLibrary.INSTANCE.cs_close(handleRef)
  }

  /** Convert JNA cs_insn structure to Kotlin Instruction object */
  private fun convertInstruction(insn: CsInsn): Instruction {
    val id = insn.id
    val address = insn.address
    val size = insn.size.toInt()

    // Copy instruction bytes
    val bytes = insn.bytes.copyOf(size)

    // Get mnemonic and operand string (find null terminator for efficiency)
    val mnemonicEnd = insn.mnemonic.indexOf(0.toByte()).takeIf { it >= 0 } ?: insn.mnemonic.size
    val mnemonic = String(insn.mnemonic, 0, mnemonicEnd)
    val opStrEnd = insn.op_str.indexOf(0.toByte()).takeIf { it >= 0 } ?: insn.op_str.size
    val opStr = String(insn.op_str, 0, opStrEnd)

    // Parse detail if available and enabled
    val detail =
        if (detailEnabled && insn.detail != null) {
          convertDetail(insn.detail!!)
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

  /** Convert JNA cs_detail structure to Kotlin InstructionDetail object */
  private fun convertDetail(detail: CsDetail): InstructionDetail {
    detail.read()

    // Parse registers read
    val regsRead = buildList {
      for (i in 0 until detail.regs_read_count.toInt()) {
        val regId = detail.regs_read[i].toInt()
        val regName = CapstoneLibrary.INSTANCE.cs_reg_name(handle, regId)
        add(Register(regId, regName))
      }
    }

    // Parse registers written
    val regsWritten = buildList {
      for (i in 0 until detail.regs_write_count.toInt()) {
        val regId = detail.regs_write[i].toInt()
        val regName = CapstoneLibrary.INSTANCE.cs_reg_name(handle, regId)
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
        writeback = detail.writeback.toInt() != 0,
        archDetail = archDetail)
  }

  /** Convert architecture-specific details from the union */
  private fun convertArchDetail(detail: CsDetail): ArchDetail? {
    return when (architecture) {
      Architecture.ARM64 -> {
        detail.arch.setType(Arm64UnionOpInfo::class.java)
        detail.arch.read()
        val arm64 = detail.arch.arm64 ?: return null
        arm64.read()
        ArchDetail.AArch64(convertAArch64Detail(arm64))
      }
      Architecture.X86 -> {
        detail.arch.setType(X86UnionOpInfo::class.java)
        detail.arch.read()
        val x86 = detail.arch.x86 ?: return null
        ArchDetail.X86(convertX86Detail(x86))
      }
      Architecture.ARM -> {
        detail.arch.setType(ArmUnionOpInfo::class.java)
        detail.arch.read()
        val arm = detail.arch.arm ?: return null
        arm.read()
        ArchDetail.ARM(convertArmDetail(arm))
      }
      else -> null
    }
  }

  private fun convertArmDetail(arm: ArmUnionOpInfo): ArmInstructionDetail {
    val operands = buildList {
      for (i in 0 until arm.op_count.toInt()) {
        val op = arm.op[i]
        op.read()

        val type = op.type.toArmOpType()

        var reg: Register? = null
        var imm: Int? = null
        var fp: Double? = null
        var mem: ArmMemoryOperand? = null
        var setend = ArmSetEndType.INVALID

        when (type) {
          ArmOpType.REG,
          ArmOpType.SYSREG,
          ArmOpType.BANKEDREG -> {
            val regId = op.value.reg
            val regName = CapstoneLibrary.INSTANCE.cs_reg_name(handle, regId)
            reg = Register(regId, regName)
          }
          ArmOpType.IMM,
          ArmOpType.CIMM,
          ArmOpType.PIMM -> {
            imm = op.value.imm
          }
          ArmOpType.FP -> {
            fp = op.value.fp
          }
          ArmOpType.MEM -> {
            val m = op.value.mem
            val baseReg =
                if (m.base != 0) {
                  Register(m.base, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.base))
                } else null

            val indexReg =
                if (m.index != 0) {
                  Register(m.index, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.index))
                } else null

            mem =
                ArmMemoryOperand(
                    base = baseReg,
                    index = indexReg,
                    scale = m.scale,
                    disp = m.disp,
                    lshift = m.lshift)
          }
          ArmOpType.SETEND -> {
            setend = op.value.setend.toArmSetEndType()
          }
          else -> {}
        }

        add(
            ArmOperand(
                type = type,
                vectorIndex = op.vector_index,
                shiftType = op.shift.type.toArmShifter(),
                shiftValue = op.shift.value,
                reg = reg,
                imm = imm,
                fp = fp,
                mem = mem,
                setend = setend,
                subtracted = op.subtracted.toInt() != 0,
                access = AccessType.fromValue(op.access.toInt()),
                neonLane = op.neon_lane))
      }
    }

    return ArmInstructionDetail(
        usermode = arm.usermode.toInt() != 0,
        vectorSize = arm.vector_size,
        vectorData = arm.vector_data.toArmVectorDataType(),
        cpsMode = arm.cps_mode.toArmCpsModeType(),
        cpsFlag = arm.cps_flag.toArmCpsFlagType(),
        cc = arm.cc.toArmConditionCode(),
        updateFlags = arm.update_flags.toInt() != 0,
        writeback = arm.writeback.toInt() != 0,
        memBarrier = arm.mem_barrier.toArmMemoryBarrierOption(),
        operands = operands)
  }

  private fun convertAArch64Detail(aarch64: Arm64UnionOpInfo): AArch64InstructionDetail {
    val operands = buildList {
      for (i in 0 until aarch64.op_count.toInt()) {
        val op = aarch64.op[i]
        op.read()

        val type =
            AArch64OpType.entries.firstOrNull { it.toInt() == op.type } ?: AArch64OpType.INVALID

        var reg: Register? = null
        var imm: Long? = null
        var fp: Double? = null
        var mem: AArch64MemoryOperand? = null

        when (type) {
          AArch64OpType.REG,
          AArch64OpType.REG_MRS,
          AArch64OpType.REG_MSR -> {
            val regId = op.value.reg
            val regName = CapstoneLibrary.INSTANCE.cs_reg_name(handle, regId)
            reg = Register(regId, regName)
          }
          AArch64OpType.IMM,
          AArch64OpType.CIMM -> {
            imm = op.value.imm
          }
          AArch64OpType.FP -> {
            fp = op.value.fp
          }
          AArch64OpType.MEM -> {
            val m = op.value.mem
            val baseReg =
                if (m.base != 0) {
                  Register(m.base, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.base))
                } else null

            val indexReg =
                if (m.index != 0) {
                  Register(m.index, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.index))
                } else null

            mem = AArch64MemoryOperand(base = baseReg, index = indexReg, disp = m.disp.toLong())
          }
          else -> {}
        }

        add(
            AArch64Operand(
                type = type,
                access = AccessType.fromValue(op.access.toInt()),
                vectorIndex = op.vector_index,
                vas =
                    AArch64VectorLayout.entries.firstOrNull { it.toInt() == op.vas }
                        ?: AArch64VectorLayout.INVALID,
                shifter =
                    AArch64Shifter.entries.firstOrNull { it.toInt() == op.shift.type }
                        ?: AArch64Shifter.INVALID,
                shiftValue = op.shift.value,
                extender =
                    AArch64Extender.entries.firstOrNull { it.toInt() == op.ext }
                        ?: AArch64Extender.INVALID,
                reg = reg,
                imm = imm,
                fp = fp,
                mem = mem,
                barrier = null,
                prefetch = null,
                isListMember = false))
      }
    }

    return AArch64InstructionDetail(
        cc =
            AArch64ConditionCode.entries.firstOrNull { it.toInt() == aarch64.cc }
                ?: AArch64ConditionCode.Invalid,
        updateFlags = aarch64.update_flags > 0,
        writeback = aarch64.post_index > 0,
        postIndex = aarch64.post_index > 0,
        operands = operands)
  }

  private fun convertX86Detail(x86: X86UnionOpInfo): X86InstructionDetail {
    val prefix = buildList {
      for (i in 0 until 4) {
        if (x86.prefix[i].toInt() != 0) {
          add(X86Prefix.fromValue(x86.prefix[i].toInt()))
        }
      }
    }

    val operands = buildList {
      for (i in 0 until x86.op_count.toInt()) {
        val op = x86.op[i]
        op.read()

        val type = X86OpType.fromValue(op.type)

        var reg: Register? = null
        var imm: Long? = null
        var mem: X86MemoryOperand? = null

        when (type) {
          X86OpType.REG -> {
            val regId = op.value.reg
            val regName = CapstoneLibrary.INSTANCE.cs_reg_name(handle, regId)
            reg = Register(regId, regName)
          }
          X86OpType.IMM -> {
            imm = op.value.imm
          }
          X86OpType.MEM -> {
            val m = op.value.mem
            val baseReg =
                if (m.base != 0) {
                  Register(m.base, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.base))
                } else null

            val indexReg =
                if (m.index != 0) {
                  Register(m.index, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.index))
                } else null

            val segmentReg =
                if (m.segment != 0) {
                  Register(m.segment, CapstoneLibrary.INSTANCE.cs_reg_name(handle, m.segment))
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
                avxBcast = X86AvxBroadcast.fromValue(op.avx_bcast),
                avxZeroOpmask = op.avx_zero_opmask))
      }
    }

    return X86InstructionDetail(
        prefix = prefix,
        opcode = x86.opcode.copyOf(),
        rex = x86.rex,
        addrSize = x86.addr_size.toInt(),
        modrm = x86.modrm,
        sib = x86.sib,
        disp = x86.disp,
        sibIndex =
            if (x86.sib_index != 0)
                Register(x86.sib_index, CapstoneLibrary.INSTANCE.cs_reg_name(handle, x86.sib_index))
            else null,
        sibScale = x86.sib_scale.toInt(),
        sibBase =
            if (x86.sib_base != 0)
                Register(x86.sib_base, CapstoneLibrary.INSTANCE.cs_reg_name(handle, x86.sib_base))
            else null,
        operands = operands,
        avxCC = X86AvxConditionCode.fromValue(x86.avx_cc),
        sseCC = X86SseConditionCode.fromValue(x86.sse_cc),
        avxRm = X86AvxRoundingMode.fromValue(x86.avx_rm),
        avxSae = x86.avx_sae > 0,
        eflags = BitField.fromValue(x86.eflags.toULong()),
        fpuFlags = x86.fpu_flags)
  }
}
