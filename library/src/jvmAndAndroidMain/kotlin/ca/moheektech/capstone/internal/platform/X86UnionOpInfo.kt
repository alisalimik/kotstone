package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class X86UnionOpInfo : Structure() {
  @JvmField var prefix: ByteArray = ByteArray(4)
  @JvmField var opcode: ByteArray = ByteArray(4)
  @JvmField var rex: Byte = 0
  @JvmField var addr_size: Byte = 0
  @JvmField var modrm: Byte = 0
  @JvmField var sib: Byte = 0
  @JvmField var disp: Long = 0
  @JvmField var sib_index: Int = 0
  @JvmField var sib_scale: Byte = 0
  @JvmField var sib_base: Int = 0
  @JvmField var sse_cc: Int = 0
  @JvmField var avx_cc: Int = 0
  @JvmField var avx_sae: Byte = 0
  @JvmField var avx_rm: Int = 0
  @JvmField var eflags: Long = 0
  @JvmField var fpu_flags: Long = 0
  @JvmField var op_count: Byte = 0
  @JvmField var op: Array<X86Operand> = Array(8) { X86Operand() }

  override fun getFieldOrder() =
      listOf(
          "prefix",
          "opcode",
          "rex",
          "addr_size",
          "modrm",
          "sib",
          "disp",
          "sib_index",
          "sib_scale",
          "sib_base",
          "sse_cc",
          "avx_cc",
          "avx_sae",
          "avx_rm",
          "eflags",
          "fpu_flags",
          "op_count",
          "op")
}
