package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

@ExportedApi
actual enum class AArch64DbNxs(override val value: Int) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_DBnXS> begin
  // clang-format off
  ISHNXS(AARCH64_DBNXS_ISHNXS),
  NSHNXS(AARCH64_DBNXS_NSHNXS),
  OSHNXS(AARCH64_DBNXS_OSHNXS),
  SYNXS(AARCH64_DBNXS_SYNXS),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_DBnXS> end
  ENDING(AARCH64_DBNXS_ENDING),
}
