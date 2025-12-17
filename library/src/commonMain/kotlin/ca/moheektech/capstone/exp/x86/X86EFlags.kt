package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

import ca.moheektech.capstone.bit.BitField
import ca.moheektech.capstone.bit.BitFieldEnum

expect enum class X86EFlags : INumericEnum {
  MODIFY_AF,
  MODIFY_CF,
  MODIFY_SF,
  MODIFY_ZF,
  MODIFY_PF,
  MODIFY_OF,
  MODIFY_TF,
  MODIFY_IF,
  MODIFY_DF,
  MODIFY_NT,
  MODIFY_RF,
  PRIOR_OF,
  PRIOR_SF,
  PRIOR_ZF,
  PRIOR_AF,
  PRIOR_PF,
  PRIOR_CF,
  PRIOR_TF,
  PRIOR_IF,
  PRIOR_DF,
  PRIOR_NT,
  RESET_OF,
  RESET_CF,
  RESET_DF,
  RESET_IF,
  RESET_SF,
  RESET_AF,
  RESET_TF,
  RESET_NT,
  RESET_PF,
  SET_CF,
  SET_DF,
  SET_IF,
  TEST_OF,
  TEST_SF,
  TEST_ZF,
  TEST_PF,
  TEST_CF,
  TEST_NT,
  TEST_DF,
  UNDEFINED_OF,
  UNDEFINED_SF,
  UNDEFINED_ZF,
  UNDEFINED_PF,
  UNDEFINED_AF,
  UNDEFINED_CF,
  RESET_RF,
  TEST_RF,
  TEST_IF,
  TEST_TF,
  TEST_AF,
  RESET_ZF,
  SET_OF,
  SET_SF,
  SET_ZF,
  SET_AF,
  SET_PF,
  RESET_0F,
  RESET_AC;

    companion object : BitFieldEnum<X86EFlags> {
        override fun fromValue(value: ULong): X86EFlags?
        override fun allFlags(): BitField<X86EFlags>
    }
}
