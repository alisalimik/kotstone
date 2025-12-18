package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 range prefetch. */
expect enum class AArch64Rprfm : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_RPRFM> begin
  // clang-format off
  PLDKEEP,
  PLDSTRM,
  PSTKEEP,
  PSTSTRM,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_RPRFM> end
  ENDING,
}
