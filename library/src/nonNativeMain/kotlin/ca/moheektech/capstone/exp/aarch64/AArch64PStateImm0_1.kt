package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64PStateImm0_1(override val value: Int) : INumericEnum {
  ALLINT(AARCH64_PSTATEIMM0_1_ALLINT),
  PM(AARCH64_PSTATEIMM0_1_PM),
  ENDING(AARCH64_PSTATEIMM0_1_ENDING),
}
