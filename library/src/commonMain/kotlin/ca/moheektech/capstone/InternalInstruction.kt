
@file:ExportedApi
package ca.moheektech.capstone

import ca.moheektech.capstone.enums.InstructionGroup
import ca.moheektech.capstone.model.InstructionDetail
import kotlin.js.ExperimentalJsExport
import ca.moheektech.capstone.internal.ExportedApi

/**
 * Internal/Generic instruction implementation. Used for generic decomposition or when architecture
 * details are not fully reified.
 */

data class InternalInstruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail? = null
) : Instruction {

  override fun isInGroup(group: InstructionGroup): Boolean = detail?.isInGroup(group) == true

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as InternalInstruction

    if (id != other.id) return false
    if (address != other.address) return false
    if (size != other.size) return false
    if (!bytes.contentEquals(other.bytes)) return false
    if (mnemonic != other.mnemonic) return false
    if (opStr != other.opStr) return false
    if (detail != other.detail) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id
    result = 31 * result + address.hashCode()
    result = 31 * result + size
    result = 31 * result + bytes.contentHashCode()
    result = 31 * result + mnemonic.hashCode()
    result = 31 * result + opStr.hashCode()
    result = 31 * result + (detail?.hashCode() ?: 0)
    return result
  }
}
