package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class AArch64Shifter : INumericEnum {
  INVALID,
  LSL,
  MSL,
  LSR,
  ASR,
  ROR,
  LSL_REG,
  MSL_REG,
  LSR_REG,
  ASR_REG,
  ROR_REG,
}
