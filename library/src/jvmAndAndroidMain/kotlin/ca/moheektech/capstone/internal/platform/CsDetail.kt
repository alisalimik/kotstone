package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

/**
 * JNA structure representing cs_detail
 */
internal open class CsDetail : Structure() {
    class ByReference : CsDetail(), Structure.ByReference

    @JvmField var regs_read: ShortArray = ShortArray(20)
    @JvmField var regs_read_count: Byte = 0
    @JvmField var regs_write: ShortArray = ShortArray(47)
    @JvmField var regs_write_count: Byte = 0
    @JvmField var groups: ByteArray = ByteArray(16)
    @JvmField var groups_count: Byte = 0
    @JvmField var writeback: Byte = 0
    @JvmField var arch: UnionArch = UnionArch()

    override fun getFieldOrder(): List<String> {
        return listOf("regs_read", "regs_read_count", "regs_write", "regs_write_count",
                      "groups", "groups_count", "writeback", "arch")
    }
}
