package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 instruction cache operation. */
expect enum class AArch64Ic : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_IC> begin
  // clang-format off
  IALLU,
  IALLUIS,
  IVAU,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_IC> end
  ENDING,
}
