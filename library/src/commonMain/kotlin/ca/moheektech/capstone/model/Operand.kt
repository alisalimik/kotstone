package ca.moheektech.capstone.model

import ca.moheektech.capstone.enums.AccessType

/**
 * Base sealed class for instruction operands.
 *
 * Different operand types are represented by subclasses.
 */
sealed class Operand {
    /** Access type for this operand (read, write, or both) */
    abstract val access: AccessType

    /**
     * Register operand
     */
    data class RegisterOperand(
        val register: Register,
        override val access: AccessType = AccessType.INVALID
    ) : Operand() {
        override fun toString(): String = register.toString()
    }

    /**
     * Immediate (constant) operand
     */
    data class ImmediateOperand(
        val value: Long,
        override val access: AccessType = AccessType.READ
    ) : Operand() {
        override fun toString(): String = "0x${value.toString(16)}"
    }

    /**
     * Floating-point operand
     */
    data class FloatingPointOperand(
        val value: Double,
        override val access: AccessType = AccessType.READ
    ) : Operand() {
        override fun toString(): String = value.toString()
    }

    /**
     * Memory operand
     */
    data class MemoryOperand(
        val base: Register? = null,
        val index: Register? = null,
        val scale: Int = 1,
        val displacement: Long = 0,
        val segment: Register? = null,
        override val access: AccessType = AccessType.INVALID
    ) : Operand() {
        override fun toString(): String = buildString {
            segment?.let { append("${it.name}:") }
            append("[")
            val parts = mutableListOf<String>()
            base?.let { parts.add(it.toString()) }
            if (index != null) {
                if (scale != 1) {
                    parts.add("${index}*$scale")
                } else {
                    parts.add(index.toString())
                }
            }
            if (displacement != 0L || parts.isEmpty()) {
                parts.add(if (displacement >= 0) "+0x${displacement.toString(16)}" else "-0x${(-displacement).toString(16)}")
            }
            append(parts.joinToString(""))
            append("]")
        }
    }
}
