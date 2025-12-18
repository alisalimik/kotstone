package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone ARM CPS flag type. */
expect enum class ArmCpsFlagType : INumericEnum {
  INVALID,
  F,
  I,
  A,
  NONE
}
