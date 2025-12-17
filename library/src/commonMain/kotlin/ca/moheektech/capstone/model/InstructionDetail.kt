@file:ExportedApi

package ca.moheektech.capstone.model

import ca.moheektech.capstone.arch.ArchDetail
import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.internal.ExportedApi

/**
 * Detailed instruction information.
 *
 * Available only when detail mode is enabled via [CapstoneBuilder.detail].
 *
 * @property regsRead List of registers implicitly read by this instruction
 * @property regsWritten List of registers implicitly written by this instruction
 * @property groups List of instruction groups this instruction belongs to
 * @property writeback True if instruction has writeback operands
 * @property archDetail Architecture-specific details (type-safe via sealed class)
 */
data class InstructionDetail(
    val regsRead: List<Register> = emptyList(),
    val regsWritten: List<Register> = emptyList(),
    val groups: List<InstructionGroup> = emptyList(),
    val writeback: Boolean = false,
    val archDetail: ArchDetail? = null
) {
  /** Check if this instruction belongs to a specific group */
  fun isInGroup(group: InstructionGroup): Boolean = groups.contains(group)

  fun readsRegister(regId: Int): Boolean = regsRead.any { it.id == regId }

  fun writesRegister(regId: Int): Boolean = regsWritten.any { it.id == regId }

  /** Get all registers accessed (read or written) */
  val allRegisters: List<Register>
    get() = (regsRead + regsWritten).distinct()
}
