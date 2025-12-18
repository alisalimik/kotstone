package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class AArch64Shifter(override val value: Int) : INumericEnum {
  INVALID(AARCH64_SFT_INVALID),
  LSL(AARCH64_SFT_LSL),
  MSL(AARCH64_SFT_MSL),
  LSR(AARCH64_SFT_LSR),
  ASR(AARCH64_SFT_ASR),
  ROR(AARCH64_SFT_ROR),
  LSL_REG(AARCH64_SFT_LSL_REG),
  MSL_REG(AARCH64_SFT_MSL_REG),
  LSR_REG(AARCH64_SFT_LSR_REG),
  ASR_REG(AARCH64_SFT_ASR_REG),
  ROR_REG(AARCH64_SFT_ROR_REG)
}
