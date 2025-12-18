package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class ArmCpsFlagType(override val value: Int) : INumericEnum {
  INVALID(ARM_CPSFLAG_INVALID),
  F(ARM_CPSFLAG_F),
  I(ARM_CPSFLAG_I),
  A(ARM_CPSFLAG_A),
  NONE(ARM_CPSFLAG_NONE)
}
