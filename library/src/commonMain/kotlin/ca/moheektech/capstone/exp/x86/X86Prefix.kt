package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum

expect enum class X86Prefix : INumericEnum {
  ZERO,
  LOCK,
  REP,
  REPE,
  REPNE,
  CS,
  SS,
  DS,
  ES,
  FS,
  GS,
  OPSIZE,
  ADDRSIZE;

    companion object {
        fun fromValue(value: Int): X86Prefix
    }
}
