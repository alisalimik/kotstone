package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Bti(override val value: Int) : INumericEnum {
  C(AARCH64_BTI_C),
  J(AARCH64_BTI_J),
  JC(AARCH64_BTI_JC),
  ENDING(AARCH64_BTI_ENDING),
}
