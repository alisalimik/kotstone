package ca.moheektech.capstone

import ca.moheektech.capstone.enums.InstructionGroup

/**
 * Represents a disassembled instruction.
 *
 * This is the base type for all architecture-specific instructions. Cast to specific types (e.g.,
 * [ArmInstruction], [X86Instruction]) to access detailed operands.
 */
sealed interface Instruction {
  val id: Int
  val address: Long
  val size: Int
  val bytes: ByteArray
  val mnemonic: String
  val opStr: String

  /** Check if instruction belongs to a specific group */
  fun isInGroup(group: InstructionGroup): Boolean

  /** Human-readable string representation */
  override fun toString(): String

  // Default implementations for convenience properties
  val isJump: Boolean
    get() = isInGroup(InstructionGroup.JUMP)

  val isCall: Boolean
    get() = isInGroup(InstructionGroup.CALL)

  val isReturn: Boolean
    get() = isInGroup(InstructionGroup.RET)

  val isPrivileged: Boolean
    get() = isInGroup(InstructionGroup.PRIVILEGE)
}
