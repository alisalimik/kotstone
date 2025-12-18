package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 PState immediate 0-15. */
expect enum class AArch64PStateImm0_15 : INumericEnum {
  DAIFCLR,
  DAIFSET,
  DIT,
  PAN,
  SPSEL,
  SSBS,
  TCO,
  UAO,
  ENDING,
}
