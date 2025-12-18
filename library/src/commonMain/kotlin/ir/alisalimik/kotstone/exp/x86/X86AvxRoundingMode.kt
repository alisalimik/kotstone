package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum

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
