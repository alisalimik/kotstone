package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class AArch64ConditionCode(override val value: Int) : INumericEnum {
  // Meaning  Meaning
  EQ(AArch64CC_EQ), // Equal                      Equal
  NE(AArch64CC_NE), // Not equal), or unordered
  HS(AArch64CC_HS), // Unsigned higher or same    >), ==), or unordered
  LO(AArch64CC_LO), // Unsigned lower             Less than
  MI(AArch64CC_MI), // Minus), negative            Less than
  PL(AArch64CC_PL), // Plus), positive or zero     >), ==), or unordered
  VS(AArch64CC_VS), // Overflow                   Unordered
  VC(AArch64CC_VC), // No overflow                Not unordered
  /// Unsigned higher Greater than), or unordered
  HI(AArch64CC_HI),
  LS(AArch64CC_LS), // Unsigned lower or same     Less than or equal
  GE(AArch64CC_GE), // Greater than or equal      Greater than or equal
  LT(AArch64CC_LT), // Less than), or unordered
  GT(AArch64CC_GT), // Greater than               Greater than
  LE(AArch64CC_LE), // Less than or equal         <), ==), or unordered
  AL(AArch64CC_AL), // Always      Always
  NV(AArch64CC_NV), // Always      Always
  // Note the NV exists purely to disassemble 0b1111. Execution is "always".
  Invalid(AArch64CC_Invalid),

  // Common aliases used for SVE.
  ANY_ACTIVE(AArch64CC_ANY_ACTIVE), //
  FIRST_ACTIVE(AArch64CC_FIRST_ACTIVE), //
  LAST_ACTIVE(AArch64CC_LAST_ACTIVE), //
  NONE_ACTIVE(AArch64CC_NONE_ACTIVE),
}
