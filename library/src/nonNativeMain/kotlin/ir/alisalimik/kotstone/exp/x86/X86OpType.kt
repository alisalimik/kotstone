package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.CS_OP_IMM
import ir.alisalimik.kotstone.internal.CS_OP_INVALID
import ir.alisalimik.kotstone.internal.CS_OP_MEM
import ir.alisalimik.kotstone.internal.CS_OP_REG
import ir.alisalimik.kotstone.internal.ExportedApi

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
