package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum

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
