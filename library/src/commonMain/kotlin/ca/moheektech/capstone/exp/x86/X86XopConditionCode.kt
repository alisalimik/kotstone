package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone X86 XOP condition code. */
expect enum class X86XopConditionCode : INumericEnum {

  INVALID, /// < Uninitialized.
  LT,
  LE,
  GT,
  GE,
  EQ,
  NEQ,
  FALSE,
  TRUE,
}
