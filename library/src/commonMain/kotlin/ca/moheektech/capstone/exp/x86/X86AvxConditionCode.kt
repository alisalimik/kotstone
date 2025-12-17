package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone X86 SSE condition code. */
expect enum class X86AvxConditionCode : INumericEnum {
  INVALID, /// < Uninitialized.
  EQ,
  LT,
  LE,
  UNORD,
  NEQ,
  NLT,
  NLE,
  ORD,
  EQ_UQ,
  NGE,
  NGT,
  FALSE,
  NEQ_OQ,
  GE,
  GT,
  TRUE,
  EQ_OS,
  LT_OQ,
  LE_OQ,
  UNORD_S,
  NEQ_US,
  NLT_UQ,
  NLE_UQ,
  ORD_S,
  EQ_US,
  NGE_UQ,
  NGT_UQ,
  FALSE_OS,
  NEQ_OS,
  GE_OQ,
  GT_OQ,
  TRUE_US;

    companion object {
        fun fromValue(value: Int): X86AvxConditionCode
    }
}
