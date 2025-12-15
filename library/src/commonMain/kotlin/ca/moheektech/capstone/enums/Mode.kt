package ca.moheektech.capstone.enums

/**
 * Capstone disassembly mode flags.
 *
 * These can be OR-combined to specify the disassembly mode. Maps to cs_mode enum from capstone.h
 *
 * Example:
 * ```kotlin
 * // 64-bit x86 in little-endian
 * val mode = Mode.MODE_64 or Mode.LITTLE_ENDIAN
 *
 * // ARM Thumb mode with v8 extensions
 * val mode = Mode.THUMB or Mode.V8
 * ```
 */
enum class Mode(val value: Int) {
  /** Little-endian mode (default) */
  LITTLE_ENDIAN(0),

  /** 32-bit ARM mode */
  ARM(0),

  /** 16-bit mode (X86) */
  MODE_16(1 shl 1),

  /** 32-bit mode (X86, MIPS) */
  MODE_32(1 shl 2),

  /** 64-bit mode (X86, MIPS, PPC) */
  MODE_64(1 shl 3),

  /** ARM Thumb mode (including Thumb-2) */
  THUMB(1 shl 4),

  /** ARM Cortex-M series */
  MCLASS(1 shl 5),

  /** ARMv8 A32 encodings */
  V8(1 shl 6),

  /** MicroMips mode (MIPS) */
  MICRO(1 shl 4),

  /** Mips III ISA (MIPS) */
  MIPS3(1 shl 5),

  /** Mips32r6 ISA (MIPS) */
  MIPS32R6(1 shl 6),

  /** Mips II ISA (MIPS) */
  MIPS2(1 shl 7),

  /** SparcV9 mode (Sparc) */
  V9(1 shl 4),

  /** Quad Processing eXtensions mode (PPC) */
  QPX(1 shl 4),

  /** Signal Processing Engine mode (PPC) */
  SPE(1 shl 5),

  /** Book-E mode (PPC) */
  BOOKE(1 shl 6),

  /** Paired-singles mode (PPC) */
  PS(1 shl 7),

  /** M68000 mode (M68K) */
  M68K_000(1 shl 1),

  /** M68010 mode (M68K) */
  M68K_010(1 shl 2),

  /** M68020 mode (M68K) */
  M68K_020(1 shl 3),

  /** M68030 mode (M68K) */
  M68K_030(1 shl 4),

  /** M68040 mode (M68K) */
  M68K_040(1 shl 5),

  /** M68060 mode (M68K) */
  M68K_060(1 shl 6),

  /** Big-endian mode */
  BIG_ENDIAN(1 shl 31),

  /** Mips32 ISA (MIPS) - alias for MODE_32 */
  MIPS32(1 shl 2),

  /** Mips64 ISA (MIPS) - alias for MODE_64 */
  MIPS64(1 shl 3),

  /** M6301 mode (M680X) */
  M680X_6301(1 shl 1),

  /** M6309 mode (M680X) */
  M680X_6309(1 shl 2),

  /** M6800 mode (M680X) */
  M680X_6800(1 shl 3),

  /** M6801 mode (M680X) */
  M680X_6801(1 shl 4),

  /** M6805 mode (M680X) */
  M680X_6805(1 shl 5),

  /** M6808 mode (M680X) */
  M680X_6808(1 shl 6),

  /** M6809 mode (M680X) */
  M680X_6809(1 shl 7),

  /** M6811 mode (M680X) */
  M680X_6811(1 shl 8),

  /** CPU12 mode (M680X) */
  M680X_CPU12(1 shl 9),

  /** HCS08 mode (M680X) */
  M680X_HCS08(1 shl 10),

  /** Classic BPF mode (BPF) */
  BPF_CLASSIC(0),

  /** Extended BPF mode (BPF) */
  BPF_EXTENDED(1 shl 0),

  /** RISCV 32-bit mode (RISCV) */
  RISCV32(1 shl 0),

  /** RISCV 64-bit mode (RISCV) */
  RISCV64(1 shl 1),

  /** RISCV compressed instruction mode (RISCV) */
  RISCVC(1 shl 2),

  /** MOS6502 mode (MOS65XX) */
  MOS65XX_6502(1 shl 1),

  /** 65C02 mode (MOS65XX) */
  MOS65XX_65C02(1 shl 2),

  /** W65C02 mode (MOS65XX) */
  MOS65XX_W65C02(1 shl 3),

  /** 65816 mode (MOS65XX) */
  MOS65XX_65816(1 shl 4),

  /** 65816 long M mode (MOS65XX) */
  MOS65XX_65816_LONG_M(1 shl 5),

  /** 65816 long X mode (MOS65XX) */
  MOS65XX_65816_LONG_X(1 shl 6),

  /** 65816 long MX mode (MOS65XX) */
  MOS65XX_65816_LONG_MX((1 shl 5) or (1 shl 6)),

  /** SH2 mode (SH) */
  SH2(1 shl 1),

  /** SH2A mode (SH) */
  SH2A(1 shl 2),

  /** SH3 mode (SH) */
  SH3(1 shl 3),

  /** SH4 mode (SH) */
  SH4(1 shl 4),

  /** SH4A mode (SH) */
  SH4A(1 shl 5),

  /** SH FPU mode (SH) */
  SHFPU(1 shl 6),

  /** SH DSP mode (SH) */
  SHDSP(1 shl 7),

  /** TriCore 1.10 mode (TRICORE) */
  TRICORE_110(1 shl 1),

  /** TriCore 1.20 mode (TRICORE) */
  TRICORE_120(1 shl 2),

  /** TriCore 1.30 mode (TRICORE) */
  TRICORE_130(1 shl 3),

  /** TriCore 1.31 mode (TRICORE) */
  TRICORE_131(1 shl 4),

  /** TriCore 1.60 mode (TRICORE) */
  TRICORE_160(1 shl 5),

  /** TriCore 1.61 mode (TRICORE) */
  TRICORE_161(1 shl 6),

  /** TriCore 1.62 mode (TRICORE) */
  TRICORE_162(1 shl 7),

  /** TriCore 1.80 mode (TRICORE) */
  TRICORE_180(1 shl 8);

  /** Combine this mode with another using OR */
  infix fun or(other: Mode): Int = this.value or other.value

  /** Combine this mode with an integer value using OR */
  infix fun or(other: Int): Int = this.value or other

  companion object {
    /** Convert integer value to Mode enum */
    fun fromValue(value: Int): Mode? = entries.firstOrNull { it.value == value }
  }
}

/** Deprecated alias for backward compatibility. Use [Mode] instead. */
@Deprecated(
    message = "Use Mode instead",
    replaceWith = ReplaceWith("Mode", "ca.moheektech.capstone.enums.Mode"),
    level = DeprecationLevel.WARNING)
typealias CsMode = ca.moheektech.capstone.CsMode
