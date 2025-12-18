package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class AArch64SveVectorLengthSpecifier(override val value: Int) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEVECLENSPECIFIER> begin
  // clang-format off
  VLX2(AARCH64_SVEVECLENSPECIFIER_VLX2),
  VLX4(AARCH64_SVEVECLENSPECIFIER_VLX4),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEVECLENSPECIFIER> end
  ENDING(AARCH64_SVEVECLENSPECIFIER_ENDING),
}
