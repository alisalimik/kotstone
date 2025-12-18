package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class ArmCpsModeType(override val value: Int) : INumericEnum {
  INVALID(ARM_CPSMODE_INVALID),
  IE(ARM_CPSMODE_IE),
  ID(ARM_CPSMODE_ID)
}
