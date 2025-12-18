package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class ArmSetEndType(override val value: UInt) : INumericEnum {

  INVALID(ARM_SETEND_INVALID), // /< Uninitialized.
  BE(ARM_SETEND_BE), // /< BE operand.
  LE(ARM_SETEND_LE), // /< LE operand
}
