package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone AArch64 PState immediate 0-1.
 */
expect enum class AArch64PStateImm0_1 : INumericEnum {
    ALLINT, PM, ENDING,
}