package ca.moheektech.capstone

import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.model.InstructionDetail

/**
 * Internal/Generic instruction implementation. Used for generic decomposition or when architecture
 * details are not fully reified.
 */
data class InternalInstruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail? = null
) : Instruction {

  override fun isInGroup(group: InstructionGroup): Boolean = detail?.isInGroup(group) == true

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"
}
