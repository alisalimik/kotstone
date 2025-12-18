package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class ArmSpsrCpsrBits(override val value: UInt) : INumericEnum {

  // SPSR* field flags can be OR combined
  SPSR_C(ARM_FIELD_SPSR_C),
  SPSR_X(ARM_FIELD_SPSR_X),
  SPSR_S(ARM_FIELD_SPSR_S),
  SPSR_F(ARM_FIELD_SPSR_F),

  // CPSR* field flags can be OR combined
  CPSR_C(ARM_FIELD_CPSR_C),
  CPSR_X(ARM_FIELD_CPSR_X),
  CPSR_S(ARM_FIELD_CPSR_S),
  CPSR_F(ARM_FIELD_CPSR_F),
}
