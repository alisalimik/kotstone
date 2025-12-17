package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class X86Operand : Structure() {
  @JvmField var type: Int = 0
  @JvmField var value: X86OpValue = X86OpValue()
  @JvmField var size: Byte = 0
  @JvmField var access: Int = 0
  @JvmField var avx_bcast: Int = 0
  @JvmField var avx_zero_opmask: Boolean = false

  override fun read() {
    super.read()
    when (type) {
      3 -> value.setType(X86MemType::class.java) // X86_OP_MEM
      2 -> value.setType(Long::class.javaPrimitiveType) // X86_OP_IMM
      1 -> value.setType(Int::class.javaPrimitiveType) // X86_OP_REG
    }
    if (type != 0) {
      readField("value")
    }
  }

  override fun getFieldOrder() =
      listOf("type", "value", "size", "access", "avx_bcast", "avx_zero_opmask")
}
