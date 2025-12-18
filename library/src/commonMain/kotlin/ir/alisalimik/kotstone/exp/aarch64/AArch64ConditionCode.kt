package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class AArch64ConditionCode : INumericEnum {
  // Meaning  Meaning
  EQ, // Equal                      Equal
  NE, // Not equal), or unordered
  HS, // Unsigned higher or same    >), ==), or unordered
  LO, // Unsigned lower             Less than
  MI, // Minus), negative            Less than
  PL, // Plus), positive or zero     >), ==), or unordered
  VS, // Overflow                   Unordered
  VC, // No overflow                Not unordered
  /// Unsigned higher Greater than), or unordered
  HI,
  LS, // Unsigned lower or same     Less than or equal
  GE, // Greater than or equal      Greater than or equal
  LT, // Less than), or unordered
  GT, // Greater than               Greater than
  LE, // Less than or equal         <), ==), or unordered
  AL, // Always      Always
  NV, // Always      Always
  // Note the NV exists purely to disassemble 0b1111. Execution is "always".
  Invalid,

  // Common aliases used for SVE.
  ANY_ACTIVE, //
  FIRST_ACTIVE, //
  LAST_ACTIVE, //
  NONE_ACTIVE,
}
