package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

expect enum class X86OpType : INumericEnum {
  INVALID,
  REG,
  IMM,
  MEM;

    companion object {
        fun fromValue(value: Int): X86OpType
    }
}
