package ca.moheektech.capstone.error

import ca.moheektech.capstone.bit.BitField
import ca.moheektech.capstone.bit.toBitField
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.Mode

/**
 * Result type for Capstone operations.
 *
 * Wraps Kotlin's standard Result type with Capstone-specific error handling.
 *
 * Example usage:
 * ```kotlin
 * val result: CapstoneResult<List<Instruction>> = engine.disassemble(code, 0x1000)
 * result.onSuccess { instructions ->
 *     println("Disassembled ${instructions.size} instructions")
 * }.onFailure { error ->
 *     println("Error: ${error.message}")
 * }
 * ```
 */
typealias CapstoneResult<T> = Result<T>

/**
 * Extension to safely get result or throw Capstone-specific error.
 *
 * Example:
 * ```kotlin
 * val instructions = engine.disassemble(code).getOrThrow()
 * ```
 */
fun <T> CapstoneResult<T>.getOrThrow(): T = getOrElse { error ->
  throw when (error) {
    is CapstoneError -> error
    else -> CapstoneError.Unknown(-1, error.message ?: "Unknown error")
  }
}

fun <T> CapstoneResult<T>.success(value: T): CapstoneResult<T> = Result.success(value)

fun <T> CapstoneResult<T>.failure(error: CapstoneError): CapstoneResult<T> = Result.failure(error)

fun <T> CapstoneResult<T>.failure(
    errorCode: ErrorCode,
    arch: Architecture = Architecture.ARM,
    mode: BitField<Mode> = Mode.LITTLE_ENDIAN.toBitField()
): CapstoneResult<T> = Result.failure(errorCode.toError(arch, mode))
