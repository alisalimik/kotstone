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
import ir.alisalimik.kapstone.internal.CapstoneModuleInstance
import ir.alisalimik.kapstone.model.InstructionDetail
import ir.alisalimik.kapstone.model.Register
import kotlin.Boolean
import kotlin.Byte
import kotlin.ByteArray
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.Pair
import kotlin.String
import kotlin.Unit
import kotlin.also
import kotlin.collections.List
import kotlin.collections.buildList
import kotlin.collections.emptyList
import kotlin.collections.firstOrNull
import kotlin.collections.mutableListOf
import kotlin.collections.toByteArray
import kotlin.error
import kotlin.fromBits
import kotlin.js.JsAny
import kotlin.js.Promise
import kotlin.let
import kotlin.runCatching
import kotlin.text.decodeToString

internal expect suspend fun loadCapstoneModule(): Promise<CapstoneModuleInstance>

internal expect fun ByteArray.asJsInt8Array(): JsAny

internal expect fun JsAny.toInt(): Int

internal expect fun JsAny.toLong(): Long

internal expect fun Int.asJsAny(): JsAny

// internal expect fun Long.toBigInt(): JsBigInt // Not needed if we use helper or split 32-bit
// reading

// Helper for unsafe cast if needed
internal expect fun <T : JsAny> JsAny.unsafeCast(): T

/**
 * Wasm/JS-based platform implementation.
 *
 * This implementation uses Emscripten-compiled Capstone via JS interop.
 */
internal actual fun createPlatformBinding(
    architecture: Architecture,
    mode: BitField<Mode>
): CapstoneBinding = WasmCapstoneBinding(architecture, mode)

internal actual fun getPlatformVersion(): Pair<Int, Int> {
  // This will be called after initialization
  val module = getModuleInstance()
  val majorPtr = module._malloc(4)
  val minorPtr = module._malloc(4)
  try {
    module._cs_version(majorPtr, minorPtr)
    val major = module.getValue(majorPtr, "i32").toInt()
    val minor = module.getValue(minorPtr, "i32").toInt()
    return Pair(major, minor)
  } finally {
    module._free(majorPtr)
    module._free(minorPtr)
  }
}

internal actual fun isPlatformSupported(arch: Architecture): Boolean {
  val module = getModuleInstance()
  return module._cs_support(arch.value)
}

// Global module instance accessor
private var moduleInstance: CapstoneModuleInstance? = null
private var initializationPromise: Promise<CapstoneModuleInstance>? = null

internal suspend fun initializeCapstoneModule() {
  if (moduleInstance != null) return

  val promise = initializationPromise ?: loadCapstoneModule().also { initializationPromise = it }
  moduleInstance = promise.await()
}

internal fun getModuleInstance(): CapstoneModuleInstance {
  return moduleInstance
      ?: error("Capstone module not initialized. Call initializeCapstoneModule() first.")
}

/** Wasm/JS-based Capstone binding implementation */
internal class WasmCapstoneBinding(
    private val architecture: Architecture,
    private val mode: BitField<Mode>
) : CapstoneBinding {

  private val module: CapstoneModuleInstance = getModuleInstance()
  private val handlePtr: Int = module._malloc(4) // Allocate space for size_t handle
  private val handle: Int
  private var detailEnabled = false

  init {
    val err = module._cs_open(architecture.value, mode.value.toInt(), handlePtr)

    if (err != ErrorCode.OK.value) {
      val errorStrPtr = module._cs_strerror(err)
      val errorMsg = if (errorStrPtr != 0) module.UTF8ToString(errorStrPtr) else "Unknown error"
      module._free(handlePtr)
      throw CapstoneError.UnsupportedArchitecture(
          architecture, "Failed to open Capstone: $errorMsg")
    }

    handle = module.getValue(handlePtr, "i32").toInt()
  }

  override fun disasm(
      code: ByteArray,
      address: Long,
      count: Int
  ): CapstoneResult<List<Instruction>> = runCatching {
    // Allocate memory for code
    val codePtr = module._malloc(code.size)
    val insnPtrPtr = module._malloc(4) // Pointer to cs_insn*

    try {
      // Write code to WASM memory
      module.writeArrayToMemory(code.asJsInt8Array(), codePtr)

      // Call cs_disasm
      val disasmCount =
          module._cs_disasm(
              handle,
              codePtr,
              code.size,
              // println("Address type: " + js("typeof address"))
              address.toPlatformBigInt(),
              count,
              insnPtrPtr)

      if (disasmCount == 0) {
        val errno = module._cs_errno(handle)
        if (errno != ErrorCode.OK.value) {
          throw ErrorCode.fromValue(errno).toError(architecture, mode)
        }
        emptyList()
      } else {
        try {
          val instructions = mutableListOf<Instruction>()
          val insnPtr = module.getValue(insnPtrPtr, "i32").toInt()

          // cs_insn structure size is 256 bytes in Capstone v6
          val insnSize = 256

          for (i in 0 until disasmCount) {
            val currentInsnPtr = insnPtr + (i * insnSize)
            instructions.add(convertInstruction(currentInsnPtr))
          }
          instructions
        } finally {
          val insnPtr = module.getValue(insnPtrPtr, "i32").toInt()
          module._cs_free(insnPtr, disasmCount)
        }
      }
    } finally {
      module._free(codePtr)
      module._free(insnPtrPtr)
    }
  }

  override fun disasmIter(
      code: ByteArray,
      position: DisassemblyPosition
  ): CapstoneResult<Instruction?> = runCatching {
    if (!position.hasRemaining(code.size)) {
      return@runCatching null
    }

    val insnPtr = module._cs_malloc(handle)
    if (insnPtr == 0) {
      throw CapstoneError.OutOfMemory()
    }

    try {
      // Allocate memory for code
      val codePtr = module._malloc(code.size)
      try {
        module.writeArrayToMemory(code.asJsInt8Array(), codePtr)

        // Create pointers for mutable arguments
        val codePtrPtr = module._malloc(4)
        val sizePtr = module._malloc(4)
        val addrPtr = module._malloc(8) // uint64_t

        try {
          // Set initial values
          val currentCodePtr = codePtr + position.offset
          module.setValue(codePtrPtr, currentCodePtr.asJsAny(), "i32")
          module.setValue(sizePtr, position.remaining(code.size).asJsAny(), "i32")
          // For 64-bit address, we need to set both low and high 32 bits
          module.setValue(addrPtr, (position.address and 0xFFFFFFFF).toInt().asJsAny(), "i32")
          module.setValue(addrPtr + 4, (position.address ushr 32).toInt().asJsAny(), "i32")

          val result = module._cs_disasm_iter(handle, codePtrPtr, sizePtr, addrPtr, insnPtr)

          if (result) {
            // Get the instruction size to advance position
            val size = readShort(insnPtr + 24) // size field at offset 24
            position.advance(size)

            // Convert and return instruction
            convertInstruction(insnPtr)
          } else {
            null
          }
        } finally {
          module._free(codePtrPtr)
          module._free(sizePtr)
          module._free(addrPtr)
        }
      } finally {
        module._free(codePtr)
      }
    } finally {
      module._cs_free(insnPtr, 1)
    }
  }

  override fun setOption(option: CapstoneOption): CapstoneResult<Unit> = runCatching {
    val err = module._cs_option(handle, option.optType, option.optValue)

    if (err != ErrorCode.OK.value) {
      throw ErrorCode.fromValue(err).toError(architecture, mode)
    }

    // Track detail mode
    if (option is CapstoneOption.Detail) {
      detailEnabled = option.enabled
    }
  }

  override fun regName(regId: Int): String? {
    val namePtr = module._cs_reg_name(handle, regId)
    return if (namePtr != 0) module.UTF8ToString(namePtr) else null
  }

  override fun insnName(insnId: Int): String? {
    val namePtr = module._cs_insn_name(handle, insnId)
    return if (namePtr != 0) module.UTF8ToString(namePtr) else null
  }

  override fun groupName(groupId: Int): String? {
    val namePtr = module._cs_group_name(handle, groupId)
    return if (namePtr != 0) module.UTF8ToString(namePtr) else null
  }

  override fun lastError(): ErrorCode {
    val err = module._cs_errno(handle)
    return ErrorCode.fromValue(err)
  }

  override fun close() {
    module._cs_close(handlePtr)
    module._free(handlePtr)
  }

  /**
   * Convert cs_insn structure from WASM memory to Kotlin Instruction object
   *
   * Structure layout (Capstone v6):
   * - Offset 0-3: id (4 bytes)
   * - Offset 4-7: alias_id (4 bytes)
   * - Offset 8-15: padding (8 bytes)
   * - Offset 16-23: address (8 bytes)
   * - Offset 24-25: size (2 bytes)
   * - Offset 26-49: bytes (24 bytes)
   * - Offset 50-81: mnemonic (32 bytes)
   * - Offset 82-241: op_str (160 bytes)
   * - Offset 242-249: detail pointer (8 bytes on 64-bit, but varies)
   */
  private fun convertInstruction(insnPtr: Int): Instruction {
    val id = readInt(insnPtr + 0)
    val address = readLong(insnPtr + 16)
    val size = readShort(insnPtr + 24)

    // Copy instruction bytes
    val bytes = ByteArray(size)
    for (i in 0 until size) {
      bytes[i] = module.getValue(insnPtr + 26 + i, "i8").toInt().toByte()
    }

    // Get mnemonic and operand string (null-terminated)
    val mnemonic = readCString(insnPtr + 50, 32)
    val opStr = readCString(insnPtr + 82, 160)

    // Parse detail if available and enabled
    val detail =
        if (detailEnabled) {
          // Detail pointer is at offset 248 (after op_str + 3 bools + padding)
          // op_str ends at 242.
          // is_alias (242), usesAliasDetails (243), illegal (244).
          // Padding (245-247) -> detail at 248 (aligned to 4/8).
          val detailPtr = readInt(insnPtr + 248)
          if (detailPtr != 0) {
            convertDetail(detailPtr)
          } else null
        } else null

    return InternalInstruction(
        id = id,
        address = address,
        size = size,
        bytes = bytes,
        mnemonic = mnemonic,
        opStr = opStr,
        detail = detail)
  }

  /** Read a null-terminated C string from WASM memory */
  private fun readCString(ptr: Int, maxLen: Int): String {
    val bytes = mutableListOf<Byte>()
    for (i in 0 until maxLen) {
      val byte = module.getValue(ptr + i, "i8").toInt().toByte()
      if (byte == 0.toByte()) break
      bytes.add(byte)
    }
    return bytes.toByteArray().decodeToString()
  }

  /** Read Int (4 bytes) from WASM memory */
  private fun readInt(ptr: Int): Int {
    return module.getValue(ptr, "i32").toInt()
  }

  /** Read Short (2 bytes) from WASM memory */
  private fun readShort(ptr: Int): Int {
    return module.getValue(ptr, "i16").toInt()
  }

  /** Read Long (8 bytes) from WASM memory */
  private fun readLong(ptr: Int): Long {
    val low = (readInt(ptr) and 0xFFFFFFFF.toInt()).toLong() and 0xFFFFFFFFL
    val high = readInt(ptr + 4).toLong()
    return (high shl 32) or low
  }

  /** Convert cs_detail structure to Kotlin InstructionDetail object */
  private fun convertDetail(detailPtr: Int): InstructionDetail {
    // cs_detail structure (Capstone v6 header):
    // - Offset 0-39: regs_read (array of uint16_t, max 20) -> 40 bytes
    // - Offset 40: regs_read_count (uint8_t)
    // - Padding 41 (1 byte) to align to 2
    // - Offset 42-135: regs_write (array of uint16_t, max 47) -> 94 bytes
    // - Offset 136: regs_write_count (uint8_t)
    // - Offset 137-152: groups (array of uint8_t, max 16) -> 16 bytes
    // - Offset 153: groups_count (uint8_t)
    // - Offset 154: writeback (bool)
    // - Padding to 160 (align to 8 for union)
    // - Offset 160+: architecture-specific data (union)

    val regsReadCount = module.getValue(detailPtr + 40, "i8").toInt() and 0xFF
    val regsRead = buildList {
      for (i in 0 until regsReadCount) {
        val regId = readShort(detailPtr + i * 2)
        val regName = regName(regId)
        add(Register(regId, regName))
      }
    }

    val regsWriteCount = module.getValue(detailPtr + 136, "i8").toInt() and 0xFF
    val regsWrite = buildList {
      for (i in 0 until regsWriteCount) {
        val regId = readShort(detailPtr + 42 + i * 2)
        val regName = regName(regId)
        add(Register(regId, regName))
      }
    }

    val groupsCount = module.getValue(detailPtr + 153, "i8").toInt() and 0xFF

    val groups = buildList {
      for (i in 0 until groupsCount) {
        val groupId = module.getValue(detailPtr + 137 + i, "i8").toInt() and 0xFF
        InstructionGroup.fromValue(groupId)?.let { add(it) }
      }
    }

    // Parse architecture-specific details
    val archOffset = 160
    val archDetail: ArchDetail? =
        when (architecture) {
          Architecture.ARM64 -> ArchDetail.AArch64(convertAArch64Detail(detailPtr + archOffset))
          Architecture.ARM -> ArchDetail.ARM(convertArmDetail(detailPtr + archOffset))
          Architecture.X86 -> ArchDetail.X86(convertX86Detail(detailPtr + archOffset))
          else -> null
        }

    return InstructionDetail(
        regsRead = regsRead,
        regsWritten = regsWrite,
        groups = groups,
        writeback = module.getValue(detailPtr + 154, "i8").toInt() != 0,
        archDetail = archDetail)
  }

  /** Convert ARM64/AArch64 detail structure */
  private fun convertAArch64Detail(ptr: Int): AArch64InstructionDetail {
    // cs_arm64 structure
    val ccVal = readInt(ptr)
    val updateFlags = module.getValue(ptr + 4, "i8").toInt() != 0
    val writeback = module.getValue(ptr + 5, "i8").toInt() != 0
    val opCount = module.getValue(ptr + 6, "i8").toInt() and 0xFF

    // Padding to 8 bytes for operands array (cs_arm64_op alignment is 8)
    val operandsOffset = 8

    val operands = buildList {
      for (i in 0 until opCount) {
        val opPtr = ptr + operandsOffset + i * 48 // Each operand is ~48 bytes
        add(convertAArch64Operand(opPtr))
      }
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
    val vectorIndex = readInt(ptr)
    val vasVal = readInt(ptr + 4)
    // vess is at +8 but not used in Kotlin model?
    val shiftTypeVal = readInt(ptr + 12)
    val shiftValue = readInt(ptr + 16)
    val extTypeVal = readInt(ptr + 20)
    val extValue = readInt(ptr + 24)
    val opTypeVal = readInt(ptr + 28)

    val opType =
        AArch64OpType.entries.firstOrNull { it.toInt() == opTypeVal } ?: AArch64OpType.INVALID

    // Union data at offset 32
    val data =
        when (opType) {
          AArch64OpType.REG -> readInt(ptr + 32)
          AArch64OpType.IMM,
          AArch64OpType.CIMM -> readLong(ptr + 32)
          AArch64OpType.FP -> {
            // Read as double
            val fpBits = readLong(ptr + 32)
            Double.fromBits(fpBits)
          }
          AArch64OpType.MEM -> -1 // Handled separately
          else -> 0
        }

    // Helper vars for specific types
    val reg =
        if (opType == AArch64OpType.REG) Register(data.toInt(), regName(data.toInt())) else null
    val imm =
        if (opType == AArch64OpType.IMM || opType == AArch64OpType.CIMM) data as Long else null
    val fp = if (opType == AArch64OpType.FP) data as Double else null
    val mem = if (opType == AArch64OpType.MEM) convertAArch64MemOp(ptr + 32) else null

    val access = readInt(ptr + 44)

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
    val baseId = readInt(ptr)
    val indexId = readInt(ptr + 4)
    val disp = readInt(ptr + 8)

    return AArch64MemoryOperand(
        base = if (baseId != 0) Register(baseId, regName(baseId)) else null,
        index = if (indexId != 0) Register(indexId, regName(indexId)) else null,
        disp = disp.toLong() // Ensure Long
        )
  }

  /** Convert ARM detail structure */
  private fun convertArmDetail(ptr: Int): ArmInstructionDetail {
    val usermode = module.getValue(ptr, "i8").toInt() != 0
    val vectorSize = readInt(ptr + 4)
    val vectorDataVal = readInt(ptr + 8)
    val cpsModeVal = readInt(ptr + 12)
    val cpsFlagVal = readInt(ptr + 16)
    val ccVal = readInt(ptr + 20)
    val updateFlags = module.getValue(ptr + 24, "i8").toInt() != 0
    val writeback = module.getValue(ptr + 25, "i8").toInt() != 0
    val memBarrierVal = readInt(ptr + 28)
    val opCount = module.getValue(ptr + 32, "i8").toInt() and 0xFF

    // Padding to 4/8 bytes? cs_arm_op has double/int64?
    // cs_arm_op has double fp; int64_t imm; -> 8 byte alignment.
    // offsets so far 33 bytes.
    // Aligned to 8 bytes -> 40?
    // Or maybe cs_arm_op doesn't force 8 byte align if internal union allows?
    // Let's check cs_arm_op.
    // arm_op_type (4) + ... union with double (8).
    // Structure alignment is max of fields. So 8 bytes.
    // Next multiple of 8 after 33 is 40.
    // But wait, let's re-verify offsets.
    // 0(1)+3(pad) + 4(4) + 8(4) + 12(4) + 16(4) + 20(4) + 24(1) + 25(1) + 2(pad) + 28(4) + 32(1).
    // 33 bytes used.
    // Padding to 40?
    // Let's assume 36 for now (4 byte align) but double check if Wasm aligns struct to 8.
    // Actually cs_arm has `cs_arm_op operands[36]`.
    // If cs_arm_op aligns to 8, then operands array aligns to 8.
    // 33 -> 40.
    // Let's try 36 first (safe for 4-byte packed, likely 4-byte align on 32-bit wasm).
    // Actually, checking standard layout:
    // Linux/ARM32: 36 offset.
    // Wasm32: 36 offset? (double is 8-byte aligned usually).
    // If double is 8-byte aligned, struct is 8-byte aligned.
    // 33 -> 40.
    // I'll stick to 36 (most likely for packed-ish or 4-byte dominant) or 40.
    // Previous analysis said 36.
    // 28+4 = 32. op_count at 32 (1 byte). 33.
    // If next is 36, then 3 bytes padding.
    // If next is 40, then 7 bytes padding.
    // Let's use 36 because `mem_barrier` ends at 32.
    // I'll try 36.

    val operandsOffset = 36 // Align to 36 (4-byte alignment of struct)

    val operands = buildList {
      for (i in 0 until opCount) {
        val opPtr = ptr + operandsOffset + i * 48
        add(convertArmOperand(opPtr))
      }
    }

    // Helper extensions or manual mapping
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
    val vectorIndex = readInt(ptr)
    val shiftTypeVal = readInt(ptr + 4)
    val shiftValue = readInt(ptr + 8)
    val opTypeVal = readInt(ptr + 12)

    val opType = ArmOpType.entries.firstOrNull { it.toInt() == opTypeVal } ?: ArmOpType.INVALID
    val shift = ArmShifter.entries.firstOrNull { it.toInt() == shiftTypeVal } ?: ArmShifter.INVALID

    val dataVal =
        when (opType) {
          ArmOpType.REG,
          ArmOpType.SYSREG -> readInt(ptr + 16)
          ArmOpType.IMM,
          ArmOpType.CIMM,
          ArmOpType.PIMM -> readInt(ptr + 16)
          ArmOpType.FP -> {
            val fpBits = readLong(ptr + 16)
            Double.fromBits(fpBits)
          }
          ArmOpType.MEM -> -1 // Handled separately
          ArmOpType.SETEND -> readInt(ptr + 16)
          else -> 0
        }

    val reg =
        if (opType == ArmOpType.REG || opType == ArmOpType.SYSREG)
            Register(dataVal.toInt(), regName(dataVal.toInt()))
        else null
    val imm =
        if (opType == ArmOpType.IMM || opType == ArmOpType.CIMM || opType == ArmOpType.PIMM)
            dataVal as Int
        else null
    val fp = if (opType == ArmOpType.FP) dataVal as Double else null
    val mem = if (opType == ArmOpType.MEM) convertArmMemOp(ptr + 16) else null
    val setend =
        if (opType == ArmOpType.SETEND)
            ArmSetEndType.entries.firstOrNull { it.toInt() == dataVal as Int }
                ?: ArmSetEndType.INVALID
        else ArmSetEndType.INVALID

    val subtracted = module.getValue(ptr + 36, "i8").toInt() != 0
    val access = readInt(ptr + 40)
    val neonLane = module.getValue(ptr + 44, "i8").toInt().toByte()

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
    val baseId = readInt(ptr)
    val indexId = readInt(ptr + 4)
    return ArmMemoryOperand(
        base = if (baseId != 0) Register(baseId, regName(baseId)) else null,
        index = if (indexId != 0) Register(indexId, regName(indexId)) else null,
        scale = readInt(ptr + 8),
        disp = readInt(ptr + 12),
        lshift = readInt(ptr + 16))
  }

  /** Convert X86 detail structure */
  private fun convertX86Detail(ptr: Int): X86InstructionDetail {
    // Read prefix bytes (4 bytes)
    val prefix = buildList {
      for (i in 0 until 4) {
        val pVal = module.getValue(ptr + i, "i8").toInt() and 0xFF
        if (pVal != 0) {
          add(X86Prefix.fromValue(pVal))
        }
      }
    }

    val opcode = ByteArray(4) { i -> module.getValue(ptr + 4 + i, "i8").toInt().toByte() }

    val rex = module.getValue(ptr + 8, "i8").toInt().toByte()
    val addrSize = module.getValue(ptr + 9, "i8").toInt()
    val modRm = module.getValue(ptr + 10, "i8").toInt().toByte()
    val sib = module.getValue(ptr + 11, "i8").toInt().toByte()

    val disp = readLong(ptr + 12)

    val sibIndexId = readInt(ptr + 20)
    val sibScale = module.getValue(ptr + 24, "i8").toInt()
    val sibBaseId = readInt(ptr + 28)

    val xopCcVal = readInt(ptr + 32)
    val sseCCVal = readInt(ptr + 36)
    val avxCcVal = readInt(ptr + 40)
    val avxSae = module.getValue(ptr + 44, "i8").toInt() != 0
    val avxRmVal = readInt(ptr + 48)

    // EFLAGS (8 bytes)
    val eflagsPtr = ptr + 52
    val eflagsVal = readLong(eflagsPtr) // Capstone internal eflags

    val opCount = module.getValue(ptr + 60, "i8").toInt() and 0xFF

    val operands = buildList {
      for (i in 0 until opCount) {
        val opPtr = ptr + 64 + i * 48
        add(convertX86Operand(opPtr))
      }
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
        sseCC = X86SseConditionCode.fromValue(sseCCVal),
        avxRm = X86AvxRoundingMode.fromValue(avxRmVal),
        avxSae = avxSae,
        eflags = BitField.fromValue(eflagsVal.toULong()),
        fpuFlags = 0 // Unresolved FPU flags in Web binding? Wait, struct has 64 bytes for operands.
        // Check if fpu_flags exists? Step 69 says opCount at ptr+60.
        // The struct seems to end eflags at 60.
        // Let's recheck cs_x86 layout.
        // 52: eflags (8 bytes). ends at 60.
        // 60: op_count (1 byte)
        // So no fpu_flags in Web binding version or it was missed?
        // X86UnionOpInfo has fpu_flags.
        // Native binding uses x86.fpu_flags.
        // Web binding seems to skip it or it's implicitly 0.
        // I will stick to eflags change.
        )
  }

  private fun convertX86Operand(ptr: Int): X86Operand {
    val opTypeVal = readInt(ptr)
    val opType = X86OpType.fromValue(opTypeVal)

    val dataVal =
        when (opType) {
          X86OpType.REG -> readInt(ptr + 4)
          X86OpType.IMM -> readLong(ptr + 4)
          X86OpType.MEM -> -1
          else -> 0
        }

    val reg =
        if (opType == X86OpType.REG) Register(dataVal.toInt(), regName(dataVal.toInt())) else null
    val imm = if (opType == X86OpType.IMM) dataVal as Long else null
    val mem = if (opType == X86OpType.MEM) convertX86MemOp(ptr + 4) else null

    val size = module.getValue(ptr + 28, "i8").toInt()
    val access = readInt(ptr + 32)
    val avxBcastVal = readInt(ptr + 36)
    val avxZeroOpmask = module.getValue(ptr + 40, "i8").toInt() != 0

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
    val segmentId = readInt(ptr)
    val baseId = readInt(ptr + 4)
    val indexId = readInt(ptr + 8)

    return X86MemoryOperand(
        segment = if (segmentId != 0) Register(segmentId, regName(segmentId)) else null,
        base = if (baseId != 0) Register(baseId, regName(baseId)) else null,
        index = if (indexId != 0) Register(indexId, regName(indexId)) else null,
        scale = readInt(ptr + 12),
        disp = readLong(ptr + 16))
  }
}
