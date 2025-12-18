package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class X86SseConditionCode(override val value: Int) : INumericEnum {
  INVALID(X86_SSE_CC_INVALID), // /< Uninitialized.
  EQ(X86_SSE_CC_EQ),
  LT(X86_SSE_CC_LT),
  LE(X86_SSE_CC_LE),
  UNORD(X86_SSE_CC_UNORD),
  NEQ(X86_SSE_CC_NEQ),
  NLT(X86_SSE_CC_NLT),
  NLE(X86_SSE_CC_NLE),
  ORD(X86_SSE_CC_ORD);

  actual companion object {
    actual fun fromValue(value: Int): X86SseConditionCode {
      return entries.firstOrNull { it.value == value } ?: INVALID
    }
  }
}
