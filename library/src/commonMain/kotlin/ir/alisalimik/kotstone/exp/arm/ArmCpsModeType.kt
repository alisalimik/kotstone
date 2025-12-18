package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone ARM CPS mode type. */
expect enum class ArmCpsModeType : INumericEnum {
  INVALID,
  IE,
  ID
}
