package ca.moheektech.capstone

import ca.moheektech.capstone.arch.ArchDetail
import ca.moheektech.capstone.arch.ArmInstructionDetail
import ca.moheektech.capstone.arch.ArmOperand
import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.exp.arm.*
import ca.moheektech.capstone.model.InstructionDetail

/** ARM architecture-specific instruction. */
data class ArmInstruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail
) : Instruction {

  val arm: ArmInstructionDetail =
      (detail.archDetail as? ArchDetail.ARM)?.detail
          ?: throw IllegalArgumentException(
              "ArmInstruction requires generic detail to have ARM specific detail")

  override fun isInGroup(group: InstructionGroup): Boolean = detail.groups.contains(group)

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

  // Convenience accessors
  val operands: List<ArmOperand>
    get() = arm.operands

  val cc: ArmConditionCode
    get() = arm.cc
}
