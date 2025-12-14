package ca.moheektech.capstone

import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.arch.AArch64InstructionDetail
import ca.moheektech.capstone.arch.AArch64Operand
import ca.moheektech.capstone.arch.ArchDetail
import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.model.InstructionDetail
import ca.moheektech.capstone.exp.aarch64.*

/**
 * AArch64 (ARM64) architecture-specific instruction.
 */
data class Arm64Instruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail
) : Instruction {

    val arm64: AArch64InstructionDetail = (detail.archDetail as? ArchDetail.AArch64)?.detail
        ?: throw IllegalArgumentException("Arm64Instruction requires generic detail to have AArch64 specific detail")

    override fun isInGroup(group: InstructionGroup): Boolean =
        detail.groups.contains(group)

    override fun toString(): String =
        "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

    // Convenience accessors
    val operands: List<AArch64Operand> get() = arm64.operands
    val cc: AArch64ConditionCode get() = arm64.cc
}
