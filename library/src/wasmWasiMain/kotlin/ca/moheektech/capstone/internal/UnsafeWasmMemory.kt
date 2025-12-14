package ca.moheektech.capstone.internal

import kotlin.wasm.unsafe.*

/**
 * Helper for unsafe memory access in WASI.
 * Wraps kotlin.wasm.unsafe.Pointer calls.
 */
@OptIn(UnsafeWasmMemoryApi::class)
internal object UnsafeWasmMemory {
    val memory = MemoryAccess

    object MemoryAccess {
        val buffer = BufferAccess // Placeholder if needed

        fun loadByte(addr: Int): Byte = Pointer(addr.toUInt()).loadByte()
        fun loadInt32(addr: Int): Int = Pointer(addr.toUInt()).loadInt()
        fun storeByte(addr: Int, value: Byte) = Pointer(addr.toUInt()).storeByte(value)
        
        // Add others as needed
        fun loadShort(addr: Int): Short = Pointer(addr.toUInt()).loadShort()
        fun loadLong(addr: Int): Long = Pointer(addr.toUInt()).loadLong()
    }
    
    object BufferAccess {
        // usage: UnsafeWasmMemory.memory.buffer.storeByte(ptr + i, bytes[i])
        fun storeByte(addr: Int, value: Byte) = MemoryAccess.storeByte(addr, value)
    }
}
