package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class ArmUnionOpInfo : Structure() {
    @JvmField var usermode: Byte = 0
    @JvmField var vector_size: Int = 0
    @JvmField var vector_data: Int = 0
    @JvmField var cps_mode: Int = 0
    @JvmField var cps_flag: Int = 0
    @JvmField var cc: Int = 0
    @JvmField var update_flags: Byte = 0
    @JvmField var writeback: Byte = 0
    @JvmField var mem_barrier: Int = 0
    @JvmField var op_count: Byte = 0
    @JvmField var op: Array<ArmOperand> = Array(36) { ArmOperand() }

    override fun read() {
        readField("usermode")
        readField("vector_size")
        readField("vector_data")
        readField("cps_mode")
        readField("cps_flag")
        readField("cc")
        readField("update_flags")
        readField("writeback")
        readField("mem_barrier")
        readField("op_count")
        if (op_count.toInt() != 0) {
            op = Array(op_count.toInt()) { ArmOperand() }
            readField("op")
        }
    }

    override fun getFieldOrder() = listOf("usermode", "vector_size", "vector_data",
        "cps_mode", "cps_flag", "cc", "update_flags", "writeback", "mem_barrier", "op_count", "op")
}
