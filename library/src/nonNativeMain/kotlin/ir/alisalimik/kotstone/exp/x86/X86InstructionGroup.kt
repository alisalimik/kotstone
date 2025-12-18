package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

@ExportedApi
actual enum class X86InstructionGroup(override val value: Int) : INumericEnum {

  INVALID(X86_GRP_INVALID), // /< = CS_GRP_INVALID

  // Generic groups
  // all jump instructions (conditional+direct+indirect jumps)
  JUMP(X86_GRP_JUMP), // /< = CS_GRP_JUMP
  // all call instructions
  CALL(X86_GRP_CALL), // /< = CS_GRP_CALL
  // all return instructions
  RET(X86_GRP_RET), // /< = CS_GRP_RET
  // all interrupt instructions (int+syscall)
  INT(X86_GRP_INT), // /< = CS_GRP_INT
  // all interrupt return instructions
  IRET(X86_GRP_IRET), // /< = CS_GRP_IRET
  // all privileged instructions
  PRIVILEGE(X86_GRP_PRIVILEGE), // /< = CS_GRP_PRIVILEGE
  // all relative branching instructions
  BRANCH_RELATIVE(X86_GRP_BRANCH_RELATIVE), // /< = CS_GRP_BRANCH_RELATIVE

  // Architecture-specific groups
  VM(X86_GRP_VM), // /< all virtualization instructions (VT-x + AMD-V)
  _3DNOW(X86_GRP_3DNOW),
  AES(X86_GRP_AES),
  ADX(X86_GRP_ADX),
  AVX(X86_GRP_AVX),
  AVX2(X86_GRP_AVX2),
  AVX512(X86_GRP_AVX512),
  BMI(X86_GRP_BMI),
  BMI2(X86_GRP_BMI2),
  CMOV(X86_GRP_CMOV),
  F16C(X86_GRP_F16C),
  FMA(X86_GRP_FMA),
  FMA4(X86_GRP_FMA4),
  FSGSBASE(X86_GRP_FSGSBASE),
  HLE(X86_GRP_HLE),
  MMX(X86_GRP_MMX),
  MODE32(X86_GRP_MODE32),
  MODE64(X86_GRP_MODE64),
  RTM(X86_GRP_RTM),
  SHA(X86_GRP_SHA),
  SSE1(X86_GRP_SSE1),
  SSE2(X86_GRP_SSE2),
  SSE3(X86_GRP_SSE3),
  SSE41(X86_GRP_SSE41),
  SSE42(X86_GRP_SSE42),
  SSE4A(X86_GRP_SSE4A),
  SSSE3(X86_GRP_SSSE3),
  PCLMUL(X86_GRP_PCLMUL),
  XOP(X86_GRP_XOP),
  CDI(X86_GRP_CDI),
  ERI(X86_GRP_ERI),
  TBM(X86_GRP_TBM),
  _16BITMODE(X86_GRP_16BITMODE),
  NOT64BITMODE(X86_GRP_NOT64BITMODE),
  SGX(X86_GRP_SGX),
  DQI(X86_GRP_DQI),
  BWI(X86_GRP_BWI),
  PFI(X86_GRP_PFI),
  VLX(X86_GRP_VLX),
  SMAP(X86_GRP_SMAP),
  NOVLX(X86_GRP_NOVLX),
  FPU(X86_GRP_FPU),
  ENDING(X86_GRP_ENDING)
}
