package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone AArch64 exact FP immediate. */
expect enum class AArch64ExactFpImm : INumericEnum {

  HALF,
  ONE,
  TWO,
  ZERO,

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_ExactFPImm> end
  INVALID,
  ENDING,
}
