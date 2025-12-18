package ir.alisalimik.kotstone.arch

import ir.alisalimik.kotstone.enums.AccessType
import ir.alisalimik.kotstone.exp.aarch64.AArch64Db
import ir.alisalimik.kotstone.exp.aarch64.AArch64Extender
import ir.alisalimik.kotstone.exp.aarch64.AArch64OpType
import ir.alisalimik.kotstone.exp.aarch64.AArch64Prfm
import ir.alisalimik.kotstone.exp.aarch64.AArch64Shifter
import ir.alisalimik.kotstone.exp.aarch64.AArch64VectorLayout
import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.Register

/** AArch64 instruction operand. */
@ExportedApi
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
