
@file:ExportedApi
package ca.moheektech.capstone.api

import ca.moheektech.capstone.Instruction
import ca.moheektech.capstone.internal.platform.CapstoneBinding
import kotlin.js.ExperimentalJsExport
import ca.moheektech.capstone.internal.ExportedApi

/**
 * Iterator for memory-efficient instruction disassembly.
 *
 * This iterator allows streaming disassembly of large binaries without loading all instructions
 * into memory at once.
 *
 * Example usage:
 * ```kotlin
 * engine.iterate(code, 0x1000).use { iterator ->
 *     for (instruction in iterator) {
 *         println(instruction)
 *         if (instruction.isCall) {
 *             println("Found call at: ${instruction.address.toString(16)}")
 *         }
 *     }
 * }
 * ```
 */

class DisassemblyIterator
internal constructor(
    private val binding: CapstoneBinding,
    private val code: ByteArray,
    initialAddress: Long,
    private val transform: (Instruction) -> Instruction = { it }
) : Iterator<Instruction>, AutoCloseable {

  private val position = DisassemblyPosition(0, initialAddress)
  private var cachedInstruction: Instruction? = null
  private var finished = false

  override fun hasNext(): Boolean {
    // If we already have a cached instruction, return true
    if (cachedInstruction != null) return true

    // If we're finished, return false
    if (finished) return false

    // If no more bytes to disassemble, we're done
    if (!position.hasRemaining(code.size)) {
      finished = true
      return false
    }

    // Try to disassemble the next instruction
    val result = binding.disasmIter(code, position)

    return result.fold(
        onSuccess = { insn ->
          if (insn == null) {
            // No more instructions
            finished = true
            false
          } else {
            // Cache the instruction
            cachedInstruction = transform(insn)
            true
          }
        },
        onFailure = {
          // Error occurred, stop iteration
          finished = true
          false
        })
  }

  override fun next(): Instruction {
    if (!hasNext()) {
      throw NoSuchElementException("No more instructions to disassemble")
    }

    val instruction = cachedInstruction!!
    cachedInstruction = null
    return instruction
  }

  override fun close() {
    // Cleanup if needed
    finished = true
    cachedInstruction = null
  }

  /**
   * Get the current position in the disassembly.
   *
   * @return Current position (offset and address)
   */
  fun currentPosition(): DisassemblyPosition = position.copy()

  /**
   * Collect all remaining instructions into a list.
   *
   * Note: This defeats the purpose of using an iterator for memory efficiency. Use only for small
   * code segments.
   *
   * @return List of all remaining instructions
   */
  fun toList(): List<Instruction> = buildList {
    while (hasNext()) {
      add(next())
    }
  }
}

/** Extension to easily iterate with forEach. */
inline fun DisassemblyIterator.forEach(action: (Instruction) -> Unit) {
  use {
    for (instruction in this) {
      action(instruction)
    }
  }
}
