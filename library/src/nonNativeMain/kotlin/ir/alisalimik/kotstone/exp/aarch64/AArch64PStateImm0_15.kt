package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class AArch64PStateImm0_15(override val value: Int) : INumericEnum {
  DAIFCLR(AARCH64_PSTATEIMM0_15_DAIFCLR),
  DAIFSET(AARCH64_PSTATEIMM0_15_DAIFSET),
  DIT(AARCH64_PSTATEIMM0_15_DIT),
  PAN(AARCH64_PSTATEIMM0_15_PAN),
  SPSEL(AARCH64_PSTATEIMM0_15_SPSEL),
  SSBS(AARCH64_PSTATEIMM0_15_SSBS),
  TCO(AARCH64_PSTATEIMM0_15_TCO),
  UAO(AARCH64_PSTATEIMM0_15_UAO),
  ENDING(AARCH64_PSTATEIMM0_15_ENDING),
}
