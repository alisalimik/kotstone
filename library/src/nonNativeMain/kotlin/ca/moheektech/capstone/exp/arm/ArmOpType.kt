package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@ExportedApi
actual enum class ArmOpType(override val value: Int) : INumericEnum {
  INVALID(ARM_OP_INVALID),
  REG(ARM_OP_REG),
  IMM(ARM_OP_IMM),
  FP(ARM_OP_FP),
  PRED(ARM_OP_PRED),
  CIMM(ARM_OP_CIMM),
  PIMM(ARM_OP_PIMM),
  SETEND(ARM_OP_SETEND),
  SYSREG(ARM_OP_SYSREG),
  BANKEDREG(ARM_OP_BANKEDREG),
  SPSR(ARM_OP_SPSR),
  CPSR(ARM_OP_CPSR),
  SYSM(ARM_OP_SYSM),
  VPRED_R(ARM_OP_VPRED_R),
  VPRED_N(ARM_OP_VPRED_N),
  MEM(ARM_OP_MEM)
}
