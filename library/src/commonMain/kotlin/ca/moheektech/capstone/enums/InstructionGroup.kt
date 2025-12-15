package ca.moheektech.capstone.enums

/**
 * Common instruction groups.
 *
 * Groups classify instructions by their behavior (jump, call, return, etc.) Maps to cs_group_type
 * enum from capstone.h
 */
enum class InstructionGroup(val value: Int) {
  /** Invalid/unknown group */
  INVALID(0),

  /** All jump instructions (conditional and unconditional) */
  JUMP(1),

  /** All call instructions */
  CALL(2),

  /** All return instructions */
  RET(3),

  /** All interrupt instructions */
  INT(4),

  /** All interrupt return instructions */
  IRET(5),

  /** All privileged instructions */
  PRIVILEGE(6),

  /** All relative branch instructions */
  BRANCH_RELATIVE(7);

  companion object {
    /** Convert integer value to InstructionGroup enum */
    fun fromValue(value: Int): InstructionGroup? = entries.firstOrNull { it.value == value }

    /** Convert list of integer group IDs to InstructionGroup list */
    fun fromValues(values: List<Int>): List<InstructionGroup> = values.mapNotNull { fromValue(it) }
  }
}
