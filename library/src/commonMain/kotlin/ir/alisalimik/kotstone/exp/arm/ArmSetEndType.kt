package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone ARM setend type. */
expect enum class ArmSetEndType : INumericEnum {

  INVALID, /// < Uninitialized.
  BE, /// < BE operand.
  LE, /// < LE operand
}
