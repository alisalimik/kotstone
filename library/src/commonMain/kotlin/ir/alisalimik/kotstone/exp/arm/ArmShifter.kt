package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum

/** Capstone ARM shifter. */
expect enum class ArmShifter : INumericEnum {
  INVALID,
  ASR,
  LSL,
  LSR,
  ROR,
  RRX,
  UXTW,

  // Added by Capstone to signal that the shift amount is stored in a register.
  // shift.val should be interpreted as register id.
  REG,
  ASR_REG,
  LSL_REG,
  LSR_REG,
  ROR_REG,
  // Others are not defined in the ISA.

}
