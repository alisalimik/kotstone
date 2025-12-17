package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class Arm64UnionOpInfo : Structure() {
  @JvmField var cc: Int = 0
  @JvmField var update_flags: Byte = 0
  @JvmField var post_index: Byte = 0
  @JvmField var op_count: Byte = 0
  @JvmField var op: Array<Arm64Operand> = Array(8) { Arm64Operand() }

  override fun read() {
    readField("cc")
    readField("update_flags")
    readField("post_index")
    readField("op_count")
    if (op_count.toInt() != 0) {
      op = Array(op_count.toInt()) { Arm64Operand() }
      readField("op")
    }
  }

  override fun getFieldOrder() = listOf("cc", "update_flags", "post_index", "op_count", "op")
}
