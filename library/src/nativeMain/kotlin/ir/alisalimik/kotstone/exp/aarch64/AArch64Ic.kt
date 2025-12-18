package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class AArch64Ic(override val value: UInt) : INumericEnum {

  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_IC> begin
  // clang-format off
  IALLU(AARCH64_IC_IALLU),
  IALLUIS(AARCH64_IC_IALLUIS),
  IVAU(AARCH64_IC_IVAU),

  // clang-format on
  // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_IC> end
  ENDING(AARCH64_IC_ENDING),
}
