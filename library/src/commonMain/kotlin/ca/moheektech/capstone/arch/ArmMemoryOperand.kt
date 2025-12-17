package ca.moheektech.capstone.arch

import ca.moheektech.capstone.internal.ExportedApi
import ca.moheektech.capstone.model.Register

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
