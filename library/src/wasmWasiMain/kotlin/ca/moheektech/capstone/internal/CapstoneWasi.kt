@file:Suppress("OPT_IN_USAGE", "KotlinInternalExternalFunction", "FunctionName")

package ca.moheektech.capstone.internal

import kotlin.wasm.WasmImport

private const val MODULE = "./capstone-bridge.mjs"

// Bridge Memory Accessors (See capstone-bridge.mjs)
@WasmImport(MODULE, "bridge_read_int8") private external fun bridge_read_int8(addr: Int): Byte

@WasmImport(MODULE, "bridge_read_int16") private external fun bridge_read_int16(addr: Int): Short

@WasmImport(MODULE, "bridge_read_int32") private external fun bridge_read_int32(addr: Int): Int

@WasmImport(MODULE, "bridge_write_int8")
private external fun bridge_write_int8(addr: Int, val_: Byte)

// Memory Management (Exported by Emscripten)
@WasmImport(MODULE, "_malloc") internal external fun _malloc(size: Int): Int

@WasmImport(MODULE, "_free") internal external fun _free(ptr: Int)

// Core Capstone
@WasmImport(MODULE, "_cs_open")
internal external fun _cs_open(arch: Int, mode: Int, handlePtr: Int): Int

@WasmImport(MODULE, "_cs_close") internal external fun _cs_close(handlePtr: Int): Int

@WasmImport(MODULE, "_cs_option")
internal external fun _cs_option(handle: Int, type: Int, value: Int): Int

@WasmImport(MODULE, "_cs_errno") internal external fun _cs_errno(handle: Int): Int

@WasmImport(MODULE, "_cs_strerror")
internal external fun _cs_strerror(code: Int): Int // Returns char*

// Disassembly
@WasmImport(MODULE, "_cs_disasm")
internal external fun _cs_disasm(
    handle: Int,
    codePtr: Int,
    codeLen: Int,
    address: Long,
    count: Int,
    insnPtrPtr: Int
): Int

@WasmImport(MODULE, "_cs_free") internal external fun _cs_free(insnPtr: Int, count: Int)

@WasmImport(MODULE, "_cs_malloc") internal external fun _cs_malloc(handle: Int): Int

// Details / Names
@WasmImport(MODULE, "_cs_reg_name") private external fun _cs_reg_name(handle: Int, regId: Int): Int

@WasmImport(MODULE, "_cs_insn_name")
private external fun _cs_insn_name(handle: Int, insnId: Int): Int

@WasmImport(MODULE, "_cs_group_name")
private external fun _cs_group_name(handle: Int, groupId: Int): Int

@WasmImport(MODULE, "_cs_insn_group")
private external fun _cs_insn_group(handle: Int, insnPtr: Int, groupId: Int): Boolean

@WasmImport(MODULE, "_cs_reg_read")
private external fun _cs_reg_read(handle: Int, insnPtr: Int, regId: Int): Boolean

@WasmImport(MODULE, "_cs_reg_write")
private external fun _cs_reg_write(handle: Int, insnPtr: Int, regId: Int): Boolean

@WasmImport(MODULE, "_cs_op_count")
private external fun _cs_op_count(handle: Int, insnPtr: Int, opType: Int): Int

@WasmImport(MODULE, "_cs_op_index")
private external fun _cs_op_index(handle: Int, insnPtr: Int, opType: Int, position: Int): Int

@WasmImport(MODULE, "_cs_regs_access")
private external fun _cs_regs_access(
    handle: Int,
    insnPtr: Int,
    regsRead: Int,
    readCount: Int,
    regsWrite: Int,
    writeCount: Int
): Int

// Architecture Registration
@WasmImport(MODULE, "_cs_arch_register_x86") external fun cs_arch_register_x86()

@WasmImport(MODULE, "_cs_arch_register_arm") external fun cs_arch_register_arm()

@WasmImport(MODULE, "_cs_arch_register_aarch64") external fun cs_arch_register_aarch64()

object CapstoneWasi {
  /**
   * Helpers using BRIDGE functions for safe memory access to foreign module. We do NOT use
   * UnsafeWasmMemory directly for reading foreign pointers.
   */
  internal fun loadInt32(ptr: Int): Int = bridge_read_int32(ptr)

  internal fun loadShort(ptr: Int): Short = bridge_read_int16(ptr)

  internal fun loadByte(ptr: Int): Byte = bridge_read_int8(ptr)

  internal fun loadLong(ptr: Int): Long {
    val lo = bridge_read_int32(ptr)
    val hi = bridge_read_int32(ptr + 4)
    return (hi.toLong() shl 32) or (lo.toLong() and 0xFFFFFFFFL)
  }

  internal fun writeBytes(ptr: Int, bytes: ByteArray) {
    for (i in bytes.indices) {
      bridge_write_int8(ptr + i, bytes[i])
    }
  }

  fun readCString(ptr: Int): String {
    if (ptr == 0) return ""
    var len = 0
    while (bridge_read_int8(ptr + len) != 0.toByte()) {
      len++
    }
    val bytes = ByteArray(len)
    for (i in 0 until len) {
      bytes[i] = bridge_read_int8(ptr + i)
    }
    return bytes.decodeToString()
  }

  // Expose raw wrappers if needed
  fun getErrorName(code: Int): String = readCString(_cs_strerror(code))

  fun getRegName(handle: Int, regId: Int): String = readCString(_cs_reg_name(handle, regId))

  fun getInsnName(handle: Int, insnId: Int): String = readCString(_cs_insn_name(handle, insnId))
}
