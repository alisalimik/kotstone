@file:ExportedApi

package ir.alisalimik.kotstone

import ir.alisalimik.kotstone.arch.ArchDetail
import ir.alisalimik.kotstone.arch.X86InstructionDetail
import ir.alisalimik.kotstone.arch.X86Operand
import ir.alisalimik.kotstone.enums.InstructionGroup
import ir.alisalimik.kotstone.exp.x86.X86OpType
import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.model.InstructionDetail

/** X86 architecture-specific instruction. */
data class X86Instruction(
    override val id: Int,
    override val address: Long,
    override val size: Int,
    override val bytes: ByteArray,
    override val mnemonic: String,
    override val opStr: String,
    val detail: InstructionDetail
) : Instruction {

  val x86: X86InstructionDetail =
      (detail.archDetail as? ArchDetail.X86)?.detail
          ?: throw IllegalArgumentException(
              "X86Instruction requires generic detail to have X86 specific detail")

  override fun isInGroup(group: InstructionGroup): Boolean = detail.groups.contains(group)

  override fun toString(): String =
      "0x${address.toString(16)}: $mnemonic${if (opStr.isNotEmpty()) " $opStr" else ""}"

  // Convenience accessors
  val operands: List<X86Operand>
    get() = x86.operands

  val imm: Long?
    get() = operands.firstOrNull { it.type == X86OpType.IMM }?.imm

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as X86Instruction

    if (id != other.id) return false
    if (address != other.address) return false
    if (size != other.size) return false
    if (!bytes.contentEquals(other.bytes)) return false
    if (mnemonic != other.mnemonic) return false
    if (opStr != other.opStr) return false
    if (detail != other.detail) return false
    if (x86 != other.x86) return false
    if (imm != other.imm) return false
    if (operands != other.operands) return false

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
    result = 31 * result + x86.hashCode()
    result = 31 * result + (imm?.hashCode() ?: 0)
    result = 31 * result + operands.hashCode()
    return result
  }
}
