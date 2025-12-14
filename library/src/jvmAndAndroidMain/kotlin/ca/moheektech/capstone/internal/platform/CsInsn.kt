package ca.moheektech.capstone.internal.platform

import com.sun.jna.Pointer
import com.sun.jna.Structure
import java.util.Arrays

/**
 * JNA structure representing cs_insn for Capstone v6
 *
 * Based on raw memory analysis, the structure is:
 * - Offset 0-3: id (4 bytes)
 * - Offset 4-15: additional fields/padding (12 bytes) - likely alias_id + padding
 * - Offset 16-23: address (8 bytes)
 * - Offset 24-25: size (2 bytes)
 * - Offset 26-49: bytes (24 bytes)
 * - Offset 50-81: mnemonic (32 bytes)
 * - Offset 82-241: op_str (160 bytes)
 * - Offset 242+: detail pointer
 */
@Structure.FieldOrder("id", "alias_id", "_padding", "address", "size", "bytes", "mnemonic", "op_str", "detail")
internal class CsInsn() : Structure() {
    @JvmField var id: Int = 0          // unsigned int (4 bytes) at offset 0
    @JvmField var alias_id: Int = 0    // unsigned int (4 bytes) at offset 4 - Capstone v6
    @JvmField var _padding: Long = 0   // 8 bytes padding at offset 8 for address alignment
    @JvmField var address: Long = 0    // uint64_t (8 bytes) at offset 16
    @JvmField var size: Short = 0      // uint16_t (2 bytes) at offset 24
    @JvmField var bytes: ByteArray = ByteArray(24)    // uint8_t[24] at offset 26
    @JvmField var mnemonic: ByteArray = ByteArray(32) // char[32] at offset 50
    @JvmField var op_str: ByteArray = ByteArray(160)  // char[160] at offset 82
    @JvmField var detail: CsDetail.ByReference? = null // cs_detail*

    init {
        Arrays.fill(mnemonic, 0.toByte())
        Arrays.fill(op_str, 0.toByte())
    }

    constructor(p: Pointer) : this() {
        useMemory(p)
        read()
    }

    override fun getFieldOrder(): List<String> {
        return listOf("id", "alias_id", "_padding", "address", "size", "bytes", "mnemonic", "op_str", "detail")
    }
}