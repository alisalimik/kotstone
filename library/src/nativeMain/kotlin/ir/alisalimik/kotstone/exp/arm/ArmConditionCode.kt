package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.*

actual enum class ArmConditionCode(override val value: UInt) : INumericEnum {
  EQ(ARMCC_EQ),
  NE(ARMCC_NE),
  HS(ARMCC_HS),
  LO(ARMCC_LO),
  MI(ARMCC_MI),
  PL(ARMCC_PL),
  VS(ARMCC_VS),
  VC(ARMCC_VC),
  HI(ARMCC_HI),
  LS(ARMCC_LS),
  GE(ARMCC_GE),
  LT(ARMCC_LT),
  GT(ARMCC_GT),
  LE(ARMCC_LE),
  AL(ARMCC_AL),
  UNDEF(ARMCC_UNDEF),
  Invalid(ARMCC_Invalid)
}
