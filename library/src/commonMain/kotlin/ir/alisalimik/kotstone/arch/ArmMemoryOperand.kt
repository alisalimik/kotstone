package ir.alisalimik.kotstone.arch

import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.Register

/** ARM memory operand. */
@ExportedApi
data class ArmMemoryOperand(
    val base: Register? = null,
    val index: Register? = null,
    val scale: Int = 1,
    val disp: Int = 0,

    /** Lshift for memory index */
    val lshift: Int = 0
)
