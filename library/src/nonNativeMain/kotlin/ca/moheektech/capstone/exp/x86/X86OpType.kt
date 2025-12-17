package ca.moheektech.capstone.exp.x86

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.CS_OP_IMM
import ca.moheektech.capstone.internal.CS_OP_INVALID
import ca.moheektech.capstone.internal.CS_OP_MEM
import ca.moheektech.capstone.internal.CS_OP_REG
import ca.moheektech.capstone.internal.ExportedApi

@ExportedApi
actual enum class X86OpType(override val value: Int) : INumericEnum {
  INVALID(CS_OP_INVALID),
  REG(CS_OP_REG),
  IMM(CS_OP_IMM),
  MEM(CS_OP_MEM);

    actual companion object {
        actual fun fromValue(value: Int): X86OpType {
            return entries.firstOrNull { it.value == value } ?: INVALID
        }
    }
}
