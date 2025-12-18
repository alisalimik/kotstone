package ir.alisalimik.kotstone.exp

import ir.alisalimik.kotstone.Instruction
import ir.alisalimik.kotstone.exp.aarch64.AArch64Instruction
import ir.alisalimik.kotstone.exp.arm.ArmConditionCode
import ir.alisalimik.kotstone.exp.arm.ArmCpsFlagType
import ir.alisalimik.kotstone.exp.arm.ArmCpsModeType
import ir.alisalimik.kotstone.exp.arm.ArmInstruction
import ir.alisalimik.kotstone.exp.arm.ArmMemoryBarrierOption
import ir.alisalimik.kotstone.exp.arm.ArmOpType
import ir.alisalimik.kotstone.exp.arm.ArmSetEndType
import ir.alisalimik.kotstone.exp.arm.ArmShifter
import ir.alisalimik.kotstone.exp.arm.ArmVectorDataType
import ir.alisalimik.kotstone.exp.x86.X86Instruction

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
