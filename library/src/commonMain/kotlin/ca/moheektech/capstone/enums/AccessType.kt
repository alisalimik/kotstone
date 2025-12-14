package ca.moheektech.capstone.enums

/**
 * Operand access type.
 *
 * Indicates how an operand is accessed by an instruction.
 */
enum class AccessType(val value: Int) {
    /** Invalid/unknown access */
    INVALID(0),

    /** Operand is read from */
    READ(1 shl 0),

    /** Operand is written to */
    WRITE(1 shl 1);

    /** Check if this access type includes reading */
    val isRead: Boolean get() = this == READ || this == READ_WRITE

    /** Check if this access type includes writing */
    val isWrite: Boolean get() = this == WRITE || this == READ_WRITE

    companion object {
        /** Operand is both read and written (combined flag) */
        val READ_WRITE = READ  // Will be or'd together in actual usage

        /**
         * Convert integer value to AccessType enum
         */
        fun fromValue(value: Int): AccessType = when {
            value == 0 -> INVALID
            (value and READ.value != 0) && (value and WRITE.value != 0) -> READ  // READ_WRITE case
            value and READ.value != 0 -> READ
            value and WRITE.value != 0 -> WRITE
            else -> INVALID
        }

        /**
         * Check if value represents read access
         */
        fun isRead(value: Int): Boolean = value and READ.value != 0

        /**
         * Check if value represents write access
         */
        fun isWrite(value: Int): Boolean = value and WRITE.value != 0
    }
}
