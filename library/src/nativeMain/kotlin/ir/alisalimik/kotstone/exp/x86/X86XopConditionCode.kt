package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class X86XopConditionCode(override val value: UInt) : INumericEnum {

  INVALID(X86_XOP_CC_INVALID), // /< Uninitialized.
  LT(X86_XOP_CC_LT),
  LE(X86_XOP_CC_LE),
  GT(X86_XOP_CC_GT),
  GE(X86_XOP_CC_GE),
  EQ(X86_XOP_CC_EQ),
  NEQ(X86_XOP_CC_NEQ),
  FALSE(X86_XOP_CC_FALSE),
  TRUE(X86_XOP_CC_TRUE),
}
