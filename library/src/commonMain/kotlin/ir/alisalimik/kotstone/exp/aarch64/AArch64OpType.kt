package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class AArch64OpType : INumericEnum {
  INVALID,
  REG, /// < = CS_OP_REG .
  IMM, /// < = CS_OP_IMM .
  MEM_REG,
  MEM_IMM,
  MEM, /// < = CS_OP_MEM .
  FP, /// < = CS_OP_FP .
  CIMM, /// < C-Immediate
  REG_MRS, /// < MRS register operand.
  REG_MSR, /// < MSR register operand.
  IMPLICIT_IMM_0,
  SVCR,
  AT,
  DB,
  DC,
  ISB,
  TSB,
  PRFM,
  SVEPRFM,
  RPRFM,
  PSTATEIMM0_15,
  PSTATEIMM0_1,
  PSB,
  BTI,
  SVEPREDPAT,
  SVEVECLENSPECIFIER,
  SME,
  IMM_RANGE,
  TLBI,
  IC,
  DBNXS,
  EXACTFPIMM,
  SYSREG,
  SYSIMM,
  SYSALIAS,
  PRED,
}
