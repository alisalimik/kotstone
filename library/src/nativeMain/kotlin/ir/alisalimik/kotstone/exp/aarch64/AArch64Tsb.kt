package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class AArch64Tsb(override val value: UInt) : INumericEnum {
  CSYNC(AARCH64_TSB_CSYNC),
  ENDING(AARCH64_TSB_ENDING),
}
