package ir.alisalimik.kotstone.internal.platform

import ir.alisalimik.kotstone.Instruction
import ir.alisalimik.kotstone.api.DisassemblyPosition
import ir.alisalimik.kotstone.bit.BitField
import ir.alisalimik.kotstone.enums.Architecture
import ir.alisalimik.kotstone.enums.CapstoneOption
import ir.alisalimik.kotstone.enums.Mode
import ir.alisalimik.kotstone.error.CapstoneResult
import ir.alisalimik.kotstone.error.ErrorCode

/**
 * Internal platform-specific binding interface.
 *
 * Each platform (JVM, Native, JS, WASM) implements this interface to provide the actual Capstone
 * functionality.
 *
 * This is an internal API and should not be used directly.
 */
internal interface CapstoneBinding {
  /**
   * Disassemble binary code.
   *
   * @param code Binary machine code
   * @param address Starting address
   * @param count Maximum number of instructions to disassemble (0 = all)
   * @return Result containing list of instructions or error
   */
  fun disasm(code: ByteArray, address: Long, count: Int): CapstoneResult<List<Instruction>>

  /**
   * Disassemble single instruction iteratively.
   *
   * Updates position after successful disassembly.
   *
   * @param code Binary machine code
   * @param position Current position (updated after success)
   * @return Result containing instruction or null if end reached
   */
  fun disasmIter(code: ByteArray, position: DisassemblyPosition): CapstoneResult<Instruction?>

  /**
   * Set runtime option.
   *
   * @param option Option to set
   * @return Result indicating success or error
   */
  fun setOption(option: CapstoneOption): CapstoneResult<Unit>

  /**
   * Get register name from register ID.
   *
   * @param regId Register ID
   * @return Register name or null if invalid
   */
  fun regName(regId: Int): String?

  /**
   * Get instruction name from instruction ID.
   *
   * @param insnId Instruction ID
   * @return Instruction name or null if invalid
   */
  fun insnName(insnId: Int): String?

  /**
   * Get group name from group ID.
   *
   * @param groupId Group ID
   * @return Group name or null if invalid
   */
  fun groupName(groupId: Int): String?

  /**
   * Get the last error code.
   *
   * @return Error code of last operation
   */
  fun lastError(): ErrorCode

  /** Close and release resources. */
  fun close()

  companion object {
    /**
     * Get Capstone library version.
     *
     * @return Pair of (major, minor) version
     */
    fun version(): Pair<Int, Int> = Pair(6, 0) // Will be overridden by platform

    /**
     * Check if architecture is supported.
     *
     * @param arch Architecture to check
     * @return True if supported
     */
    fun isSupported(arch: Architecture): Boolean = true // Will be overridden by platform
  }
}

/**
 * Create platform-specific binding.
 *
 * This is an expect function that must be implemented on each platform.
 *
 * @param architecture Target architecture
 * @param mode Disassembly mode
 * @return Platform-specific binding instance
 * @throws CapstoneError if initialization fails
 */
internal expect fun createPlatformBinding(
    architecture: Architecture,
    mode: BitField<Mode>
): CapstoneBinding

/** Get Capstone version (platform-specific). */
internal expect fun getPlatformVersion(): Pair<Int, Int>

/** Check if architecture is supported (platform-specific). */
internal expect fun isPlatformSupported(arch: Architecture): Boolean
