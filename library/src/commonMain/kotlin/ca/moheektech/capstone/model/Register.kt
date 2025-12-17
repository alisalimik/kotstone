package ca.moheektech.capstone.model

import kotlin.js.ExperimentalJsExport
import ca.moheektech.capstone.internal.ExportedApi

/**
 * Represents a CPU register.
 *
 * @property id Register ID (architecture-specific)
 * @property name Register name (e.g., "rax", "x0", "r1")
 */

data class Register(val id: Int, val name: String? = null) {
  override fun toString(): String = name ?: "reg_$id"
}
