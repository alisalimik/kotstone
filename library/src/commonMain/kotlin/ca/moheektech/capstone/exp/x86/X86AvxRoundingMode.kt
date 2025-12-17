package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

expect enum class X86AvxRoundingMode : INumericEnum {
  INVALID,
  RN,
  RD,
  RU,
  RZ;

  companion object {
    fun fromValue(value: Int): X86AvxRoundingMode
  }
}
