package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class X86XopConditionCode(override val value: Int): INumericEnum {

    CC_INVALID(X86_XOP_CC_INVALID), ///< Uninitialized.
    CC_LT(X86_XOP_CC_LT),
    CC_LE(X86_XOP_CC_LE),
    CC_GT(X86_XOP_CC_GT),
    CC_GE(X86_XOP_CC_GE),
    CC_EQ(X86_XOP_CC_EQ),
    CC_NEQ(X86_XOP_CC_NEQ),
    CC_FALSE(X86_XOP_CC_FALSE),
    CC_TRUE(X86_XOP_CC_TRUE),

}