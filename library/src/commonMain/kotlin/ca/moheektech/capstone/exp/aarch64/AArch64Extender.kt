package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone AArch64 extender.
 */
expect enum class AArch64Extender : INumericEnum {
    INVALID, UXTB, UXTH, UXTW, UXTX,
    SXTB, SXTH, SXTW, SXTX,
}
