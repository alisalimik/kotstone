package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class AArch64Svcr(override val value: UInt) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVCR> begin
  // clang-format off
  SVCRSM(AARCH64_SVCR_SVCRSM),
  SVCRSMZA(AARCH64_SVCR_SVCRSMZA),
  SVCRZA(AARCH64_SVCR_SVCRZA),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_SVCR> end
  ENDING(AARCH64_SVCR_ENDING),
}
