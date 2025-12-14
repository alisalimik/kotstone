package ca.moheektech.capstone.arch

import ca.moheektech.capstone.enums.AccessType
import ca.moheektech.capstone.model.Register
import ca.moheektech.capstone.exp.aarch64.*

/**
 * AArch64 (ARM64) instruction details.
 *
 * @property cc Condition code for conditional instructions
 * @property updateFlags True if instruction updates condition flags
 * @property writeback True if instruction uses writeback addressing
 * @property postIndex True if post-indexed, false if pre-indexed
 * @property operands List of operands for this instruction
 */
data class AArch64InstructionDetail(
    val cc: AArch64ConditionCode = AArch64ConditionCode.Invalid,
    val updateFlags: Boolean = false,
    val writeback: Boolean = false,
    val postIndex: Boolean = false,
    val operands: List<AArch64Operand> = emptyList()
)

/**
 * AArch64 instruction operand.
 */
data class AArch64Operand(
    val type: AArch64OpType,
    val access: AccessType = AccessType.INVALID,

    /** Vector index for vector element access (-1 if not applicable) */
    val vectorIndex: Int = -1,

    /** Vector arrangement specifier */
    val vas: AArch64VectorLayout = AArch64VectorLayout.INVALID,

    /** Shifter applied to operand */
    val shifter: AArch64Shifter = AArch64Shifter.INVALID,

    /** Shift value */
    val shiftValue: Int = 0,

    /** Extender applied to operand */
    val extender: AArch64Extender = AArch64Extender.INVALID,

    /** Register (for REG type) */
    val reg: Register? = null,

    /** Immediate value (for IMM type) */
    val imm: Long? = null,

    /** Floating-point value (for FP type) */
    val fp: Double? = null,

    /** Memory operand (for MEM types) */
    val mem: AArch64MemoryOperand? = null,

    /** Barrier operand (for BARRIER/DB type) */
    val barrier: AArch64Db? = null,

    /** Prefetch operand (for PREFETCH type) */
    val prefetch: AArch64Prfm? = null,

    /** True if this operand is part of a register/vector list */
    val isListMember: Boolean = false
)

/**
 * AArch64 memory operand.
 */
data class AArch64MemoryOperand(
    val base: Register? = null,
    val index: Register? = null,
    val disp: Long = 0
)
