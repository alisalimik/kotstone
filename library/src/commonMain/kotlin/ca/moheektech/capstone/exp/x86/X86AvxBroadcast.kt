package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

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
