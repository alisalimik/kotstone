package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone AArch64 SVE vector length specifier. */
expect enum class AArch64SveVectorLengthSpecifier : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEVECLENSPECIFIER> begin
  // clang-format off
  VLX2,
  VLX4,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEVECLENSPECIFIER> end
  ENDING,
}
