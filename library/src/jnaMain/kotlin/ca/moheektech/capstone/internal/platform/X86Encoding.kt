package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class X86Encoding : Structure() {
  @JvmField var modrm_offset: Byte = 0
  @JvmField var disp_offset: Byte = 0
  @JvmField var disp_size: Byte = 0
  @JvmField var imm_offset: Byte = 0
  @JvmField var imm_size: Byte = 0

  override fun getFieldOrder() =
      listOf("modrm_offset", "disp_offset", "disp_size", "imm_offset", "imm_size")
}
