@file:ExportedApi

package ir.alisalimik.kotstone

import ir.alisalimik.kotstone.arch.ArchDetail
import ir.alisalimik.kotstone.arch.ArmInstructionDetail
import ir.alisalimik.kotstone.arch.ArmOperand
import ir.alisalimik.kotstone.enums.InstructionGroup
import ir.alisalimik.kotstone.exp.arm.*
import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.InstructionDetail

data class ArmInstruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail
) : Instruction {

  val arm: ArmInstructionDetail =
      (detail.archDetail as? ArchDetail.ARM)?.detail
          ?: throw IllegalArgumentException(
              "ArmInstruction requires generic detail to have ARM specific detail")

  override fun isInGroup(group: InstructionGroup): Boolean = detail.groups.contains(group)

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

  // Convenience accessors
  val operands: List<ArmOperand>
    get() = arm.operands

  val cc: ArmConditionCode
    get() = arm.cc

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as ArmInstruction

    if (id != other.id) return false
    if (address != other.address) return false
    if (size != other.size) return false
    if (!bytes.contentEquals(other.bytes)) return false
    if (mnemonic != other.mnemonic) return false
    if (opStr != other.opStr) return false
    if (detail != other.detail) return false
    if (arm != other.arm) return false
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
    result = 31 * result + arm.hashCode()
    result = 31 * result + operands.hashCode()
    result = 31 * result + cc.hashCode()
    return result
  }
}
