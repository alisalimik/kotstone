package ca.moheektech.capstone.arch

import ca.moheektech.capstone.enums.AccessType
import ca.moheektech.capstone.exp.arm.ArmOpType
import ca.moheektech.capstone.exp.arm.ArmSetEndType
import ca.moheektech.capstone.exp.arm.ArmShifter
import ca.moheektech.capstone.internal.ExportedApi
import ca.moheektech.capstone.model.Register

/** ARM instruction operand. */
@ExportedApi
data class ArmOperand(
    val type: ArmOpType,
    val access: AccessType = AccessType.INVALID,

    /** Vector index for vector element access (-1 if not applicable) */
    val vectorIndex: Int = -1,

    /** Shift type */
    val shiftType: ArmShifter = ArmShifter.INVALID,

    /** Shift value */
    val shiftValue: Int = 0,

    /** Register (for REG, SYSREG, BANKEDREG, etc.) */
    val reg: Register? = null,

    /** Immediate value (for IMM, CIMM, PIMM) */
    val imm: Int? = null,

    /** Floating-point value (for FP) */
    val fp: Double? = null,

    /** Memory operand (for MEM types) */
    val mem: ArmMemoryOperand? = null,

    /** SETEND type (for SETEND) */
    val setend: ArmSetEndType = ArmSetEndType.INVALID,

    /** Subtracted */
    val subtracted: Boolean = false,

    /** NEON lane index */
    val neonLane: Byte = -1
)
