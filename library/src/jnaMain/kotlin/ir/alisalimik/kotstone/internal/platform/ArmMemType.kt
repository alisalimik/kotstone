package ir.alisalimik.kotstone.internal.platform

import com.sun.jna.Structure

// ARM structures
internal class ArmMemType : Structure() {
  @JvmField var base: Int = 0
  @JvmField var index: Int = 0
  @JvmField var scale: Int = 0
  @JvmField var disp: Int = 0
  @JvmField var lshift: Int = 0

  override fun getFieldOrder() = listOf("base", "index", "scale", "disp", "lshift")
}
