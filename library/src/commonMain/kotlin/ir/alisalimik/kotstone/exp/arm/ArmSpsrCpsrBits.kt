package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone ARM SPSR/CPSR bits. */
expect enum class ArmSpsrCpsrBits : INumericEnum {

  // SPSR* field flags can be OR combined
  SPSR_C,
  SPSR_X,
  SPSR_S,
  SPSR_F,

  // CPSR* field flags can be OR combined
  CPSR_C,
  CPSR_X,
  CPSR_S,
  CPSR_F,
}
