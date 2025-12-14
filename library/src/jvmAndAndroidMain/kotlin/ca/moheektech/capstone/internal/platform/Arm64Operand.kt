package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class Arm64Operand : Structure() {
    @JvmField var vector_index: Int = 0
    @JvmField var vas: Int = 0
    @JvmField var shift: Arm64OpShift = Arm64OpShift()
    @JvmField var ext: Int = 0
    @JvmField var type: Int = 0
    @JvmField var value: Arm64OpValue = Arm64OpValue()
    @JvmField var access: Byte = 0

    override fun read() {
        readField("type")
        when (type) {
            3 -> value.setType(Arm64MemType::class.java) // ARM64_OP_MEM
            4 -> value.setType(Double::class.javaPrimitiveType) // ARM64_OP_FP
            else -> value.setType(Long::class.javaPrimitiveType)
        }
        if (type != 0) {
            readField("value")
            readField("ext")
            readField("shift")
            readField("vas")
            readField("vector_index")
            readField("access")
        }
    }

    override fun getFieldOrder() = listOf("vector_index", "vas", "shift", "ext", "type", "value", "access")
}
