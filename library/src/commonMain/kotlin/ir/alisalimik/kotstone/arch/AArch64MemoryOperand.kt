package ir.alisalimik.kotstone.arch

import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.Register

/** AArch64 memory operand. */
@ExportedApi
data class AArch64MemoryOperand(
    val base: Register? = null,
    val index: Register? = null,
    val disp: Long = 0
)
