package ca.moheektech.capstone.arch

import ca.moheektech.capstone.enums.AccessType
import ca.moheektech.capstone.exp.arm.*
import ca.moheektech.capstone.model.Register

/** ARM instruction details. */
data class ArmInstructionDetail(
    val usermode: Boolean = false,
    val vectorSize: Int = 0,
    val vectorData: ArmVectorDataType = ArmVectorDataType.INVALID,
    val cpsMode: ArmCpsModeType = ArmCpsModeType.INVALID,
    val cpsFlag: ArmCpsFlagType = ArmCpsFlagType.INVALID,
    val cc: ArmConditionCode = ArmConditionCode.Invalid,
    val updateFlags: Boolean = false,
    val writeback: Boolean = false,
    val memBarrier: ArmMemoryBarrierOption = ArmMemoryBarrierOption.RESERVED_0, // Default?
    val operands: List<ArmOperand> = emptyList()
)

/** ARM instruction operand. */
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

/** ARM memory operand. */
data class ArmMemoryOperand(
    val base: Register? = null,
    val index: Register? = null,
    val scale: Int = 1,
    val disp: Int = 0,

    /** Lshift for memory index */
    val lshift: Int = 0
)
