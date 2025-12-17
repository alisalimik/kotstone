
@file:ExportedApi
package ca.moheektech.capstone.api

import kotlin.js.ExperimentalJsExport
import ca.moheektech.capstone.internal.ExportedApi

/**
 * Position tracker for iterative disassembly.
 *
 * Maintains the current offset in the code buffer and the corresponding virtual address.
 *
 * @property offset Current byte offset in the code buffer
 * @property address Current virtual address
 */

data class DisassemblyPosition(var offset: Int = 0, var address: Long = 0) {
  /**
   * Advance position by the given number of bytes.
   *
   * @param bytes Number of bytes to advance
   */
  fun advance(bytes: Int) {
    offset += bytes
    address += bytes
  }

  /**
   * Check if there are remaining bytes in the buffer.
   *
   * @param codeSize Total size of the code buffer
   * @return True if there are bytes left to disassemble
   */
  fun hasRemaining(codeSize: Int): Boolean = offset < codeSize

  /**
   * Get the number of remaining bytes.
   *
   * @param codeSize Total size of the code buffer
   * @return Number of bytes remaining
   */
  fun remaining(codeSize: Int): Int = maxOf(0, codeSize - offset)
}
