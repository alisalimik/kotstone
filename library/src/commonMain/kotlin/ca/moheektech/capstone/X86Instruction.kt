package ca.moheektech.capstone

import ca.moheektech.capstone.arch.ArchDetail
import ca.moheektech.capstone.arch.X86InstructionDetail
import ca.moheektech.capstone.arch.X86Operand
import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.exp.x86.*
import ca.moheektech.capstone.model.InstructionDetail

/** X86 architecture-specific instruction. */
data class X86Instruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail
) : Instruction {

  val x86: X86InstructionDetail =
      (detail.archDetail as? ArchDetail.X86)?.detail
          ?: throw IllegalArgumentException(
              "X86Instruction requires generic detail to have X86 specific detail")

  override fun isInGroup(group: InstructionGroup): Boolean = detail.groups.contains(group)

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

  // Convenience accessors
  val operands: List<X86Operand>
    get() = x86.operands

  val imm: Long?
    get() = operands.firstOrNull { it.type == ca.moheektech.capstone.arch.X86OpType.IMM }?.imm
}
