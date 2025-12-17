package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.AARCH64_EXT_INVALID
import ca.moheektech.capstone.internal.AARCH64_EXT_SXTB
import ca.moheektech.capstone.internal.AARCH64_EXT_SXTH
import ca.moheektech.capstone.internal.AARCH64_EXT_SXTW
import ca.moheektech.capstone.internal.AARCH64_EXT_SXTX
import ca.moheektech.capstone.internal.AARCH64_EXT_UXTB
import ca.moheektech.capstone.internal.AARCH64_EXT_UXTH
import ca.moheektech.capstone.internal.AARCH64_EXT_UXTW
import ca.moheektech.capstone.internal.AARCH64_EXT_UXTX
import ca.moheektech.capstone.internal.ExportedApi

@ExportedApi
actual enum class AArch64Extender(override val value: Int) : INumericEnum {
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
