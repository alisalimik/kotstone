package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone X86 SSE condition code.
 */
expect enum class X86SseConditionCode : INumericEnum {

    CC_INVALID, ///< Uninitialized.
    CC_EQ,
    CC_LT,
    CC_LE,
    CC_UNORD,
    CC_NEQ,
    CC_NLT,
    CC_NLE,
    CC_ORD,

}
