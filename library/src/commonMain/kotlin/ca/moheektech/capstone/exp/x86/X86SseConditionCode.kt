package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone X86 SSE condition code. */
expect enum class X86SseConditionCode : INumericEnum {
  INVALID,
  EQ,
  LT,
  LE,
  UNORD,
  NEQ,
  NLT,
  NLE,
  ORD;

    companion object {
        fun fromValue(value: Int): X86SseConditionCode
    }
}
