package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.X86_PREFIX_0
import ca.moheektech.capstone.internal.X86_PREFIX_ADDRSIZE
import ca.moheektech.capstone.internal.X86_PREFIX_CS
import ca.moheektech.capstone.internal.X86_PREFIX_DS
import ca.moheektech.capstone.internal.X86_PREFIX_ES
import ca.moheektech.capstone.internal.X86_PREFIX_FS
import ca.moheektech.capstone.internal.X86_PREFIX_GS
import ca.moheektech.capstone.internal.X86_PREFIX_LOCK
import ca.moheektech.capstone.internal.X86_PREFIX_OPSIZE
import ca.moheektech.capstone.internal.X86_PREFIX_REP
import ca.moheektech.capstone.internal.X86_PREFIX_REPE
import ca.moheektech.capstone.internal.X86_PREFIX_REPNE
import ca.moheektech.capstone.internal.X86_PREFIX_SS

actual enum class X86Prefix(override val value: UInt) : INumericEnum {
  ZERO(X86_PREFIX_0),
  LOCK(X86_PREFIX_LOCK),
  REP(X86_PREFIX_REP),
  REPE(X86_PREFIX_REPE),
  REPNE(X86_PREFIX_REPNE),
  CS(X86_PREFIX_CS),
  SS(X86_PREFIX_SS),
  DS(X86_PREFIX_DS),
  ES(X86_PREFIX_ES),
  FS(X86_PREFIX_FS),
  GS(X86_PREFIX_GS),
  OPSIZE(X86_PREFIX_OPSIZE),
  ADDRSIZE(X86_PREFIX_ADDRSIZE);

    actual companion object {
        actual fun fromValue(value: Int): X86Prefix {
            return entries.firstOrNull { it.value == value.toUInt() } ?: ZERO
        }
    }
}
