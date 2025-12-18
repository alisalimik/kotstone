@file:ExportedApi

package ir.alisalimik.kotstone.enums

import ir.alisalimik.kotstone.internal.ExportedApi
import kotlin.js.JsStatic
import kotlin.jvm.JvmStatic

/**
 * Assembly syntax styles.
 *
 * Used with CS_OPT_SYNTAX option to configure output syntax.
 */
enum class Syntax(val value: Int) {
  /** Default syntax (architecture-dependent) */
  DEFAULT(0),

  /** Intel syntax (X86) */
  INTEL(1 shl 0),

  /** AT&T syntax (X86) */
  ATT(1 shl 1),

  /** MASM syntax (X86) */
  MASM(1 shl 4),

  /** Motorola syntax (M68K, MOS65XX) */
  MOTOROLA(1 shl 5),

  /** No register name prefix */
  NO_REGNAME(1 shl 3),

  /** CS register alias */
  CS_REG_ALIAS(1 shl 6),

  /** Percent prefix (PPC) */
  PERCENT(1 shl 7),

  /** No dollar prefix (MIPS, LoongArch) */
  NO_DOLLAR(1 shl 8);

  companion object {
    /** Convert integer value to Syntax enum */
    @JsStatic
    @JvmStatic
    fun fromValue(value: Int): Syntax? = entries.firstOrNull { it.value == value }
  }
}
