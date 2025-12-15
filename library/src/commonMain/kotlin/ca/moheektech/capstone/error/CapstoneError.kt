package ca.moheektech.capstone.error

import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.Mode
import ca.moheektech.capstone.enums.Syntax

/**
 * Sealed class hierarchy representing all possible Capstone errors.
 *
 * Use when you need type-safe error handling with specific error information.
 */
sealed class CapstoneError : Exception() {
  /** Out of memory error */
  data class OutOfMemory(override val message: String = "Out of memory") : CapstoneError()

  /** Unsupported architecture error */
  data class UnsupportedArchitecture(
      val arch: Architecture,
      override val message: String = "Unsupported architecture: $arch"
  ) : CapstoneError()

  /** Invalid handle error */
  data class InvalidHandle(override val message: String = "Invalid Capstone handle") :
      CapstoneError()

  /** Invalid mode error */
  data class InvalidMode(
      val mode: Mode,
      override val message: String = "Invalid/unsupported mode: $mode"
  ) : CapstoneError()

  /** Invalid option error */
  data class InvalidOption(
      val optionName: String = "unknown",
      override val message: String = "Invalid/unsupported option: $optionName"
  ) : CapstoneError()

  /** Detail mode not enabled error */
  data class DetailNotEnabled(
      override val message: String = "Detail mode is not enabled. Use builder.detail = true"
  ) : CapstoneError()

  /** Memory setup error */
  data class MemSetup(override val message: String = "Dynamic memory management uninitialized") :
      CapstoneError()

  /** Unsupported version error */
  data class UnsupportedVersion(override val message: String = "Unsupported Capstone version") :
      CapstoneError()

  /** Diet mode error */
  data class Diet(override val message: String = "Access irrelevant data in diet engine") :
      CapstoneError()

  /** Skipdata mode error */
  data class SkipData(override val message: String = "Access irrelevant data in SKIPDATA mode") :
      CapstoneError()

  /** X86 syntax error */
  data class X86Syntax(
      val syntax: Syntax,
      override val message: String = "X86 $syntax syntax is unsupported (opt-out at compile time)"
  ) : CapstoneError()

  /** Unknown/generic error */
  data class Unknown(val code: Int, override val message: String = "Unknown error code: $code") :
      CapstoneError()
}

/** Convert ErrorCode to CapstoneError */
fun ErrorCode.toError(
    arch: Architecture = Architecture.ARM,
    mode: Mode = Mode.LITTLE_ENDIAN,
    syntax: Syntax = Syntax.DEFAULT
): CapstoneError =
    when (this) {
      ErrorCode.OK -> throw IllegalArgumentException("OK is not an error")
      ErrorCode.OUT_OF_MEMORY -> CapstoneError.OutOfMemory()
      ErrorCode.UNSUPPORTED_ARCH -> CapstoneError.UnsupportedArchitecture(arch)
      ErrorCode.INVALID_HANDLE -> CapstoneError.InvalidHandle()
      ErrorCode.INVALID_CSH -> CapstoneError.InvalidHandle("Invalid CSH argument")
      ErrorCode.INVALID_MODE -> CapstoneError.InvalidMode(mode)
      ErrorCode.INVALID_OPTION -> CapstoneError.InvalidOption()
      ErrorCode.DETAIL_OFF -> CapstoneError.DetailNotEnabled()
      ErrorCode.MEM_SETUP -> CapstoneError.MemSetup()
      ErrorCode.UNSUPPORTED_VERSION -> CapstoneError.UnsupportedVersion()
      ErrorCode.DIET -> CapstoneError.Diet()
      ErrorCode.SKIPDATA -> CapstoneError.SkipData()
      ErrorCode.X86_ATT -> CapstoneError.X86Syntax(Syntax.ATT)
      ErrorCode.X86_INTEL -> CapstoneError.X86Syntax(Syntax.INTEL)
      ErrorCode.X86_MASM -> CapstoneError.X86Syntax(Syntax.MASM)
    }
