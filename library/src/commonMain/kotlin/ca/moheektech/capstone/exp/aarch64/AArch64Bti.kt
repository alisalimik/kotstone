package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone AArch64 BTI. */
expect enum class AArch64Bti : INumericEnum {
  C,
  J,
  JC,
  ENDING,
}
