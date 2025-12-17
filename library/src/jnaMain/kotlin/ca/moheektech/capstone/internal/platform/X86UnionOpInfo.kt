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
  @JvmField var xop_cc: Int = 0 // Missing in kotlin but present in C struct!
  @JvmField var sse_cc: Int = 0
  @JvmField var avx_cc: Int = 0
  @JvmField var avx_sae: Byte = 0
  @JvmField var avx_rm: Int = 0
  @JvmField var eflags: Long = 0 // Union with fpu_flags
  @JvmField var op_count: Byte = 0
  @JvmField var op: Array<X86Operand> = Array(8) { X86Operand() }
  @JvmField var encoding: X86Encoding = X86Encoding() // New field in C struct!

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
          "xop_cc",
          "sse_cc",
          "avx_cc",
          "avx_sae",
          "avx_rm",
          "eflags",
          "op_count",
          "op",
          "encoding")
}
