package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/** Capstone ARM system register. */
expect enum class ArmSystemRegister : INumericEnum {

  // generated content <ARMGenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_MClassSysReg> begin
  // clang-format off

  APSR,
  APSR_G,
  APSR_NZCVQ,
  APSR_NZCVQG,
  BASEPRI,
  BASEPRI_MAX,
  BASEPRI_NS,
  CONTROL,
  CONTROL_NS,
  EAPSR,
  EAPSR_G,
  EAPSR_NZCVQ,
  EAPSR_NZCVQG,
  EPSR,
  FAULTMASK,
  FAULTMASK_NS,
  IAPSR,
  IAPSR_G,
  IAPSR_NZCVQ,
  IAPSR_NZCVQG,
  IEPSR,
  IPSR,
  MSP,
  MSPLIM,
  MSPLIM_NS,
  MSP_NS,
  PAC_KEY_P_0,
  PAC_KEY_P_0_NS,
  PAC_KEY_P_1,
  PAC_KEY_P_1_NS,
  PAC_KEY_P_2,
  PAC_KEY_P_2_NS,
  PAC_KEY_P_3,
  PAC_KEY_P_3_NS,
  PAC_KEY_U_0,
  PAC_KEY_U_0_NS,
  PAC_KEY_U_1,
  PAC_KEY_U_1_NS,
  PAC_KEY_U_2,
  PAC_KEY_U_2_NS,
  PAC_KEY_U_3,
  PAC_KEY_U_3_NS,
  PRIMASK,
  PRIMASK_NS,
  PSP,
  PSPLIM,
  PSPLIM_NS,
  PSP_NS,
  SP_NS,
  XPSR,
  XPSR_G,
  XPSR_NZCVQ,
  XPSR_NZCVQG,

  // clang-format on
  // generated content <ARMGenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_MClassSysReg> end

}
