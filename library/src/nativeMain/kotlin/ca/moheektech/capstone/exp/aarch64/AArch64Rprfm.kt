package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Rprfm(override val value: UInt) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_RPRFM> begin
  // clang-format off
  PLDKEEP(AARCH64_RPRFM_PLDKEEP),
  PLDSTRM(AARCH64_RPRFM_PLDSTRM),
  PSTKEEP(AARCH64_RPRFM_PSTKEEP),
  PSTSTRM(AARCH64_RPRFM_PSTSTRM),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_RPRFM> end
  ENDING(AARCH64_RPRFM_ENDING),
}
