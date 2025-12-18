package ir.alisalimik.kotstone.enums

import ir.alisalimik.kotstone.bit.BitField
import ir.alisalimik.kotstone.bit.BitFieldEnum
import ir.alisalimik.kotstone.bit.IFlagValue
import ir.alisalimik.kotstone.exp.INumericEnum

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
expect enum class Mode : INumericEnum, IFlagValue {
  LITTLE_ENDIAN,
  ARM,
  MODE_16,
  MODE_32,
  MODE_64,
  THUMB,
  MCLASS,
  V8,
  MICRO,
  MIPS3,
  MIPS32R6,
  MIPS2,
  V9,
  QPX,
  SPE,
  BOOKE,
  PS,
  M68K_000,
  M68K_010,
  M68K_020,
  M68K_030,
  M68K_040,
  M68K_060,
  BIG_ENDIAN,
  MIPS32,
  MIPS64,
  M680X_6301,
  M680X_6309,
  M680X_6800,
  M680X_6801,
  M680X_6805,
  M680X_6808,
  M680X_6809,
  M680X_6811,
  M680X_CPU12,
  M680X_HCS08,
  BPF_CLASSIC,
  BPF_EXTENDED,
  RISCV32,
  RISCV64,
  RISCVC,
  MOS65XX_6502,
  MOS65XX_65C02,
  MOS65XX_W65C02,
  MOS65XX_65816,
  MOS65XX_65816_LONG_M,
  MOS65XX_65816_LONG_X,
  MOS65XX_65816_LONG_MX,
  SH2,
  SH2A,
  SH3,
  SH4,
  SH4A,
  SHFPU,
  SHDSP,
  TRICORE_110,
  TRICORE_120,
  TRICORE_130,
  TRICORE_131,
  TRICORE_160,
  TRICORE_161,
  TRICORE_162,
  TRICORE_180;

  override val flag: ULong

  companion object : BitFieldEnum<Mode> {
    fun fromValue(value: Int): Mode?

    override fun fromValue(value: ULong): Mode?

    override fun allFlags(): BitField<Mode>
  }
}
