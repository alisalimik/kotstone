package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

// X86 structures
internal class X86MemType : Structure() {
  @JvmField var segment: Int = 0
  @JvmField var base: Int = 0
  @JvmField var index: Int = 0
  @JvmField var scale: Int = 0
  @JvmField var disp: Long = 0

  override fun getFieldOrder() = listOf("segment", "base", "index", "scale", "disp")
}
