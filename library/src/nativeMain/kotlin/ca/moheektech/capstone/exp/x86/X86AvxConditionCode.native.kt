package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.X86_AVX_CC_EQ
import ca.moheektech.capstone.internal.X86_AVX_CC_EQ_OS
import ca.moheektech.capstone.internal.X86_AVX_CC_EQ_UQ
import ca.moheektech.capstone.internal.X86_AVX_CC_EQ_US
import ca.moheektech.capstone.internal.X86_AVX_CC_FALSE
import ca.moheektech.capstone.internal.X86_AVX_CC_FALSE_OS
import ca.moheektech.capstone.internal.X86_AVX_CC_GE
import ca.moheektech.capstone.internal.X86_AVX_CC_GE_OQ
import ca.moheektech.capstone.internal.X86_AVX_CC_GT
import ca.moheektech.capstone.internal.X86_AVX_CC_GT_OQ
import ca.moheektech.capstone.internal.X86_AVX_CC_INVALID
import ca.moheektech.capstone.internal.X86_AVX_CC_LE
import ca.moheektech.capstone.internal.X86_AVX_CC_LE_OQ
import ca.moheektech.capstone.internal.X86_AVX_CC_LT
import ca.moheektech.capstone.internal.X86_AVX_CC_LT_OQ
import ca.moheektech.capstone.internal.X86_AVX_CC_NEQ
import ca.moheektech.capstone.internal.X86_AVX_CC_NEQ_OQ
import ca.moheektech.capstone.internal.X86_AVX_CC_NEQ_OS
import ca.moheektech.capstone.internal.X86_AVX_CC_NEQ_US
import ca.moheektech.capstone.internal.X86_AVX_CC_NGE
import ca.moheektech.capstone.internal.X86_AVX_CC_NGE_UQ
import ca.moheektech.capstone.internal.X86_AVX_CC_NGT
import ca.moheektech.capstone.internal.X86_AVX_CC_NGT_UQ
import ca.moheektech.capstone.internal.X86_AVX_CC_NLE
import ca.moheektech.capstone.internal.X86_AVX_CC_NLE_UQ
import ca.moheektech.capstone.internal.X86_AVX_CC_NLT
import ca.moheektech.capstone.internal.X86_AVX_CC_NLT_UQ
import ca.moheektech.capstone.internal.X86_AVX_CC_ORD
import ca.moheektech.capstone.internal.X86_AVX_CC_ORD_S
import ca.moheektech.capstone.internal.X86_AVX_CC_TRUE
import ca.moheektech.capstone.internal.X86_AVX_CC_TRUE_US
import ca.moheektech.capstone.internal.X86_AVX_CC_UNORD
import ca.moheektech.capstone.internal.X86_AVX_CC_UNORD_S

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
