package ca.moheektech.capstone.arch

import ca.moheektech.capstone.exp.arm.ArmConditionCode
import ca.moheektech.capstone.exp.arm.ArmCpsFlagType
import ca.moheektech.capstone.exp.arm.ArmCpsModeType
import ca.moheektech.capstone.exp.arm.ArmMemoryBarrierOption
import ca.moheektech.capstone.exp.arm.ArmVectorDataType
import ca.moheektech.capstone.internal.ExportedApi

/** ARM instruction details. */
@ExportedApi
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
