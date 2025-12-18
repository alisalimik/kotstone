package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class X86OpType : INumericEnum {
  INVALID,
  REG,
  IMM,
  MEM;

  companion object {
    fun fromValue(value: Int): X86OpType
  }
}
