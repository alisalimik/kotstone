package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class ArmOpType(override val value: UInt) : INumericEnum {
  INVALID(CS_OP_INVALID),
  REG(CS_OP_REG),
  IMM(CS_OP_IMM),
  FP(CS_OP_FP),
  PRED(CS_OP_PRED),
  CIMM(CS_OP_SPECIAL + 0u),
  PIMM(CS_OP_SPECIAL + 1u),
  SETEND(CS_OP_SPECIAL + 2u),
  SYSREG(CS_OP_SPECIAL + 3u),
  BANKEDREG(CS_OP_SPECIAL + 4u),
  SPSR(CS_OP_SPECIAL + 5u),
  CPSR(CS_OP_SPECIAL + 6u),
  SYSM(CS_OP_SPECIAL + 7u),
  VPRED_R(CS_OP_SPECIAL + 8u),
  VPRED_N(CS_OP_SPECIAL + 9u),
  MEM(CS_OP_MEM)
}
