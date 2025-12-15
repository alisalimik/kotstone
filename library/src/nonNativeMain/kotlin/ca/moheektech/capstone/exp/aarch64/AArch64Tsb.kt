package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Tsb(override val value: Int) : INumericEnum {
  CSYNC(AARCH64_TSB_CSYNC),
  ENDING(AARCH64_TSB_ENDING),
}
