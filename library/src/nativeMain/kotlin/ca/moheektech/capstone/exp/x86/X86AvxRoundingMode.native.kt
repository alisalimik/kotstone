package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.X86_AVX_RM_INVALID
import ca.moheektech.capstone.internal.X86_AVX_RM_RD
import ca.moheektech.capstone.internal.X86_AVX_RM_RN
import ca.moheektech.capstone.internal.X86_AVX_RM_RU
import ca.moheektech.capstone.internal.X86_AVX_RM_RZ

actual enum class X86AvxRoundingMode(override val value: UInt) : INumericEnum {
  INVALID(X86_AVX_RM_INVALID),
  RN(X86_AVX_RM_RN),
  RD(X86_AVX_RM_RD),
  RU(X86_AVX_RM_RU),
  RZ(X86_AVX_RM_RZ);

  actual companion object {
    actual fun fromValue(value: Int): X86AvxRoundingMode {
      return entries.firstOrNull { it.value == value.toUInt() } ?: INVALID
    }
  }
}
