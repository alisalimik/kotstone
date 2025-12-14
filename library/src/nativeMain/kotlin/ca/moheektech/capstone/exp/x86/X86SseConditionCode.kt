package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class X86SseConditionCode(override val value: UInt): INumericEnum {

    CC_INVALID(X86_SSE_CC_INVALID),
    CC_EQ(X86_SSE_CC_EQ),
    CC_LT(X86_SSE_CC_LT),
    CC_LE(X86_SSE_CC_LE),
    CC_UNORD(X86_SSE_CC_UNORD),
    CC_NEQ(X86_SSE_CC_NEQ),
    CC_NLT(X86_SSE_CC_NLT),
    CC_NLE(X86_SSE_CC_NLE),
    CC_ORD(X86_SSE_CC_ORD),

}
