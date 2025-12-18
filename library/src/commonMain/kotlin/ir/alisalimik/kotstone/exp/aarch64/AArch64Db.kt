package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 data barrier. */
expect enum class AArch64Db : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_DB> begin
  // clang-format off
  ISH,
  ISHLD,
  ISHST,
  LD,
  NSH,
  NSHLD,
  NSHST,
  OSH,
  OSHLD,
  OSHST,
  ST,
  SY,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_DB> end
  ENDING,
}
