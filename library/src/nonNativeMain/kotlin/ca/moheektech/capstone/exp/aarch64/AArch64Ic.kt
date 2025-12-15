package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Ic(override val value: Int) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_IC> begin
  // clang-format off
  IALLU(AARCH64_IC_IALLU),
  IALLUIS(AARCH64_IC_IALLUIS),
  IVAU(AARCH64_IC_IVAU),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_IC> end
  ENDING(AARCH64_IC_ENDING),
}
