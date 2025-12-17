package ir.alisalimik.kapstone.internal.platform

import ir.alisalimik.kapstone.Instruction
import ir.alisalimik.kapstone.InternalInstruction
import ir.alisalimik.kapstone.api.DisassemblyPosition
import ir.alisalimik.kapstone.arch.AArch64InstructionDetail
import ir.alisalimik.kapstone.arch.AArch64MemoryOperand
import ir.alisalimik.kapstone.arch.AArch64Operand
import ir.alisalimik.kapstone.arch.ArchDetail
import ir.alisalimik.kapstone.arch.ArmInstructionDetail
import ir.alisalimik.kapstone.arch.ArmMemoryOperand
import ir.alisalimik.kapstone.arch.ArmOperand
import ir.alisalimik.kapstone.arch.X86InstructionDetail
import ir.alisalimik.kapstone.arch.X86MemoryOperand
import ir.alisalimik.kapstone.arch.X86Operand
import ir.alisalimik.kapstone.bit.BitField
import ir.alisalimik.kapstone.enums.AccessType
import ir.alisalimik.kapstone.enums.Architecture
import ir.alisalimik.kapstone.enums.CapstoneOption
import ir.alisalimik.kapstone.enums.InstructionGroup
import ir.alisalimik.kapstone.enums.Mode
import ir.alisalimik.kapstone.error.CapstoneError
import ir.alisalimik.kapstone.error.CapstoneResult
import ir.alisalimik.kapstone.error.ErrorCode
import ir.alisalimik.kapstone.error.toError
import ir.alisalimik.kapstone.exp.aarch64.AArch64ConditionCode
import ir.alisalimik.kapstone.exp.aarch64.AArch64Extender
import ir.alisalimik.kapstone.exp.aarch64.AArch64OpType
import ir.alisalimik.kapstone.exp.aarch64.AArch64Shifter
import ir.alisalimik.kapstone.exp.aarch64.AArch64VectorLayout
import ir.alisalimik.kapstone.exp.arm.ArmConditionCode
import ir.alisalimik.kapstone.exp.arm.ArmCpsFlagType
import ir.alisalimik.kapstone.exp.arm.ArmCpsModeType
import ir.alisalimik.kapstone.exp.arm.ArmMemoryBarrierOption
import ir.alisalimik.kapstone.exp.arm.ArmOpType
import ir.alisalimik.kapstone.exp.arm.ArmSetEndType
import ir.alisalimik.kapstone.exp.arm.ArmShifter
import ir.alisalimik.kapstone.exp.arm.ArmVectorDataType
import ir.alisalimik.kapstone.exp.x86.X86AvxBroadcast
import ir.alisalimik.kapstone.exp.x86.X86AvxConditionCode
import ir.alisalimik.kapstone.exp.x86.X86AvxRoundingMode
import ir.alisalimik.kapstone.exp.x86.X86OpType
import ir.alisalimik.kapstone.exp.x86.X86Prefix
import ir.alisalimik.kapstone.exp.x86.X86SseConditionCode
import ir.alisalimik.kapstone.internal.CapstoneWasi
import ir.alisalimik.kapstone.internal._cs_close
import ir.alisalimik.kapstone.internal._cs_disasm
import ir.alisalimik.kapstone.internal._cs_errno
import ir.alisalimik.kapstone.internal._cs_free
import ir.alisalimik.kapstone.internal._cs_open
import ir.alisalimik.kapstone.internal._cs_option
import ir.alisalimik.kapstone.internal._cs_support
import ir.alisalimik.kapstone.internal._cs_version
import ir.alisalimik.kapstone.internal._free
import ir.alisalimik.kapstone.internal._malloc
import ir.alisalimik.kapstone.model.InstructionDetail
import ir.alisalimik.kapstone.model.Register

internal class WasmWasiCapstoneBinding(
    private val architecture: Architecture,
    private val mode: BitField<Mode>
) : CapstoneBinding {

  private val handlePtr: Int = _malloc(4)
  private var handle: Int = 0
  private var detailEnabled = false

  init {
    val err = _cs_open(architecture.value, mode.value.toInt(), handlePtr)
    if (err != ErrorCode.OK.value) {
      val errorMsg = CapstoneWasi.getErrorName(err)
      _free(handlePtr)
      throw CapstoneError.UnsupportedArchitecture(
          architecture, "Failed to open Capstone: \$errorMsg")
    }
    handle = CapstoneWasi.loadInt32(handlePtr)
  }

  override fun disasm(
      code: ByteArray,
      address: Long,
      count: Int
  ): CapstoneResult<List<Instruction>> = runCatching {
    val codePtr = _malloc(code.size)
    CapstoneWasi.writeBytes(codePtr, code)
    val insnPtrPtr = _malloc(4)

    try {
      val disasmCount = _cs_disasm(handle, codePtr, code.size, address, count, insnPtrPtr)

      if (disasmCount > 0) {
        val instructions = ArrayList<Instruction>(disasmCount)
        val insnArrPtr = CapstoneWasi.loadInt32(insnPtrPtr)
        val STRUCT_SIZE = 256 // Estimate, we use manual offsets

        for (i in 0 until disasmCount) {
          val ptr = insnArrPtr + (i * STRUCT_SIZE)
          instructions.add(convertInstruction(ptr))
        }
        _cs_free(insnArrPtr, disasmCount)
        instructions
      } else {
        emptyList()
      }
    } finally {
      _free(codePtr)
      _free(insnPtrPtr)
    }
  }

  override fun disasmIter(
      code: ByteArray,
      position: DisassemblyPosition
  ): CapstoneResult<Instruction?> = runCatching {
    val remainingSize = position.remaining(code.size)
    if (remainingSize <= 0) return@runCatching null

    // Copying likely needed if buffer reused by wrapper? Safe to copy.
    val slice = code.copyOfRange(position.offset, code.size)
    val codePtr = _malloc(remainingSize)
    CapstoneWasi.writeBytes(codePtr, slice)
    val insnPtrPtr = _malloc(4)

    try {
      val count = _cs_disasm(handle, codePtr, remainingSize, position.address, 1, insnPtrPtr)
      if (count > 0) {
        val insnArrPtr = CapstoneWasi.loadInt32(insnPtrPtr)
        val ptr = insnArrPtr // First instruction

        val insn = convertInstruction(ptr)
        position.advance(insn.size)

        _cs_free(insnArrPtr, count)
        insn
      } else {
        null
      }
    } finally {
      _free(codePtr)
      _free(insnPtrPtr)
    }
  }

  private fun convertInstruction(ptr: Int): Instruction {
    val id = CapstoneWasi.loadInt32(ptr)
    val address = CapstoneWasi.loadLong(ptr + 16)
    val size = CapstoneWasi.loadShort(ptr + 24).toInt() and 0xFFFF

    val validSize = if (size > 24) 24 else size
    val bytes = ByteArray(validSize)
    for (b in 0 until validSize) {
      bytes[b] = CapstoneWasi.loadByte(ptr + 26 + b)
    }

    val mnemonic = CapstoneWasi.readCString(ptr + 50)
    val opStr = CapstoneWasi.readCString(ptr + 82)

    // Detail pointer at 248 (Aligned to 8 bytes)
    val detailPtr = CapstoneWasi.loadInt32(ptr + 248)
    if (detailEnabled && detailPtr == 0) {
      throw RuntimeException("DEBUG: detailEnabled=true but detailPtr=0 at offset 248")
    }
    val detail = if (detailPtr != 0) convertDetail(detailPtr, mnemonic) else null

    return InternalInstruction(id, address, size, bytes, mnemonic, opStr, detail)
  }

  private fun convertDetail(ptr: Int, mnemonic: String): InstructionDetail {
    val regsReadCount = CapstoneWasi.loadByte(ptr + 40).toInt() and 0xFF
    val regsRead = ArrayList<Register>(regsReadCount)
    for (i in 0 until regsReadCount) {
      val id = CapstoneWasi.loadShort(ptr + (i * 2)).toInt() and 0xFFFF
      regsRead.add(Register(id, regName(id)))
    }

    val regsWriteCount = CapstoneWasi.loadByte(ptr + 82).toInt() and 0xFF
    val regsWrite = ArrayList<Register>(regsWriteCount)
    // regs_write starts at 42
    for (i in 0 until regsWriteCount) {
      val id = CapstoneWasi.loadShort(ptr + 42 + (i * 2)).toInt() and 0xFFFF
      regsWrite.add(Register(id, regName(id)))
    }

    val groupsCount = CapstoneWasi.loadByte(ptr + 91).toInt() and 0xFF
    val groups = ArrayList<InstructionGroup>(groupsCount)
    // println("DEBUG: groupsCount=$groupsCount")
    // groups start at 83
    for (i in 0 until groupsCount) {
      val id = CapstoneWasi.loadByte(ptr + 83 + i).toInt() and 0xFF
      // println("DEBUG: group[$i]=$id")
      InstructionGroup.fromValue(id)?.let { groups.add(it) }
    }

    if (groups.isEmpty()) {
      when (architecture) {
        Architecture.ARM -> {
          when (mnemonic) {
            "b",
            "bx" -> groups.add(InstructionGroup.JUMP)
            "bl",
            "blx" -> groups.add(InstructionGroup.CALL)
          }
        }
        Architecture.ARM64 -> {
          if (mnemonic == "b" ||
              mnemonic.startsWith("b.") ||
              mnemonic == "br" ||
              mnemonic == "blr" ||
              mnemonic == "cbnz" ||
              mnemonic == "cbz" ||
              mnemonic == "tbz" ||
              mnemonic == "tbnz") {
            groups.add(InstructionGroup.JUMP)
          }
          if (mnemonic == "bl" || mnemonic == "blr") {
            groups.add(InstructionGroup.CALL)
          }
          if (mnemonic == "ret") {
            groups.add(InstructionGroup.RET)
          }
        }
        Architecture.X86 -> {
          if (mnemonic.startsWith("j") || mnemonic.startsWith("loop")) {
            groups.add(InstructionGroup.JUMP)
          }
          if (mnemonic.startsWith("call")) {
            groups.add(InstructionGroup.CALL)
          }
          if (mnemonic.startsWith("ret")) {
            groups.add(InstructionGroup.RET)
          }
          if (mnemonic.startsWith("int")) {
            groups.add(InstructionGroup.INT)
          }
          if (mnemonic.startsWith("iret")) {
            groups.add(InstructionGroup.IRET)
          }
        }
        else -> {}
      }
    }

    val writeback = CapstoneWasi.loadByte(ptr + 92).toInt() != 0

    // Union architecture details start at 96
    val archOffset = 96
    val archDetail: ArchDetail? =
        when (architecture) {
          Architecture.ARM64 -> ArchDetail.AArch64(convertAArch64Detail(ptr + archOffset))
          Architecture.ARM -> ArchDetail.ARM(convertArmDetail(ptr + archOffset))
          Architecture.X86 -> ArchDetail.X86(convertX86Detail(ptr + archOffset))
          else -> null
        }

    return InstructionDetail(regsRead, regsWrite, groups, writeback, archDetail)
  }

  // --- Arch Specific Conversions (Adapted from Web/Native bindings) ---

  private fun convertAArch64Detail(ptr: Int): AArch64InstructionDetail {
    val ccVal = CapstoneWasi.loadInt32(ptr)
    val updateFlags = CapstoneWasi.loadByte(ptr + 4).toInt() != 0
    val writeback = CapstoneWasi.loadByte(ptr + 5).toInt() != 0
    val opCount = CapstoneWasi.loadByte(ptr + 6).toInt() and 0xFF

    val operandsOffset = 8
    val operands = ArrayList<AArch64Operand>(opCount)
    for (i in 0 until opCount) {
      val opPtr = ptr + operandsOffset + (i * 48) // AArch64 operand size ~48
      operands.add(convertAArch64Operand(opPtr))
    }

    return AArch64InstructionDetail(
        cc =
            AArch64ConditionCode.entries.firstOrNull { it.toInt() == ccVal }
                ?: AArch64ConditionCode.Invalid,
        updateFlags = updateFlags,
        writeback = writeback,
        operands = operands)
  }

  private fun convertAArch64Operand(ptr: Int): AArch64Operand {
    val vectorIndex = CapstoneWasi.loadInt32(ptr)
    val vasVal = CapstoneWasi.loadInt32(ptr + 4)
    val shiftTypeVal = CapstoneWasi.loadInt32(ptr + 12)
    val shiftValue = CapstoneWasi.loadInt32(ptr + 16)
    val extTypeVal = CapstoneWasi.loadInt32(ptr + 20)
    // extValue at 24?
    val opTypeVal = CapstoneWasi.loadInt32(ptr + 28)

    val opType =
        AArch64OpType.entries.firstOrNull { it.toInt() == opTypeVal } ?: AArch64OpType.INVALID

    // Union data at offset 32
    val dataPtr = ptr + 32

    val reg =
        if (opType == AArch64OpType.REG) {
          val regId = CapstoneWasi.loadInt32(dataPtr)
          Register(regId, regName(regId))
        } else null

    val imm =
        if (opType == AArch64OpType.IMM || opType == AArch64OpType.CIMM) {
          CapstoneWasi.loadLong(dataPtr)
        } else null

    val fp =
        if (opType == AArch64OpType.FP) {
          Double.fromBits(CapstoneWasi.loadLong(dataPtr))
        } else null

    val mem = if (opType == AArch64OpType.MEM) convertAArch64MemOp(dataPtr) else null

    val access = CapstoneWasi.loadInt32(ptr + 44)

    return AArch64Operand(
        type = opType,
        access = AccessType.fromValue(access),
        vectorIndex = vectorIndex,
        vas =
            AArch64VectorLayout.entries.firstOrNull { it.toInt() == vasVal }
                ?: AArch64VectorLayout.INVALID,
        shifter =
            AArch64Shifter.entries.firstOrNull { it.toInt() == shiftTypeVal }
                ?: AArch64Shifter.INVALID,
        shiftValue = shiftValue,
        extender =
            AArch64Extender.entries.firstOrNull { it.toInt() == extTypeVal }
                ?: AArch64Extender.INVALID,
        reg = reg,
        imm = imm,
        fp = fp,
        mem = mem)
  }

  private fun convertAArch64MemOp(ptr: Int): AArch64MemoryOperand {
    val baseId = CapstoneWasi.loadInt32(ptr)
    val indexId = CapstoneWasi.loadInt32(ptr + 4)
    val disp = CapstoneWasi.loadInt32(ptr + 8)
    return AArch64MemoryOperand(
        base = if (baseId != 0) Register(baseId, regName(baseId)) else null,
        index = if (indexId != 0) Register(indexId, regName(indexId)) else null,
        disp = disp.toLong())
  }

  private fun convertArmDetail(ptr: Int): ArmInstructionDetail {
    val usermode = CapstoneWasi.loadByte(ptr).toInt() != 0
    val vectorSize = CapstoneWasi.loadInt32(ptr + 4)
    val vectorDataVal = CapstoneWasi.loadInt32(ptr + 8)
    val cpsModeVal = CapstoneWasi.loadInt32(ptr + 12)
    val cpsFlagVal = CapstoneWasi.loadInt32(ptr + 16)
    val ccVal = CapstoneWasi.loadInt32(ptr + 20)
    val updateFlags = CapstoneWasi.loadByte(ptr + 24).toInt() != 0
    val writeback = CapstoneWasi.loadByte(ptr + 25).toInt() != 0
    val memBarrierVal = CapstoneWasi.loadInt32(ptr + 28)
    val opCount = CapstoneWasi.loadByte(ptr + 32).toInt() and 0xFF

    // Align to 36 or 40? webMain used 36.
    val operandsOffset = 36
    val operands = ArrayList<ArmOperand>(opCount)
    for (i in 0 until opCount) {
      val opPtr = ptr + operandsOffset + (i * 48) // Arm operand size ~48?
      operands.add(convertArmOperand(opPtr))
    }

    // Helper functions
    fun Int.toArmVectorDataType() =
        ArmVectorDataType.entries.firstOrNull { it.toInt() == this } ?: ArmVectorDataType.INVALID
    fun Int.toArmCpsModeType() =
        ArmCpsModeType.entries.firstOrNull { it.toInt() == this } ?: ArmCpsModeType.INVALID
    fun Int.toArmCpsFlagType() =
        ArmCpsFlagType.entries.firstOrNull { it.toInt() == this } ?: ArmCpsFlagType.INVALID
    fun Int.toArmConditionCode() =
        ArmConditionCode.entries.firstOrNull { it.toInt() == this } ?: ArmConditionCode.Invalid
    fun Int.toArmMemoryBarrierOption() =
        ArmMemoryBarrierOption.entries.firstOrNull { it.toInt() == this }
            ?: ArmMemoryBarrierOption.RESERVED_0

    return ArmInstructionDetail(
        usermode = usermode,
        vectorSize = vectorSize,
        vectorData = vectorDataVal.toArmVectorDataType(),
        cpsMode = cpsModeVal.toArmCpsModeType(),
        cpsFlag = cpsFlagVal.toArmCpsFlagType(),
        cc = ccVal.toArmConditionCode(),
        updateFlags = updateFlags,
        writeback = writeback,
        memBarrier = memBarrierVal.toArmMemoryBarrierOption(),
        operands = operands)
  }

  private fun convertArmOperand(ptr: Int): ArmOperand {
    val vectorIndex = CapstoneWasi.loadInt32(ptr)
    val shiftTypeVal = CapstoneWasi.loadInt32(ptr + 4)
    val shiftValue = CapstoneWasi.loadInt32(ptr + 8)
    val opTypeVal = CapstoneWasi.loadInt32(ptr + 12)

    val opType = ArmOpType.entries.firstOrNull { it.toInt() == opTypeVal } ?: ArmOpType.INVALID
    val shift = ArmShifter.entries.firstOrNull { it.toInt() == shiftTypeVal } ?: ArmShifter.INVALID

    val dataPtr = ptr + 16

    val reg =
        if (opType == ArmOpType.REG || opType == ArmOpType.SYSREG) {
          val r = CapstoneWasi.loadInt32(dataPtr)
          Register(r, regName(r))
        } else null

    val imm =
        if (opType == ArmOpType.IMM || opType == ArmOpType.CIMM || opType == ArmOpType.PIMM) {
          CapstoneWasi.loadInt32(dataPtr)
        } else null

    val fp =
        if (opType == ArmOpType.FP) {
          Double.fromBits(CapstoneWasi.loadLong(dataPtr))
        } else null

    val mem = if (opType == ArmOpType.MEM) convertArmMemOp(dataPtr) else null

    val setend =
        if (opType == ArmOpType.SETEND) {
          val s = CapstoneWasi.loadInt32(dataPtr)
          ArmSetEndType.entries.firstOrNull { it.toInt() == s } ?: ArmSetEndType.INVALID
        } else ArmSetEndType.INVALID

    val subtracted = CapstoneWasi.loadByte(ptr + 36).toInt() != 0
    val access = CapstoneWasi.loadInt32(ptr + 40)
    val neonLane = CapstoneWasi.loadByte(ptr + 44)

    return ArmOperand(
        type = opType,
        access = AccessType.fromValue(access),
        vectorIndex = vectorIndex,
        shiftType = shift,
        shiftValue = shiftValue,
        reg = reg,
        imm = imm,
        fp = fp,
        mem = mem,
        setend = setend,
        subtracted = subtracted,
        neonLane = neonLane)
  }

  private fun convertArmMemOp(ptr: Int): ArmMemoryOperand {
    val baseId = CapstoneWasi.loadInt32(ptr)
    val indexId = CapstoneWasi.loadInt32(ptr + 4)
    return ArmMemoryOperand(
        base = if (baseId != 0) Register(baseId, regName(baseId)) else null,
        index = if (indexId != 0) Register(indexId, regName(indexId)) else null,
        scale = CapstoneWasi.loadInt32(ptr + 8),
        disp = CapstoneWasi.loadInt32(ptr + 12),
        lshift = CapstoneWasi.loadInt32(ptr + 16))
  }

  private fun convertX86Detail(ptr: Int): X86InstructionDetail {
    val prefix = ArrayList<X86Prefix>()
    for (i in 0 until 4) {
      val pVal = CapstoneWasi.loadByte(ptr + i).toInt() and 0xFF
      if (pVal != 0) {
        prefix.add(X86Prefix.fromValue(pVal))
      }
    }

    val opcode = ByteArray(4)
    for (i in 0 until 4) {
      opcode[i] = CapstoneWasi.loadByte(ptr + 4 + i)
    }

    val rex = CapstoneWasi.loadByte(ptr + 8)
    val addrSize = CapstoneWasi.loadByte(ptr + 9).toInt() and 0xFF
    val modRm = CapstoneWasi.loadByte(ptr + 10)
    val sib = CapstoneWasi.loadByte(ptr + 11)
    val disp = CapstoneWasi.loadLong(ptr + 12)

    val sibIndexId = CapstoneWasi.loadInt32(ptr + 20) // x86_reg is int (enum)
    val sibScale = CapstoneWasi.loadByte(ptr + 24).toInt()
    val sibBaseId = CapstoneWasi.loadInt32(ptr + 28) // x86_reg gets aligned?

    val xopCcVal = CapstoneWasi.loadInt32(ptr + 32)
    val sseCcVal = CapstoneWasi.loadInt32(ptr + 36)
    val avxCcVal = CapstoneWasi.loadInt32(ptr + 40)
    val avxSae = CapstoneWasi.loadByte(ptr + 44).toInt() != 0
    val avxRmVal = CapstoneWasi.loadInt32(ptr + 48)

    val eflagsVal = CapstoneWasi.loadLong(ptr + 52)

    val opCount = CapstoneWasi.loadByte(ptr + 60).toInt() and 0xFF

    val operands = ArrayList<X86Operand>(opCount)
    val opStartPtr = ptr + 64 // 64-byte aligned or just +4 from opCount?

    for (i in 0 until opCount) {
      val opPtr = opStartPtr + (i * 48) // X86 operand size
      operands.add(convertX86Operand(opPtr))
    }

    return X86InstructionDetail(
        prefix = prefix,
        opcode = opcode,
        rex = rex,
        addrSize = addrSize,
        modrm = modRm,
        sib = sib,
        disp = disp,
        sibIndex = if (sibIndexId != 0) Register(sibIndexId, regName(sibIndexId)) else null,
        sibScale = sibScale,
        sibBase = if (sibBaseId != 0) Register(sibBaseId, regName(sibBaseId)) else null,
        operands = operands,
        avxCC = X86AvxConditionCode.fromValue(avxCcVal),
        sseCC = X86SseConditionCode.fromValue(sseCcVal),
        avxRm = X86AvxRoundingMode.fromValue(avxRmVal),
        avxSae = avxSae,
        eflags = BitField.fromValue(eflagsVal.toULong()),
        fpuFlags = 0 // Not mapped
        )
  }

  private fun convertX86Operand(ptr: Int): X86Operand {
    val opTypeVal = CapstoneWasi.loadInt32(ptr)
    val opType = X86OpType.fromValue(opTypeVal)

    val dataPtr = ptr + 4

    val reg =
        if (opType == X86OpType.REG) {
          val r = CapstoneWasi.loadInt32(dataPtr)
          Register(r, regName(r))
        } else null

    val imm =
        if (opType == X86OpType.IMM) {
          CapstoneWasi.loadLong(dataPtr)
        } else null

    val mem = if (opType == X86OpType.MEM) convertX86MemOp(dataPtr) else null

    val size = CapstoneWasi.loadByte(ptr + 28).toInt() and 0xFF
    val access = CapstoneWasi.loadInt32(ptr + 32)
    val avxBcastVal = CapstoneWasi.loadInt32(ptr + 36)
    val avxZeroOpmask = CapstoneWasi.loadByte(ptr + 40).toInt() != 0

    return X86Operand(
        type = opType,
        access = AccessType.fromValue(access),
        size = size,
        reg = reg,
        imm = imm,
        mem = mem,
        avxBcast = X86AvxBroadcast.fromValue(avxBcastVal),
        avxZeroOpmask = avxZeroOpmask)
  }

  private fun convertX86MemOp(ptr: Int): X86MemoryOperand {
    val segment = CapstoneWasi.loadInt32(ptr)
    val base = CapstoneWasi.loadInt32(ptr + 4)
    val index = CapstoneWasi.loadInt32(ptr + 8)
    val scale = CapstoneWasi.loadInt32(ptr + 12)
    val disp = CapstoneWasi.loadLong(ptr + 16)

    return X86MemoryOperand(
        segment = if (segment != 0) Register(segment, regName(segment)) else null,
        base = if (base != 0) Register(base, regName(base)) else null,
        index = if (index != 0) Register(index, regName(index)) else null,
        scale = scale,
        disp = disp)
  }

  override fun setOption(option: CapstoneOption): CapstoneResult<Unit> = runCatching {
    val err = _cs_option(handle, option.optType, option.optValue)
    if (err != ErrorCode.OK.value) {
      throw ErrorCode.fromValue(err).toError(architecture, mode)
    }
    if (option is CapstoneOption.Detail) {
      detailEnabled = option.enabled
    }
  }

  override fun lastError(): ErrorCode {
    val err = _cs_errno(handle)
    return ErrorCode.fromValue(err)
  }

  override fun regName(regId: Int): String? {
    val s = CapstoneWasi.getRegName(handle, regId)
    return s.ifEmpty { null }
  }

  override fun insnName(insnId: Int): String? {
    val s = CapstoneWasi.getInsnName(handle, insnId)
    return s.ifEmpty { null }
  }

  override fun groupName(groupId: Int): String? {
    return null
  }

  override fun close() {
    _cs_close(handlePtr)
    _free(handlePtr)
  }
}

internal actual fun createPlatformBinding(
    architecture: Architecture,
    mode: BitField<Mode>
): CapstoneBinding = WasmWasiCapstoneBinding(architecture, mode)

internal actual fun isPlatformSupported(arch: Architecture): Boolean {
  return _cs_support(arch.value)
}

internal actual fun getPlatformVersion(): Pair<Int, Int> {
  val majorPtr = _malloc(4)
  val minorPtr = _malloc(4)
  try {
    _cs_version(majorPtr, minorPtr)
    val major = CapstoneWasi.loadInt32(majorPtr)
    val minor = CapstoneWasi.loadInt32(minorPtr)
    return Pair(major, minor)
  } finally {
    _free(majorPtr)
    _free(minorPtr)
  }
}
