package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 SVE predicate pattern. */
expect enum class AArch64SvePredPattern : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEPREDPAT> begin
  // clang-format off
  ALL,
  MUL3,
  MUL4,
  POW2,
  VL1,
  VL128,
  VL16,
  VL2,
  VL256,
  VL3,
  VL32,
  VL4,
  VL5,
  VL6,
  VL64,
  VL7,
  VL8,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVEPREDPAT> end
  ENDING,
}
