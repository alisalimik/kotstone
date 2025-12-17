package ca.moheektech.capstone.exp

import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.exp.aarch64.AArch64Instruction
import ca.moheektech.capstone.exp.arm.ArmConditionCode
import ca.moheektech.capstone.exp.arm.ArmCpsFlagType
import ca.moheektech.capstone.exp.arm.ArmCpsModeType
import ca.moheektech.capstone.exp.arm.ArmInstruction
import ca.moheektech.capstone.exp.arm.ArmMemoryBarrierOption
import ca.moheektech.capstone.exp.arm.ArmOpType
import ca.moheektech.capstone.exp.arm.ArmSetEndType
import ca.moheektech.capstone.exp.arm.ArmShifter
import ca.moheektech.capstone.exp.arm.ArmVectorDataType
import ca.moheektech.capstone.exp.x86.X86Instruction

// Import others eventually

/** Extension to get ArmInstruction enum from Instruction */
val Instruction.armId: ArmInstruction
  get() = this.id.toArmInstruction()

/** Extension to get AArch64Instruction enum from Instruction */
val Instruction.aarch64Id: AArch64Instruction
  get() = this.id.toAArch64Instruction()

/** Extension to get X86Instruction enum from Instruction */
val Instruction.x86Id: X86Instruction
  get() = this.id.toX86Instruction()

expect fun Int.toX86Instruction(): X86Instruction

expect fun Int.toArmInstruction(): ArmInstruction

expect fun Int.toAArch64Instruction(): AArch64Instruction

internal expect fun Int.toArmOpType(): ArmOpType

internal expect fun Int.toArmConditionCode(): ArmConditionCode

internal expect fun Int.toArmVectorDataType(): ArmVectorDataType

internal expect fun Int.toArmCpsModeType(): ArmCpsModeType

internal expect fun Int.toArmCpsFlagType(): ArmCpsFlagType

internal expect fun Int.toArmShifter(): ArmShifter

internal expect fun Int.toArmSetEndType(): ArmSetEndType

internal expect fun Int.toArmMemoryBarrierOption(): ArmMemoryBarrierOption