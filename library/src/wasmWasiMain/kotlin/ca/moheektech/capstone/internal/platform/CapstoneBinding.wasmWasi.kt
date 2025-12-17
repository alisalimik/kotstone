package ca.moheektech.capstone.internal.platform

import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.InternalInstruction
import ca.moheektech.capstone.api.DisassemblyPosition
import ca.moheektech.capstone.bit.BitField
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.CapstoneOption
import ca.moheektech.capstone.enums.Mode
import ca.moheektech.capstone.error.*
import ca.moheektech.capstone.internal.*

/**
 * WASI-compatible Capstone binding implementation. Uses direct pointer memory access via
 * UnsafeWasmMemory and CapstoneWasi raw functions.
 */
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
          architecture, "Failed to open Capstone: $errorMsg")
    }
    handle = CapstoneWasi.loadInt32(handlePtr)
  }

  override fun disasm(
      code: ByteArray,
      address: Long,
      count: Int
  ): CapstoneResult<List<Instruction>> = runCatching {
    // Allocate code buffer
    val codePtr = _malloc(code.size)
    CapstoneWasi.writeBytes(codePtr, code)

    val insnPtrPtr = _malloc(4) // Pointer to receive cs_insn*

    try {
      // 5. Run Disassembly
      val disasmCount = _cs_disasm(handle, codePtr, code.size, address, 0, insnPtrPtr)

      if (disasmCount <= 0) {
        val errno = _cs_errno(handle)
        throw RuntimeException(
            "DEBUG: cs_disasm returned 0. Errno: $errno. CodeSize: ${code.size}. Handle: $handle")
      }

      val result = mutableListOf<String>()

      if (disasmCount > 0) {
        val instructions = ArrayList<Instruction>(disasmCount)
        val insnArrPtr = CapstoneWasi.loadInt32(insnPtrPtr)

        // cs_insn struct usage for 32-bit WASM (Emscripten)
        // Layout assumptions:
        // 0: id (4)
        // 4: pad (4)
        // 8: address (8)
        // 16: size (2)
        // 18: bytes (24)
        // 42: mnemonic (32)
        // 74: op_str (160)
        // 234: pad (2)
        // 236: detail (4)
        // Size: ~240 bytes

        val STRUCT_SIZE = 240 // Estimate
        // Or we can rely on _cs_malloc output? No.
        // Better: The array is contiguous? Yes, cs_disasm returns array.

        for (i in 0 until disasmCount) {
          val ptr = insnArrPtr + (i * STRUCT_SIZE)

          // DEBUG: Dump memory around ptr to find offsets
          val dumpSize = 100
          var dump = ""
          for (k in 0 until dumpSize) {
            val b = UnsafeWasmMemory.memory.loadByte(ptr + k).toInt() and 0xFF
            dump += "${b.toString(16).padStart(2, '0')} "
          }

          val id = CapstoneWasi.loadInt32(ptr)
          // address at ptr + 16
          val address = CapstoneWasi.loadLong(ptr + 16)
          // size at ptr + 24
          val size = CapstoneWasi.loadShort(ptr + 24).toInt() and 0xFFFF

          // bytes at ptr + 26
          val validSize = if (size > 24) 24 else size
          val bytes = ByteArray(validSize)
          for (b in 0 until validSize) {
            bytes[b] = CapstoneWasi.loadByte(ptr + 26 + b)
          }

          // mnemonic at ptr + 50
          val mnemonic = CapstoneWasi.readCString(ptr + 50)

          // op_str at ptr + 82
          val opStr = CapstoneWasi.readCString(ptr + 82)

          instructions.add(
              InternalInstruction(
                  id = id,
                  address = address,
                  size = size,
                  bytes = bytes,
                  mnemonic = mnemonic,
                  opStr = opStr,
                  detail = null // TODO: Parsing detail is much clearer with offsets
                  ))
        }

        _cs_free(insnArrPtr, disasmCount)
        instructions
      } else {
        val lastErr = lastError()
        if (lastErr != ErrorCode.OK) {
          throw lastErr.toError(architecture, mode)
        }
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
    // We use _cs_disasm with count=1 to simulate iteration easily
    // This avoids managing pointer-to-pointers for cs_disasm_iter which is tricky in Wasm without a
    // helper

    // Calculate remaining code
    val remainingSize = position.remaining(code.size)
    if (remainingSize <= 0) return@runCatching null

    // Slice code buffer? _cs_disasm consumes array pointer.
    // We need to pass the specific slice of bytes.
    // Or write only the remaining bytes to the buffer.
    val slice = code.copyOfRange(position.offset, code.size)
    // Optimization: Could write full buffer once and just pass offset pointer?
    // _cs_disasm takes 'code' pointer and 'code_len'.
    // If we wrote the full buffer at start, we could just pass codePtr + offset.
    // But codePtr is allocated locally.

    val codePtr = _malloc(remainingSize)
    CapstoneWasi.writeBytes(codePtr, slice) // This writes to 0...len

    val insnPtrPtr = _malloc(4)

    var resultInsn: Instruction? = null

    try {
      val count = _cs_disasm(handle, codePtr, remainingSize, position.address, 1, insnPtrPtr)

      if (count > 0) {
        val insnArrPtr = CapstoneWasi.loadInt32(insnPtrPtr)
        val ptr = insnArrPtr // 0th element

        // Decoding logic
        // Layout (Observed via Dump):
        // 0: id (4)
        // 16: address (8) (Aligned to 16?)
        // 24: size (2)
        // 26: bytes (24)
        // 50: mnemonic (32) (Heuristic adjustment: 52 gave 'd', so -2 to find 'a')
        // 82: op_str (160) (Start at 82, where 'w0...' was found)

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

        resultInsn = InternalInstruction(id, address, size, bytes, mnemonic, opStr, null)

        // Update position
        position.advance(size)

        _cs_free(insnArrPtr, count)
      }
    } finally {
      _free(codePtr)
      _free(insnPtrPtr)
    }

    resultInsn
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
  // Need _cs_support exposed
  return true
}

internal actual fun getPlatformVersion(): Pair<Int, Int> {
  // Static version check
  return Pair(0, 0)
}
