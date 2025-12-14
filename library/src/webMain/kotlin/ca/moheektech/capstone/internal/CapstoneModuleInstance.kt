package ca.moheektech.capstone.internal

import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.JsBigInt
import kotlin.js.JsNumber
import kotlin.js.JsString
// Removed org.khronos.webgl imports to avoid resolution issues in shared code

external interface CapstoneModuleInstance : JsAny {

    // =========================================================================
    // Emscripten Runtime & Memory
    // =========================================================================

    // Properties removed to avoid Int8Array dependency in shared code.
    // We use getValue/setValue for memory access.

    // Standard C Allocator
    fun _malloc(size: Int): Int
    fun _free(ptr: Int)

    // Stack helpers (Advanced usage)
    fun __emscripten_stack_save(): Int
    fun __emscripten_stack_restore(ptr: Int)
    fun __emscripten_stack_alloc(size: Int): Int
    fun _emscripten_stack_get_current(): Int

    // JS Interop Tools
    fun ccall(ident: String, returnType: String?, argTypes: JsArray<JsString>?, args: JsArray<JsAny?>?): JsAny?
    fun cwrap(ident: String, returnType: String?, argTypes: JsArray<JsString>?): JsAny
    fun setValue(ptr: Int, value: JsAny, type: String)
    fun getValue(ptr: Int, type: String): JsAny // Returns JsAny (Number)
    fun UTF8ToString(ptr: Int): String
    fun writeArrayToMemory(array: JsAny, buffer: Int)
    fun addFunction(func: JsAny, signature: String): Int
    fun removeFunction(index: Int)

    // =========================================================================
    // Core Capstone API
    // =========================================================================

    // Basic Management
    fun _cs_version(major: Int, minor: Int): Int
    fun _cs_support(query: Int): Boolean
    fun _cs_open(arch: Int, mode: Int, handle: Int): Int
    fun _cs_close(handle: Int): Int
    fun _cs_option(handle: Int, type: Int, value: Int): Int
    fun _cs_errno(handle: Int): Int
    fun _cs_strerror(code: Int): Int // Returns char* (pointer to string)

    // Disassembly
    // Note: `address` is uint64_t. In Wasm/JS this is typically passed as a Long (BigInt).
    // However, JS interop does not support Long directly in all cases for Wasm.
    // We might need to split it or pass as BigInt if supported.
    // For now, let's keep Long and see if Kotlin/Wasm maps it to BigInt automatically.
    // If not, we might need `address: JsBigInt` or similar.
    // UPDATE: Kotlin/Wasm treats Long as BigInt in interop.
    fun _cs_disasm(handle: Int, code: Int, code_len: Int, address: JsBigInt, count: Int, insn: Int): Int
    fun _cs_disasm_iter(handle: Int, code: Int, code_len: Int, address: Int, insn: Int): Boolean
    fun _cs_free(insn: Int, count: Int)
    fun _cs_malloc(handle: Int): Int

    // Info & Details
    fun _cs_reg_name(handle: Int, reg_id: Int): Int // Returns char*
    fun _cs_insn_name(handle: Int, insn_id: Int): Int // Returns char*
    fun _cs_group_name(handle: Int, group_id: Int): Int // Returns char*
    fun _cs_insn_group(handle: Int, insn: Int, group_id: Int): Boolean
    fun _cs_reg_read(handle: Int, insn: Int, reg_id: Int): Boolean
    fun _cs_reg_write(handle: Int, insn: Int, reg_id: Int): Boolean
    fun _cs_op_count(handle: Int, insn: Int, op_type: Int): Int
    fun _cs_op_index(handle: Int, insn: Int, op_type: Int, position: Int): Int
    fun _cs_regs_access(handle: Int, insn: Int, regs_read: Int, read_count: Int, regs_write: Int, write_count: Int): Int

    // Utilities
    fun _cs_strdup(str: Int): Int // Returns char*
    // _cs_snprintf is variadic in C, which is hard to map directly in Kotlin interfaces.
    // You usually use ccall for variadic functions.
    // fun _cs_snprintf(buffer: Int, size: Int, format: Int): Int

    // =========================================================================
    // Architecture Registration (Enable/Disable specific archs)
    // =========================================================================
    fun _cs_arch_register_arm()
    fun _cs_arch_register_aarch64()
    fun _cs_arch_register_mips()
    fun _cs_arch_register_x86()
    fun _cs_arch_register_powerpc()
    fun _cs_arch_register_sparc()
    fun _cs_arch_register_systemz()
    fun _cs_arch_register_xcore()
    fun _cs_arch_register_m68k()
    fun _cs_arch_register_tms320c64x()
    fun _cs_arch_register_m680x()
    fun _cs_arch_register_evm()
    fun _cs_arch_register_mos65xx()
    fun _cs_arch_register_wasm()
    fun _cs_arch_register_bpf()
    fun _cs_arch_register_riscv()
    fun _cs_arch_register_sh()
    fun _cs_arch_register_tricore()
    fun _cs_arch_register_alpha()
    fun _cs_arch_register_loongarch()
    fun _cs_arch_register_arc()
}