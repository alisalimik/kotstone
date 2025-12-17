package ca.moheektech.capstone.arch

import ca.moheektech.capstone.exp.x86.X86AvxConditionCode
import ca.moheektech.capstone.exp.x86.X86AvxRoundingMode
import ca.moheektech.capstone.exp.x86.X86EFlags
import ca.moheektech.capstone.exp.x86.X86Prefix
import ca.moheektech.capstone.exp.x86.X86SseConditionCode
import ca.moheektech.capstone.internal.ExportedApi
import ca.moheektech.capstone.model.Register

/**
 * X86/X86-64 instruction details.
 *
 * @property prefix List of instruction prefixes
 * @property opcode Opcode bytes (up to 4 bytes)
 * @property rex REX prefix byte (x86-64 only)
 * @property addrSize Address size override
 * @property modrm ModR/M byte
 * @property sib SIB (Scale-Index-Base) byte
 * @property disp Displacement value
 * @property sibIndex SIB index register
 * @property sibScale SIB scale factor (1, 2, 4, or 8)
 * @property sibBase SIB base register
 * @property operands List of operands
 * @property avxCC AVX condition code
 * @property sseCC SSE condition code
 * @property avxRm AVX rounding mode
 * @property avxSae AVX suppress all exceptions flag
 * @property eflagsModified EFLAGS bits modified by instruction
 * @property eflagsTested EFLAGS bits tested by instruction
 * @property fpuFlags FPU flags modified by instruction
 */
@ExportedApi
data class X86InstructionDetail(
    val prefix: List<X86Prefix> = emptyList(),
    val opcode: ByteArray = byteArrayOf(),
    val rex: Byte = 0,
    val addrSize: Int = 0,
    val modrm: Byte = 0,
    val sib: Byte = 0,
    val disp: Long = 0,
    val sibIndex: Register? = null,
    val sibScale: Int = 1,
    val sibBase: Register? = null,
    val operands: List<X86Operand> = emptyList(),
    val avxCC: X86AvxConditionCode = X86AvxConditionCode.INVALID,
    val sseCC: X86SseConditionCode = X86SseConditionCode.INVALID,
    val avxRm: X86AvxRoundingMode = X86AvxRoundingMode.INVALID,
    val avxSae: Boolean = false,
    val eflagsModified: Set<X86EFlags> = emptySet(),
    val eflagsTested: Set<X86EFlags> = emptySet(),
    val fpuFlags: Long = 0
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as X86InstructionDetail

    if (prefix != other.prefix) return false
    if (!opcode.contentEquals(other.opcode)) return false
    if (rex != other.rex) return false
    if (addrSize != other.addrSize) return false
    if (modrm != other.modrm) return false
    if (sib != other.sib) return false
    if (disp != other.disp) return false

    return true
  }

  override fun hashCode(): Int {
    var result = prefix.hashCode()
    result = 31 * result + opcode.contentHashCode()
    result = 31 * result + rex
    result = 31 * result + addrSize
    result = 31 * result + modrm
    result = 31 * result + sib
    result = 31 * result + disp.hashCode()
    return result
  }
}
