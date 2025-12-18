package ir.alisalimik.kotstone.exp.x86

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.X86_OP_IMM
import ir.alisalimik.kotstone.internal.X86_OP_INVALID
import ir.alisalimik.kotstone.internal.X86_OP_MEM
import ir.alisalimik.kotstone.internal.X86_OP_REG

actual enum class X86OpType(override val value: UInt) : INumericEnum {
  INVALID(X86_OP_INVALID),
  REG(X86_OP_REG),
  IMM(X86_OP_IMM),
  MEM(X86_OP_MEM);

  actual companion object {
    actual fun fromValue(value: Int): X86OpType {
      return entries.firstOrNull { it.value == value.toUInt() } ?: INVALID
    }
  }
}
