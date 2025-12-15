package ca.moheektech.capstone.model

/**
 * Represents a CPU register.
 *
 * @property id Register ID (architecture-specific)
 * @property name Register name (e.g., "rax", "x0", "r1")
 */
data class Register(val id: Int, val name: String? = null) {
  override fun toString(): String = name ?: "reg_$id"
}
