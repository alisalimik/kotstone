package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Db(override val value: UInt) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_DB> begin
  // clang-format off
  ISH(AARCH64_DB_ISH),
  ISHLD(AARCH64_DB_ISHLD),
  ISHST(AARCH64_DB_ISHST),
  LD(AARCH64_DB_LD),
  NSH(AARCH64_DB_NSH),
  NSHLD(AARCH64_DB_NSHLD),
  NSHST(AARCH64_DB_NSHST),
  OSH(AARCH64_DB_OSH),
  OSHLD(AARCH64_DB_OSHLD),
  OSHST(AARCH64_DB_OSHST),
  ST(AARCH64_DB_ST),
  SY(AARCH64_DB_SY),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_DB> end
  ENDING(AARCH64_DB_ENDING),
}
