package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone X86 instruction group. */
expect enum class X86InstructionGroup : INumericEnum {

  INVALID, /// < = CS_GRP_INVALID

  // Generic groups
  // all jump instructions (conditional+direct+indirect jumps)
  JUMP, /// < = CS_GRP_JUMP
  // all call instructions
  CALL, /// < = CS_GRP_CALL
  // all return instructions
  RET, /// < = CS_GRP_RET
  // all interrupt instructions (int+syscall)
  INT, /// < = CS_GRP_INT
  // all interrupt return instructions
  IRET, /// < = CS_GRP_IRET
  // all privileged instructions
  PRIVILEGE, /// < = CS_GRP_PRIVILEGE
  // all relative branching instructions
  BRANCH_RELATIVE, /// < = CS_GRP_BRANCH_RELATIVE

  // Architecture-specific groups
  VM, /// < all virtualization instructions (VT-x + AMD-V)
  _3DNOW,
  AES,
  ADX,
  AVX,
  AVX2,
  AVX512,
  BMI,
  BMI2,
  CMOV,
  F16C,
  FMA,
  FMA4,
  FSGSBASE,
  HLE,
  MMX,
  MODE32,
  MODE64,
  RTM,
  SHA,
  SSE1,
  SSE2,
  SSE3,
  SSE41,
  SSE42,
  SSE4A,
  SSSE3,
  PCLMUL,
  XOP,
  CDI,
  ERI,
  TBM,
  _16BITMODE,
  NOT64BITMODE,
  SGX,
  DQI,
  BWI,
  PFI,
  VLX,
  SMAP,
  NOVLX,
  FPU,
  ENDING
}
