package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 PRFM. */
expect enum class AArch64Prfm : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_PRFM> begin
  // clang-format off
  PLDL1KEEP,
  PLDL1STRM,
  PLDL2KEEP,
  PLDL2STRM,
  PLDL3KEEP,
  PLDL3STRM,
  PLDSLCKEEP,
  PLDSLCSTRM,
  PLIL1KEEP,
  PLIL1STRM,
  PLIL2KEEP,
  PLIL2STRM,
  PLIL3KEEP,
  PLIL3STRM,
  PLISLCKEEP,
  PLISLCSTRM,
  PSTL1KEEP,
  PSTL1STRM,
  PSTL2KEEP,
  PSTL2STRM,
  PSTL3KEEP,
  PSTL3STRM,
  PSTSLCKEEP,
  PSTSLCSTRM,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_PRFM> end
  ENDING,
}
