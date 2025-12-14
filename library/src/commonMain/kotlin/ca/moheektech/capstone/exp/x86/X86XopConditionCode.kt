package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone X86 XOP condition code.
 */
expect enum class X86XopConditionCode : INumericEnum {

    CC_INVALID, ///< Uninitialized.
    CC_LT,
    CC_LE,
    CC_GT,
    CC_GE,
    CC_EQ,
    CC_NEQ,
    CC_FALSE,
    CC_TRUE,

}