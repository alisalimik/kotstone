package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 BTI. */
expect enum class AArch64Bti : INumericEnum {
  C,
  J,
  JC,
  ENDING,
}
