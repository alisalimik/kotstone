package ca.moheektech.capstone.enums

import ca.moheektech.capstone.internal.ExportedApi

/**
 * Sealed class representing Capstone runtime options.
 *
 * Use these with [CapstoneEngine.setOption] to configure the disassembler.
 *
 * Example:
 * ```kotlin
 * engine.setOption(CapstoneOption.Detail(true))
 * engine.setOption(CapstoneOption.Syntax(Syntax.INTEL))
 * ```
 */
@ExportedApi
sealed class CapstoneOption {
  /**
   * Enable or disable detailed instruction information.
   *
   * When enabled, instructions will include operand details, registers accessed, etc.
   */
  data class Detail(val enabled: Boolean) : CapstoneOption() {
    override val optType = 2 // CS_OPT_DETAIL
    override val optValue = if (enabled) 1 else 0 // CS_OPT_ON/OFF
  }

  /**
   * Set assembly syntax style (Intel, AT&T, MASM, etc.)
   *
   * Only applicable for architectures that support multiple syntaxes (mainly X86).
   */
  data class Syntax(val style: ca.moheektech.capstone.enums.Syntax) : CapstoneOption() {
    override val optType = 1 // CS_OPT_SYNTAX
    override val optValue = style.value
  }

  /**
   * Enable or disable SKIPDATA mode.
   *
   * When enabled, Capstone will skip over data bytes and continue disassembling.
   */
  data class SkipData(val enabled: Boolean) : CapstoneOption() {
    override val optType = 4 // CS_OPT_SKIPDATA
    override val optValue = if (enabled) 1 else 0
  }

  /**
   * Print immediate operands in unsigned form.
   *
   * When enabled, immediate values are printed as unsigned integers.
   */
  data class Unsigned(val enabled: Boolean) : CapstoneOption() {
    override val optType = 8 // CS_OPT_UNSIGNED
    override val optValue = if (enabled) 1 else 0
  }

  /**
   * Change disassembly mode at runtime.
   *
   * Allows switching between modes (e.g., ARM/Thumb) without reopening the engine.
   */
  data class ChangeMode(val newMode: Mode) : CapstoneOption() {
    override val optType = 3 // CS_OPT_MODE
    override val optValue = newMode.value
  }

  /**
   * For branch instructions, only print the offset without adding to PC.
   *
   * Architecture-specific: ARM, PPC, AArch64
   */
  data class OnlyOffsetBranch(val enabled: Boolean) : CapstoneOption() {
    override val optType = 9 // CS_OPT_ONLY_OFFSET_BRANCH
    override val optValue = if (enabled) 1 else 0
  }

  /**
   * Set LITBASE register value for Xtensa architecture.
   *
   * Xtensa-specific option.
   */
  data class LitBase(val value: Long) : CapstoneOption() {
    override val optType = 10 // CS_OPT_LITBASE
    override val optValue = value.toInt()
  }

  /** Internal option type code for C API */
  internal abstract val optType: Int

  /** Internal option value for C API */
  internal abstract val optValue: Int
}
