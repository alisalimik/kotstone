package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone AArch64 trace synchronization barrier.
 */
expect enum class AArch64Tsb : INumericEnum {
    CSYNC,
    ENDING,
}