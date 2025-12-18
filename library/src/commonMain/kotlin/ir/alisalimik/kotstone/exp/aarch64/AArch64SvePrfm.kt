package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 SVE prefetch. */
expect enum class AArch64SvePrfm : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEPRFM> begin
  // clang-format off
  PLDL1KEEP,
  PLDL1STRM,
  PLDL2KEEP,
  PLDL2STRM,
  PLDL3KEEP,
  PLDL3STRM,
  PSTL1KEEP,
  PSTL1STRM,
  PSTL2KEEP,
  PSTL2STRM,
  PSTL3KEEP,
  PSTL3STRM,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEPRFM> end
  ENDING,
}
