package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone AArch64 trace synchronization barrier. */
expect enum class AArch64Tsb : INumericEnum {
  CSYNC,
  ENDING,
}
