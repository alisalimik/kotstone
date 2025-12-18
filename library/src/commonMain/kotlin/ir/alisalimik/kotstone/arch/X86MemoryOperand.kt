package ir.alisalimik.kotstone.arch

import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.Register

/** X86 memory operand. */
@ExportedApi
data class X86MemoryOperand(
    val segment: Register? = null,
    val base: Register? = null,
    val index: Register? = null,
    val scale: Int = 1,
    val disp: Long = 0
)
