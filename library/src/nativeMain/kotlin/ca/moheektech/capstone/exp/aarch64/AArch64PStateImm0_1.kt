package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.internal.*
import ca.moheektech.capstone.exp.INumericEnum

actual enum class AArch64PStateImm0_1(override val value: UInt): INumericEnum {
    ALLINT(AARCH64_PSTATEIMM0_1_ALLINT), PM(AARCH64_PSTATEIMM0_1_PM), ENDING(AARCH64_PSTATEIMM0_1_ENDING),
}
