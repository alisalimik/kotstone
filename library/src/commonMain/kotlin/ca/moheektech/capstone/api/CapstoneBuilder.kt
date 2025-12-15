package ca.moheektech.capstone.api

import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.CapstoneOption
import ca.moheektech.capstone.enums.Mode
import ca.moheektech.capstone.enums.Syntax
import ca.moheektech.capstone.error.getOrThrow
import ca.moheektech.capstone.internal.platform.createPlatformBinding

/**
 * Builder for configuring and creating a Capstone disassembly engine.
 *
 * Provides a DSL-style API for configuration.
 *
 * Example usage:
 * ```kotlin
 * val engine = CapstoneBuilder(Architecture.ARM64, Mode.LITTLE_ENDIAN)
 *     .apply {
 *         detail = true
 *         syntax = Syntax.DEFAULT
 *         skipData = false
 *     }
 *     .build()
 *
 * // Or using the convenience method:
 * val engine = CapstoneEngine.build(Architecture.X86, Mode.MODE_64) {
 *     detail = true
 *     syntax = Syntax.INTEL
 *     unsigned = true
 * }
 * ```
 *
 * @property architecture Target architecture to disassemble
 * @property mode Disassembly mode flags
 */
class CapstoneBuilder(private val architecture: Architecture, private val mode: Mode) {
  private val options = mutableListOf<CapstoneOption>()

  /**
   * Enable or disable detailed instruction information.
   *
   * When enabled, instructions will include operand details, registers accessed, groups, etc.
   * Default: false
   */
  var detail: Boolean = false
    set(value) {
      field = value
      options.removeAll { it is CapstoneOption.Detail }
      options.add(CapstoneOption.Detail(value))
    }

  /**
   * Set assembly syntax style (Intel, AT&T, MASM, etc.)
   *
   * Only applicable for architectures that support multiple syntaxes (mainly X86). Default: null
   * (use architecture default)
   */
  var syntax: Syntax? = null
    set(value) {
      field = value
      options.removeAll { it is CapstoneOption.Syntax }
      value?.let { options.add(CapstoneOption.Syntax(it)) }
    }

  /**
   * Enable or disable SKIPDATA mode.
   *
   * When enabled, Capstone will skip over data bytes and continue disassembling instead of stopping
   * at the first non-code byte. Default: false
   */
  var skipData: Boolean = false
    set(value) {
      field = value
      options.removeAll { it is CapstoneOption.SkipData }
      options.add(CapstoneOption.SkipData(value))
    }

  /**
   * Print immediate operands in unsigned form.
   *
   * When enabled, immediate values are printed as unsigned integers instead of signed. Default:
   * false
   */
  var unsigned: Boolean = false
    set(value) {
      field = value
      options.removeAll { it is CapstoneOption.Unsigned }
      options.add(CapstoneOption.Unsigned(value))
    }

  /**
   * For branch instructions, only print the offset without adding to PC.
   *
   * Architecture-specific option for: ARM, PPC, AArch64 Default: false
   */
  var onlyOffsetBranch: Boolean = false
    set(value) {
      field = value
      options.removeAll { it is CapstoneOption.OnlyOffsetBranch }
      options.add(CapstoneOption.OnlyOffsetBranch(value))
    }

  /**
   * Set LITBASE register value for Xtensa architecture.
   *
   * Xtensa-specific option. Default: 0
   */
  var litBase: Long = 0
    set(value) {
      field = value
      options.removeAll { it is CapstoneOption.LitBase }
      if (value != 0L) {
        options.add(CapstoneOption.LitBase(value))
      }
    }

  /**
   * Build the configured Capstone engine.
   *
   * @return Configured CapstoneEngine instance
   * @throws CapstoneError if engine creation or option setting fails
   */
  fun build(): CapstoneEngine {
    // Create platform-specific binding
    val binding = createPlatformBinding(architecture, mode)

    // Create engine
    val engine = CapstoneEngine(architecture, mode, binding)

    // Apply all configured options
    options.forEach { option -> engine.setOption(option).getOrThrow() }

    return engine
  }

  /**
   * Configure detail mode with a lambda.
   *
   * Example:
   * ```kotlin
   * builder.withDetail {
   *     // Detail mode will be enabled for this engine
   * }
   * ```
   */
  inline fun withDetail(configure: CapstoneBuilder.() -> Unit = {}): CapstoneBuilder {
    detail = true
    configure()
    return this
  }

  /** Configure syntax. */
  fun withSyntax(syntaxStyle: Syntax): CapstoneBuilder {
    syntax = syntaxStyle
    return this
  }

  /** Configure skipdata mode. */
  fun withSkipData(enabled: Boolean = true): CapstoneBuilder {
    skipData = enabled
    return this
  }

  /** Configure unsigned mode. */
  fun withUnsigned(enabled: Boolean = true): CapstoneBuilder {
    unsigned = enabled
    return this
  }
}
