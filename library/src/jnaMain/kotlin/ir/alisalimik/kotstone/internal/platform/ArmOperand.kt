package ir.alisalimik.kotstone.internal.platform

import com.sun.jna.Structure

internal class ArmOperand : Structure() {
  @JvmField var vector_index: Int = 0
  @JvmField var shift: ArmOpShift = ArmOpShift()
  @JvmField var type: Int = 0
  @JvmField var value: ArmOpValue = ArmOpValue()
  @JvmField var subtracted: Byte = 0
  @JvmField var access: Byte = 0
  @JvmField var neon_lane: Byte = 0

  override fun read() {
    readField("vector_index")
    readField("type")
    when (type) {
      3 -> value.setType(ArmMemType::class.java) // ARM_OP_MEM
      4 -> value.setType(Double::class.javaPrimitiveType) // ARM_OP_FP
      else -> value.setType(Int::class.javaPrimitiveType)
    }
    if (type != 0) {
      readField("value")
      readField("shift")
      readField("subtracted")
      readField("access")
      readField("neon_lane")
    }
  }

  override fun getFieldOrder() =
      listOf("vector_index", "shift", "type", "value", "subtracted", "access", "neon_lane")
}
