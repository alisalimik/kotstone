@file:ExportedApi

package ca.moheektech.capstone.api

import ca.moheektech.capstone.Arm64Instruction
import ca.moheektech.capstone.ArmInstruction
import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.InternalInstruction
import ca.moheektech.capstone.X86Instruction
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.CapstoneOption
import ca.moheektech.capstone.enums.Mode
import ca.moheektech.capstone.error.CapstoneResult
import ca.moheektech.capstone.error.ErrorCode
import ca.moheektech.capstone.internal.ExportedApi
import ca.moheektech.capstone.internal.platform.CapstoneBinding
import ca.moheektech.capstone.internal.platform.getPlatformVersion
import ca.moheektech.capstone.internal.platform.isPlatformSupported
import kotlin.js.JsName

/**
 * Main Capstone disassembly engine.
 *
 * This is the primary interface for disassembling machine code. Use the [build] factory method to
 * create instances with configuration.
 *
 * The engine must be closed after use to release resources. Use the [use] extension function for
 * automatic resource management:
 * ```kotlin
 * CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
 *     detail = true
 *     syntax = Syntax.DEFAULT
 * }.use { engine ->
 *     val result = engine.disassemble(bytes, address = 0x1000)
 *     result.onSuccess { instructions ->
 *         instructions.forEach { println(it) }
 *     }
 * }
 * ```
 *
 * @property architecture Target architecture being disassembled
 * @property mode Disassembly mode flags
 */
class CapstoneEngine
internal constructor(
    val architecture: Architecture,
    val mode: Mode,
    private val binding: CapstoneBinding
) : AutoCloseable {

  private var closed = false

  /**
   * Disassemble binary code.
   *
   * @param code Binary machine code bytes to disassemble
   * @param address Starting virtual address for the code (default: 0)
   * @param count Maximum number of instructions to disassemble (0 = all, default)
   * @return Result containing list of disassembled instructions or error
   *
   * Example:
   * ```kotlin
   * engine.disassemble(byteArrayOf(0x09, 0x00, 0x38, 0xd5), address = 0x1000)
   *     .onSuccess { instructions ->
   *         println("Disassembled ${instructions.size} instructions")
   *     }
   *     .onFailure { error ->
   *         println("Error: ${error.message}")
   *     }
   * ```
   */
  fun disassemble(
      code: ByteArray,
      address: Long = 0,
      count: Int = 0
  ): CapstoneResult<List<Instruction>> {
    checkNotClosed()
    return binding.disasm(code, address, count).map { instructions ->
      instructions.map { upgradeInstruction(it) }
    }
  }

  /**
   * Create an iterator for memory-efficient disassembly.
   *
   * Useful for processing large binaries without loading all instructions into memory.
   *
   * @param code Binary machine code bytes
   * @param address Starting virtual address (default: 0)
   * @return Iterator that streams instructions one at a time
   *
   * Example:
   * ```kotlin
   * engine.iterate(largeBinary, 0x1000).use { iterator ->
   *     for (instruction in iterator) {
   *         if (instruction.isCall) {
   *             println("Found call at: 0x${instruction.address.toString(16)}")
   *         }
   *     }
   * }
   * ```
   */
  fun iterate(code: ByteArray, address: Long = 0): DisassemblyIterator {
    checkNotClosed()
    return DisassemblyIterator(binding, code, address) { upgradeInstruction(it) }
  }

  /**
   * Disassemble single instruction at current position.
   *
   * Updates the position for the next call.
   *
   * @param code Binary machine code bytes
   * @param position Current position (will be updated on success)
   * @return Result containing instruction or null if end reached
   *
   * Example:
   * ```kotlin
   * val position = DisassemblyPosition(0, 0x1000)
   * while (position.hasRemaining(code.size)) {
   *     engine.disassembleOne(code, position).onSuccess { insn ->
   *         insn?.let { println(it) }
   *     }
   * }
   * ```
   */
  fun disassembleOne(code: ByteArray, position: DisassemblyPosition): CapstoneResult<Instruction?> {
    checkNotClosed()
    return binding.disasmIter(code, position).map { insn -> insn?.let { upgradeInstruction(it) } }
  }

  /**
   * Set a runtime option.
   *
   * Options can be changed after engine creation to reconfigure behavior.
   *
   * @param option Option to set
   * @return Result indicating success or error
   *
   * Example:
   * ```kotlin
   * engine.setOption(CapstoneOption.Detail(true))
   *     .onSuccess { println("Detail mode enabled") }
   *     .onFailure { error -> println("Failed: $error") }
   * ```
   */
  fun setOption(option: CapstoneOption): CapstoneResult<Unit> {
    checkNotClosed()
    return binding.setOption(option)
  }

  /**
   * Get the friendly name of a register.
   *
   * @param regId Register ID (architecture-specific)
   * @return Register name or null if invalid
   *
   * Example:
   * ```kotlin
   * val name = engine.registerName(0x01)  // "x1" on ARM64
   * ```
   */
  fun registerName(regId: Int): String? {
    checkNotClosed()
    return binding.regName(regId)
  }

  /**
   * Get the friendly name of an instruction.
   *
   * @param insnId Instruction ID (architecture-specific)
   * @return Instruction mnemonic or null if invalid
   *
   * Example:
   * ```kotlin
   * val name = engine.instructionName(0x0A)  // "add" on many architectures
   * ```
   */
  fun instructionName(insnId: Int): String? {
    checkNotClosed()
    return binding.insnName(insnId)
  }

  /**
   * Get the friendly name of an instruction group.
   *
   * @param groupId Group ID
   * @return Group name or null if invalid
   */
  fun groupName(groupId: Int): String? {
    checkNotClosed()
    return binding.groupName(groupId)
  }

  /**
   * Get the last error code from the engine.
   *
   * @return Error code of the last operation
   */
  fun lastError(): ErrorCode {
    return binding.lastError()
  }

  /**
   * Close the engine and release resources.
   *
   * After closing, the engine cannot be used anymore. Prefer using the [use] extension function for
   * automatic cleanup.
   */
  override fun close() {
    if (!closed) {
      binding.close()
      closed = true
    }
  }

  private fun checkNotClosed() {
    if (closed) {
      throw IllegalStateException("CapstoneEngine has been closed")
    }
  }

  private fun upgradeInstruction(insn: Instruction): Instruction {
    if (insn !is InternalInstruction || insn.detail == null) return insn

    return try {
      when (architecture) {
        Architecture.ARM ->
            ArmInstruction(
                id = insn.id,
                address = insn.address,
                size = insn.size,
                bytes = insn.bytes,
                mnemonic = insn.mnemonic,
                opStr = insn.opStr,
                detail = insn.detail)
        Architecture.ARM64 ->
            Arm64Instruction(
                id = insn.id,
                address = insn.address,
                size = insn.size,
                bytes = insn.bytes,
                mnemonic = insn.mnemonic,
                opStr = insn.opStr,
                detail = insn.detail)
        Architecture.X86 ->
            X86Instruction(
                id = insn.id,
                address = insn.address,
                size = insn.size,
                bytes = insn.bytes,
                mnemonic = insn.mnemonic,
                opStr = insn.opStr,
                detail = insn.detail)
        else -> insn
      }
    } catch (e: IllegalArgumentException) {
      // Fallback if detail type mismatches (e.g. generic detail for Arch)
      insn
    }
  }

  companion object {
    /**
     * Get the Capstone library version.
     *
     * @return Pair of (major, minor) version numbers
     *
     * Example:
     * ```kotlin
     * val (major, minor) = CapstoneEngine.version()
     * println("Capstone version: $major.$minor")  // e.g., "6.0"
     * ```
     */
    fun version(): Pair<Int, Int> = getPlatformVersion()

    /**
     * Check if an architecture is supported by the library.
     *
     * @param arch Architecture to check
     * @return True if the architecture is supported
     *
     * Example:
     * ```kotlin
     * if (CapstoneEngine.isSupported(Architecture.ARM64)) {
     *     println("ARM64 is supported")
     * }
     * ```
     */
    fun isSupported(arch: Architecture): Boolean = isPlatformSupported(arch)

    /**
     * Create a new CapstoneEngine with builder configuration.
     *
     * This is the recommended way to create engine instances.
     *
     * @param architecture Target architecture
     * @param mode Disassembly mode flags
     * @param configure Configuration lambda for the builder
     * @return Configured CapstoneEngine instance
     * @throws CapstoneError if engine creation fails
     *
     * Example:
     * ```kotlin
     * val engine = CapstoneEngine.build(Architecture.X86, Mode.MODE_64) {
     *     detail = true
     *     syntax = Syntax.INTEL
     *     unsigned = true
     * }
     * ```
     */
    @JsName("buildWithEnum")
    fun build(
        architecture: Architecture,
        mode: Mode,
        configure: CapstoneBuilder.() -> Unit = {}
    ): CapstoneEngine {
      return CapstoneBuilder(architecture, mode).apply(configure).build()
    }

    /**
     * Create a new CapstoneEngine with builder configuration and mode combination.
     *
     * Helper for combining multiple mode flags.
     *
     * @param architecture Target architecture
     * @param mode Combined mode flags
     * @param configure Configuration lambda
     * @return Configured CapstoneEngine instance
     *
     * Example:
     * ```kotlin
     * val engine = CapstoneEngine.build(
     *     Architecture.ARM,
     *     Mode.THUMB or Mode.V8
     * ) {
     *     detail = true
     * }
     * ```
     */
    fun build(
        architecture: Architecture,
        mode: Int,
        configure: CapstoneBuilder.() -> Unit = {}
    ): CapstoneEngine {
      // Find the mode enum that matches, or use the first valid one
      val modeEnum = Mode.fromValue(mode) ?: Mode.LITTLE_ENDIAN
      return build(architecture, modeEnum, configure)
    }
  }
}
