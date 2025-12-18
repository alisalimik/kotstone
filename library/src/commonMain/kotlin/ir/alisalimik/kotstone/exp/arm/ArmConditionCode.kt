package ir.alisalimik.kotstone.exp.arm

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class ArmConditionCode : INumericEnum {
  EQ,
  NE,
  HS,
  LO,
  MI,
  PL,
  VS,
  VC,
  HI,
  LS,
  GE,
  LT,
  GT,
  LE,
  AL,
  UNDEF,
  Invalid
}
