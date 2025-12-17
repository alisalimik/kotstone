package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

@ExportedApi
actual enum class ArmShifter(override val value: Int) : INumericEnum {
  INVALID(ARM_SFT_INVALID),
  ASR(ARM_SFT_ASR),
  LSL(ARM_SFT_LSL),
  LSR(ARM_SFT_LSR),
  ROR(ARM_SFT_ROR),
  RRX(ARM_SFT_RRX),
  UXTW(ARM_SFT_UXTW),

  // Added by Capstone to signal that the shift amount is stored in a register.
  // shift.val should be interpreted as register id.
  REG(ARM_SFT_REG),
  ASR_REG(ARM_SFT_ASR_REG),
  LSL_REG(ARM_SFT_LSL_REG),
  LSR_REG(ARM_SFT_LSR_REG),
  ROR_REG(ARM_SFT_ROR_REG),
  // Others are not defined in the ISA.

}
