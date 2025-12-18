@file:ExportedApi

package ir.alisalimik.kotstone

import ir.alisalimik.kotstone.arch.AArch64InstructionDetail
import ir.alisalimik.kotstone.arch.AArch64Operand
import ir.alisalimik.kotstone.arch.ArchDetail
import ir.alisalimik.kotstone.enums.InstructionGroup
import ir.alisalimik.kotstone.exp.aarch64.*
import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.InstructionDetail

data class Arm64Instruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail
) : Instruction {

  val arm64: AArch64InstructionDetail =
      (detail.archDetail as? ArchDetail.AArch64)?.detail
          ?: throw IllegalArgumentException(
              "Arm64Instruction requires generic detail to have AArch64 specific detail")

  override fun isInGroup(group: InstructionGroup): Boolean = detail.groups.contains(group)

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

  // Convenience accessors
  val operands: List<AArch64Operand>
    get() = arm64.operands

  val cc: AArch64ConditionCode
    get() = arm64.cc

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as Arm64Instruction

    if (id != other.id) return false
    if (address != other.address) return false
    if (size != other.size) return false
    if (!bytes.contentEquals(other.bytes)) return false
    if (mnemonic != other.mnemonic) return false
    if (opStr != other.opStr) return false
    if (detail != other.detail) return false
    if (arm64 != other.arm64) return false
    if (operands != other.operands) return false
    if (cc != other.cc) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id
    result = 31 * result + address.hashCode()
    result = 31 * result + size
    result = 31 * result + bytes.contentHashCode()
    result = 31 * result + mnemonic.hashCode()
    result = 31 * result + opStr.hashCode()
    result = 31 * result + detail.hashCode()
    result = 31 * result + arm64.hashCode()
    result = 31 * result + operands.hashCode()
    result = 31 * result + cc.hashCode()
    return result
  }
}
