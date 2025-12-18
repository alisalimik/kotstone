package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class X86AvxBroadcast : INumericEnum {
  BCAST_INVALID,
  BCAST_2,
  BCAST_4,
  BCAST_8,
  BCAST_16;

  companion object {
    fun fromValue(value: Int): X86AvxBroadcast
  }
}
