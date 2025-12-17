package ca.moheektech.capstone.arch

import ca.moheektech.capstone.internal.ExportedApi

/**
 * Sealed class representing architecture-specific instruction details.
 *
 * Provides type-safe access to architecture-specific information about instructions.
 *
 * Example usage:
 * ```kotlin
 * when (val archDetail = instruction.detail?.archDetail) {
 *     is ArchDetail.AArch64 -> {
 *         println("Condition: ${archDetail.detail.cc}")
 *         println("Updates flags: ${archDetail.detail.updateFlags}")
 *     }
 *     is ArchDetail.X86 -> {
 *         println("Operand count: ${archDetail.detail.operands.size}")
 *     }
 *     else -> println("No arch-specific details")
 * }
 * ```
 */
@ExportedApi
sealed class ArchDetail {
  /** AArch64 (ARM64) specific details */
  data class AArch64(val detail: AArch64InstructionDetail) : ArchDetail()

  /** X86/X86-64 specific details */
  data class X86(val detail: X86InstructionDetail) : ArchDetail()

  /** ARM (32-bit) specific details */
  data class ARM(val detail: ArmInstructionDetail) : ArchDetail()

  /** MIPS specific details */
  data class MIPS(val detail: GenericDetail) : ArchDetail()

  /** PowerPC specific details */
  data class PPC(val detail: GenericDetail) : ArchDetail()

  /** SPARC specific details */
  data class SPARC(val detail: GenericDetail) : ArchDetail()

  /** SystemZ specific details */
  data class SystemZ(val detail: GenericDetail) : ArchDetail()

  /** Generic/unknown architecture details */
  data class Generic(val detail: GenericDetail) : ArchDetail()
}
