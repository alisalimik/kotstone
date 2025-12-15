package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone ARM banked register. */
expect enum class ArmBankedRegister : INumericEnum {

  // generated content <ARMGenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_BankedReg> begin
  // clang-format off

  ELR_HYP,
  LR_ABT,
  LR_FIQ,
  LR_IRQ,
  LR_MON,
  LR_SVC,
  LR_UND,
  LR_USR,
  R10_FIQ,
  R10_USR,
  R11_FIQ,
  R11_USR,
  R12_FIQ,
  R12_USR,
  R8_FIQ,
  R8_USR,
  R9_FIQ,
  R9_USR,
  SPSR_ABT,
  SPSR_FIQ,
  SPSR_HYP,
  SPSR_IRQ,
  SPSR_MON,
  SPSR_SVC,
  SPSR_UND,
  SP_ABT,
  SP_FIQ,
  SP_HYP,
  SP_IRQ,
  SP_MON,
  SP_SVC,
  SP_UND,
  SP_USR,

  // clang-format on
  // generated content <ARMGenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_BankedReg> end

}
