package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class ArmCpsFlagType(override val value: UInt) : INumericEnum {
  INVALID(ARM_CPSFLAG_INVALID),
  F(ARM_CPSFLAG_F),
  I(ARM_CPSFLAG_I),
  A(ARM_CPSFLAG_A),
  NONE(ARM_CPSFLAG_NONE)
}
