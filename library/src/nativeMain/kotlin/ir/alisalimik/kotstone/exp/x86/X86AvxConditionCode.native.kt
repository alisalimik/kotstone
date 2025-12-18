package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.X86_AVX_CC_EQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_EQ_OS
import ir.alisalimik.kotstone.internal.X86_AVX_CC_EQ_UQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_EQ_US
import ir.alisalimik.kotstone.internal.X86_AVX_CC_FALSE
import ir.alisalimik.kotstone.internal.X86_AVX_CC_FALSE_OS
import ir.alisalimik.kotstone.internal.X86_AVX_CC_GE
import ir.alisalimik.kotstone.internal.X86_AVX_CC_GE_OQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_GT
import ir.alisalimik.kotstone.internal.X86_AVX_CC_GT_OQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_INVALID
import ir.alisalimik.kotstone.internal.X86_AVX_CC_LE
import ir.alisalimik.kotstone.internal.X86_AVX_CC_LE_OQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_LT
import ir.alisalimik.kotstone.internal.X86_AVX_CC_LT_OQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NEQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NEQ_OQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NEQ_OS
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NEQ_US
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NGE
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NGE_UQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NGT
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NGT_UQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NLE
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NLE_UQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NLT
import ir.alisalimik.kotstone.internal.X86_AVX_CC_NLT_UQ
import ir.alisalimik.kotstone.internal.X86_AVX_CC_ORD
import ir.alisalimik.kotstone.internal.X86_AVX_CC_ORD_S
import ir.alisalimik.kotstone.internal.X86_AVX_CC_TRUE
import ir.alisalimik.kotstone.internal.X86_AVX_CC_TRUE_US
import ir.alisalimik.kotstone.internal.X86_AVX_CC_UNORD
import ir.alisalimik.kotstone.internal.X86_AVX_CC_UNORD_S

actual enum class X86AvxConditionCode(override val value: UInt) : INumericEnum {
  INVALID(X86_AVX_CC_INVALID),
  EQ(X86_AVX_CC_EQ),
  LT(X86_AVX_CC_LT),
  LE(X86_AVX_CC_LE),
  UNORD(X86_AVX_CC_UNORD),
  NEQ(X86_AVX_CC_NEQ),
  NLT(X86_AVX_CC_NLT),
  NLE(X86_AVX_CC_NLE),
  ORD(X86_AVX_CC_ORD),
  EQ_UQ(X86_AVX_CC_EQ_UQ),
  NGE(X86_AVX_CC_NGE),
  NGT(X86_AVX_CC_NGT),
  FALSE(X86_AVX_CC_FALSE),
  NEQ_OQ(X86_AVX_CC_NEQ_OQ),
  GE(X86_AVX_CC_GE),
  GT(X86_AVX_CC_GT),
  TRUE(X86_AVX_CC_TRUE),
  EQ_OS(X86_AVX_CC_EQ_OS),
  LT_OQ(X86_AVX_CC_LT_OQ),
  LE_OQ(X86_AVX_CC_LE_OQ),
  UNORD_S(X86_AVX_CC_UNORD_S),
  NEQ_US(X86_AVX_CC_NEQ_US),
  NLT_UQ(X86_AVX_CC_NLT_UQ),
  NLE_UQ(X86_AVX_CC_NLE_UQ),
  ORD_S(X86_AVX_CC_ORD_S),
  EQ_US(X86_AVX_CC_EQ_US),
  NGE_UQ(X86_AVX_CC_NGE_UQ),
  NGT_UQ(X86_AVX_CC_NGT_UQ),
  FALSE_OS(X86_AVX_CC_FALSE_OS),
  NEQ_OS(X86_AVX_CC_NEQ_OS),
  GE_OQ(X86_AVX_CC_GE_OQ),
  GT_OQ(X86_AVX_CC_GT_OQ),
  TRUE_US(X86_AVX_CC_TRUE_US);

  actual companion object {
    actual fun fromValue(value: Int): X86AvxConditionCode {
      return entries.firstOrNull { it.value == value.toUInt() } ?: INVALID
    }
  }
}
