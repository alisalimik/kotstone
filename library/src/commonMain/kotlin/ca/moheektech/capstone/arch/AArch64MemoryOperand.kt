package ca.moheektech.capstone.arch

import ca.moheektech.capstone.internal.ExportedApi
import ca.moheektech.capstone.model.Register

/** AArch64 memory operand. */
@ExportedApi
data class AArch64MemoryOperand(
    val base: Register? = null,
    val index: Register? = null,
    val disp: Long = 0
)
