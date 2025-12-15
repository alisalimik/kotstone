package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class AArch64Extender(override val value: UInt) : INumericEnum {
  INVALID(AARCH64_EXT_INVALID),
  UXTB(AARCH64_EXT_UXTB),
  UXTH(AARCH64_EXT_UXTH),
  UXTW(AARCH64_EXT_UXTW),
  UXTX(AARCH64_EXT_UXTX),
  SXTB(AARCH64_EXT_SXTB),
  SXTH(AARCH64_EXT_SXTH),
  SXTW(AARCH64_EXT_SXTW),
  SXTX(AARCH64_EXT_SXTX),
}
