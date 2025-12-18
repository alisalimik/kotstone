package ir.alisalimik.kotstone.model

import ir.alisalimik.kotstone.internal.ExportedApi

/**
 * Represents a CPU register.
 *
 * @property id Register ID (architecture-specific)
 * @property name Register name (e.g., "rax", "x0", "r1")
 */
@ExportedApi
data class Register(val id: Int, val name: String? = null) {
  override fun toString(): String = name ?: "reg_$id"
}
