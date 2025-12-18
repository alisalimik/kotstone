package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class AArch64PStateImm0_1(override val value: Int) : INumericEnum {
  ALLINT(AARCH64_PSTATEIMM0_1_ALLINT),
  PM(AARCH64_PSTATEIMM0_1_PM),
  ENDING(AARCH64_PSTATEIMM0_1_ENDING),
}
