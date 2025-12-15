package ca.moheektech.capstone.arch

import ca.moheektech.capstone.enums.AccessType
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
    val avxCC: X86AVXCC = X86AVXCC.INVALID,
    val sseCC: X86SSECC = X86SSECC.INVALID,
    val avxRm: X86AVXRM = X86AVXRM.INVALID,
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

/** X86 instruction operand. */
data class X86Operand(
    val type: X86OpType,
    val access: AccessType = AccessType.INVALID,
    val size: Int = 0,

    /** Register (for REG type) */
    val reg: Register? = null,

    /** Immediate value (for IMM type) */
    val imm: Long? = null,

    /** Memory operand (for MEM type) */
    val mem: X86MemoryOperand? = null,

    /** AVX broadcast type */
    val avxBcast: X86AVXBroadcast = X86AVXBroadcast.INVALID,

    /** AVX zero opmask {z} */
    val avxZeroOpmask: Boolean = false
)

/** X86 memory operand. */
data class X86MemoryOperand(
    val segment: Register? = null,
    val base: Register? = null,
    val index: Register? = null,
    val scale: Int = 1,
    val disp: Long = 0
)

/** X86 operand types. */
enum class X86OpType(val value: Int) {
  INVALID(0),
  REG(1), // Register operand
  IMM(2), // Immediate operand
  MEM(3); // Memory operand

  companion object {
    fun fromValue(value: Int): X86OpType = entries.firstOrNull { it.value == value } ?: INVALID
  }
}

/** X86 instruction prefixes. */
enum class X86Prefix(val value: Int) {
  INVALID(0),
  LOCK(0xF0), // LOCK prefix
  REPNE(0xF2), // REPNE/REPNZ prefix
  REP(0xF3), // REP/REPE/REPZ prefix
  CS_OVERRIDE(0x2E), // CS segment override
  SS_OVERRIDE(0x36), // SS segment override
  DS_OVERRIDE(0x3E), // DS segment override
  ES_OVERRIDE(0x26), // ES segment override
  FS_OVERRIDE(0x64), // FS segment override
  GS_OVERRIDE(0x65), // GS segment override
  OPSIZE(0x66), // Operand-size override
  ADDRSIZE(0x67); // Address-size override

  companion object {
    fun fromValue(value: Int): X86Prefix = entries.firstOrNull { it.value == value } ?: INVALID
  }
}

/** X86 EFLAGS bits. */
enum class X86EFlags(val bit: Int) {
  CF(0), // Carry flag
  PF(2), // Parity flag
  AF(4), // Auxiliary carry flag
  ZF(6), // Zero flag
  SF(7), // Sign flag
  TF(8), // Trap flag
  IF(9), // Interrupt enable flag
  DF(10), // Direction flag
  OF(11), // Overflow flag
  IOPL(12), // I/O privilege level (2 bits)
  NT(14), // Nested task flag
  RF(16), // Resume flag
  VM(17), // Virtual 8086 mode flag
  AC(18), // Alignment check
  VIF(19), // Virtual interrupt flag
  VIP(20), // Virtual interrupt pending
  ID(21); // ID flag

  companion object {
    fun fromBits(bits: Long): Set<X86EFlags> =
        entries.filter { (bits and (1L shl it.bit)) != 0L }.toSet()
  }
}

/** X86 AVX condition codes. */
enum class X86AVXCC(val value: Int) {
  INVALID(0),
  EQ(0),
  LT(1),
  LE(2),
  UNORD(3),
  NEQ(4),
  NLT(5),
  NLE(6),
  ORD(7),
  EQ_UQ(8),
  NGE(9),
  NGT(10),
  FALSE(11),
  NEQ_OQ(12),
  GE(13),
  GT(14),
  TRUE(15),
  EQ_OS(16),
  LT_OQ(17),
  LE_OQ(18),
  UNORD_S(19),
  NEQ_US(20),
  NLT_UQ(21),
  NLE_UQ(22),
  ORD_S(23),
  EQ_US(24),
  NGE_UQ(25),
  NGT_UQ(26),
  FALSE_OS(27),
  NEQ_OS(28),
  GE_OQ(29),
  GT_OQ(30),
  TRUE_US(31);

  companion object {
    fun fromValue(value: Int): X86AVXCC = entries.firstOrNull { it.value == value } ?: INVALID
  }
}

/** X86 SSE condition codes. */
enum class X86SSECC(val value: Int) {
  INVALID(-1),
  EQ(0),
  LT(1),
  LE(2),
  UNORD(3),
  NEQ(4),
  NLT(5),
  NLE(6),
  ORD(7);

  companion object {
    fun fromValue(value: Int): X86SSECC = entries.firstOrNull { it.value == value } ?: INVALID
  }
}

/** X86 AVX rounding modes. */
enum class X86AVXRM(val value: Int) {
  INVALID(0),
  RN(1), // Round to nearest
  RD(2), // Round down
  RU(3), // Round up
  RZ(4); // Round toward zero

  companion object {
    fun fromValue(value: Int): X86AVXRM = entries.firstOrNull { it.value == value } ?: INVALID
  }
}

/** X86 AVX broadcast types. */
enum class X86AVXBroadcast(val value: Int) {
  INVALID(0),
  B1TO2(1),
  B1TO4(2),
  B1TO8(3),
  B1TO16(4),
  B1TO32(5),
  B1TO64(6);

  companion object {
    fun fromValue(value: Int): X86AVXBroadcast =
        entries.firstOrNull { it.value == value } ?: INVALID
  }
}
