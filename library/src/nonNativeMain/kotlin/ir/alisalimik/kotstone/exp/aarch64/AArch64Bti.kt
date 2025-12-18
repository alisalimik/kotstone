package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class AArch64Bti(override val value: Int) : INumericEnum {
  C(AARCH64_BTI_C),
  J(AARCH64_BTI_J),
  JC(AARCH64_BTI_JC),
  ENDING(AARCH64_BTI_ENDING),
}
