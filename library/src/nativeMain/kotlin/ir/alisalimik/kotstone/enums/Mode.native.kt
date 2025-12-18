package ir.alisalimik.kotstone.enums

import ir.alisalimik.kotstone.bit.BitField
import ir.alisalimik.kotstone.bit.BitFieldEnum
import ir.alisalimik.kotstone.bit.IFlagValue
import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.CS_MODE_16
import ir.alisalimik.kotstone.internal.CS_MODE_32
import ir.alisalimik.kotstone.internal.CS_MODE_64
import ir.alisalimik.kotstone.internal.CS_MODE_ARM
import ir.alisalimik.kotstone.internal.CS_MODE_BIG_ENDIAN
import ir.alisalimik.kotstone.internal.CS_MODE_BOOKE
import ir.alisalimik.kotstone.internal.CS_MODE_BPF_CLASSIC
import ir.alisalimik.kotstone.internal.CS_MODE_BPF_EXTENDED
import ir.alisalimik.kotstone.internal.CS_MODE_LITTLE_ENDIAN
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6301
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6309
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6800
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6801
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6805
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6808
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6809
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_6811
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_CPU12
import ir.alisalimik.kotstone.internal.CS_MODE_M680X_HCS08
import ir.alisalimik.kotstone.internal.CS_MODE_M68K_000
import ir.alisalimik.kotstone.internal.CS_MODE_M68K_010
import ir.alisalimik.kotstone.internal.CS_MODE_M68K_020
import ir.alisalimik.kotstone.internal.CS_MODE_M68K_030
import ir.alisalimik.kotstone.internal.CS_MODE_M68K_040
import ir.alisalimik.kotstone.internal.CS_MODE_M68K_060
import ir.alisalimik.kotstone.internal.CS_MODE_MCLASS
import ir.alisalimik.kotstone.internal.CS_MODE_MICRO
import ir.alisalimik.kotstone.internal.CS_MODE_MIPS2
import ir.alisalimik.kotstone.internal.CS_MODE_MIPS3
import ir.alisalimik.kotstone.internal.CS_MODE_MIPS32
import ir.alisalimik.kotstone.internal.CS_MODE_MIPS32R6
import ir.alisalimik.kotstone.internal.CS_MODE_MIPS64
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_6502
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_65816
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_65816_LONG_M
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_65816_LONG_MX
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_65816_LONG_X
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_65C02
import ir.alisalimik.kotstone.internal.CS_MODE_MOS65XX_W65C02
import ir.alisalimik.kotstone.internal.CS_MODE_PS
import ir.alisalimik.kotstone.internal.CS_MODE_QPX
import ir.alisalimik.kotstone.internal.CS_MODE_RISCV32
import ir.alisalimik.kotstone.internal.CS_MODE_RISCV64
import ir.alisalimik.kotstone.internal.CS_MODE_RISCVC
import ir.alisalimik.kotstone.internal.CS_MODE_SH2
import ir.alisalimik.kotstone.internal.CS_MODE_SH2A
import ir.alisalimik.kotstone.internal.CS_MODE_SH3
import ir.alisalimik.kotstone.internal.CS_MODE_SH4
import ir.alisalimik.kotstone.internal.CS_MODE_SH4A
import ir.alisalimik.kotstone.internal.CS_MODE_SHDSP
import ir.alisalimik.kotstone.internal.CS_MODE_SHFPU
import ir.alisalimik.kotstone.internal.CS_MODE_SPE
import ir.alisalimik.kotstone.internal.CS_MODE_THUMB
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_110
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_120
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_130
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_131
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_160
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_161
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_162
import ir.alisalimik.kotstone.internal.CS_MODE_TRICORE_180
import ir.alisalimik.kotstone.internal.CS_MODE_V8
import ir.alisalimik.kotstone.internal.CS_MODE_V9

actual enum class Mode(override val value: UInt) : INumericEnum, IFlagValue {
  LITTLE_ENDIAN(CS_MODE_LITTLE_ENDIAN),
  ARM(CS_MODE_ARM),
  MODE_16(CS_MODE_16),
  MODE_32(CS_MODE_32),
  MODE_64(CS_MODE_64),
  THUMB(CS_MODE_THUMB),
  MCLASS(CS_MODE_MCLASS),
  V8(CS_MODE_V8),
  MICRO(CS_MODE_MICRO),
  MIPS3(CS_MODE_MIPS3),
  MIPS32R6(CS_MODE_MIPS32R6),
  MIPS2(CS_MODE_MIPS2),
  V9(CS_MODE_V9),
  QPX(CS_MODE_QPX),
  SPE(CS_MODE_SPE),
  BOOKE(CS_MODE_BOOKE),
  PS(CS_MODE_PS),
  M68K_000(CS_MODE_M68K_000),
  M68K_010(CS_MODE_M68K_010),
  M68K_020(CS_MODE_M68K_020),
  M68K_030(CS_MODE_M68K_030),
  M68K_040(CS_MODE_M68K_040),
  M68K_060(CS_MODE_M68K_060),
  BIG_ENDIAN(CS_MODE_BIG_ENDIAN),
  MIPS32(CS_MODE_MIPS32),
  MIPS64(CS_MODE_MIPS64),
  M680X_6301(CS_MODE_M680X_6301),
  M680X_6309(CS_MODE_M680X_6309),
  M680X_6800(CS_MODE_M680X_6800),
  M680X_6801(CS_MODE_M680X_6801),
  M680X_6805(CS_MODE_M680X_6805),
  M680X_6808(CS_MODE_M680X_6808),
  M680X_6809(CS_MODE_M680X_6809),
  M680X_6811(CS_MODE_M680X_6811),
  M680X_CPU12(CS_MODE_M680X_CPU12),
  M680X_HCS08(CS_MODE_M680X_HCS08),
  BPF_CLASSIC(CS_MODE_BPF_CLASSIC),
  BPF_EXTENDED(CS_MODE_BPF_EXTENDED),
  RISCV32(CS_MODE_RISCV32),
  RISCV64(CS_MODE_RISCV64),
  RISCVC(CS_MODE_RISCVC),
  MOS65XX_6502(CS_MODE_MOS65XX_6502),
  MOS65XX_65C02(CS_MODE_MOS65XX_65C02),
  MOS65XX_W65C02(CS_MODE_MOS65XX_W65C02),
  MOS65XX_65816(CS_MODE_MOS65XX_65816),
  MOS65XX_65816_LONG_M(CS_MODE_MOS65XX_65816_LONG_M),
  MOS65XX_65816_LONG_X(CS_MODE_MOS65XX_65816_LONG_X),
  MOS65XX_65816_LONG_MX(CS_MODE_MOS65XX_65816_LONG_MX),
  SH2(CS_MODE_SH2),
  SH2A(CS_MODE_SH2A),
  SH3(CS_MODE_SH3),
  SH4(CS_MODE_SH4),
  SH4A(CS_MODE_SH4A),
  SHFPU(CS_MODE_SHFPU),
  SHDSP(CS_MODE_SHDSP),
  TRICORE_110(CS_MODE_TRICORE_110),
  TRICORE_120(CS_MODE_TRICORE_120),
  TRICORE_130(CS_MODE_TRICORE_130),
  TRICORE_131(CS_MODE_TRICORE_131),
  TRICORE_160(CS_MODE_TRICORE_160),
  TRICORE_161(CS_MODE_TRICORE_161),
  TRICORE_162(CS_MODE_TRICORE_162),
  TRICORE_180(CS_MODE_TRICORE_180);

  actual override val flag: ULong
    get() = value.toULong()

  actual companion object : BitFieldEnum<Mode> {
    actual fun fromValue(value: Int): Mode? {
      return entries.firstOrNull { it.value == value.toUInt() }
    }

    actual override fun fromValue(value: ULong): Mode? {
      return entries.firstOrNull { it.value == value.toUInt() }
    }

    actual override fun allFlags(): BitField<Mode> {
      return BitField.fromFlags(entries)
    }
  }
}
