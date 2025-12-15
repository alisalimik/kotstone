package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Psb(override val value: Int) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_PSB> begin
  // clang-format off
  CSYNC(AARCH64_PSB_CSYNC),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_PSB> end
  ENDING(AARCH64_PSB_ENDING),
}
