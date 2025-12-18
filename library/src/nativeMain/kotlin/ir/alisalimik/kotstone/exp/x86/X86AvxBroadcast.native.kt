package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.X86_AVX_BCAST_16
import ir.alisalimik.kotstone.internal.X86_AVX_BCAST_2
import ir.alisalimik.kotstone.internal.X86_AVX_BCAST_4
import ir.alisalimik.kotstone.internal.X86_AVX_BCAST_8
import ir.alisalimik.kotstone.internal.X86_AVX_BCAST_INVALID

actual enum class X86AvxBroadcast(override val value: UInt) : INumericEnum {
  BCAST_INVALID(X86_AVX_BCAST_INVALID),
  BCAST_2(X86_AVX_BCAST_2),
  BCAST_4(X86_AVX_BCAST_4),
  BCAST_8(X86_AVX_BCAST_8),
  BCAST_16(X86_AVX_BCAST_16);

  actual companion object {
    actual fun fromValue(value: Int): X86AvxBroadcast {
      return entries.firstOrNull { it.value == value.toUInt() } ?: BCAST_INVALID
    }
  }
}
