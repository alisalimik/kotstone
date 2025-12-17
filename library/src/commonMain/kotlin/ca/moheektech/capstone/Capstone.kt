
@file:ExportedApi
package ca.moheektech.capstone

import kotlin.js.ExperimentalJsExport
import ca.moheektech.capstone.internal.ExportedApi

/**
 * Represents a disassembled instruction.
 *
 * @property id Instruction ID/mnemonic (architecture-specific enum value)
 * @property address Address of this instruction
 * @property size Size of instruction in bytes
 * @property bytes Raw machine code bytes
 * @property mnemonic Instruction mnemonic (e.g., "mov", "add", "ldr")
 * @property opStr Operands string representation (e.g., "rax, rbx")
 * @property detail Detailed instruction information (only available when detail mode is enabled)
 */

enum class CsArch(val value: Int) {
  ARM(0),
  ARM64(1),
  MIPS(2),
  X86(3),
  PPC(4),
  SPARC(5),
  SYSZ(6),
  XCORE(7),
  M68K(8),
  TMS320C64X(9),
  M680X(10),
  EVM(11),
  MOS65XX(12),
  WASM(13),
  BPF(14),
  RISCV(15),
  SH(16),
  TRICORE(17),
  ALPHA(18),
  HPPA(19),
  LOONGARCH(20),
  XTENSA(21),
  ARC(22),
  MAX(23),
  ALL(0xFFFF)
}

enum class CsMode(val value: Int) {
  LITTLE_ENDIAN(0),
  ARM(0),
  MODE_16(1 shl 1),
  MODE_32(1 shl 2),
  MODE_64(1 shl 3),
  THUMB(1 shl 4),
  MCLASS(1 shl 5),
  V8(1 shl 6),
  MICRO(1 shl 4),
  MIPS3(1 shl 5),
  MIPS32R6(1 shl 6),
  MIPS2(1 shl 7),
  V9(1 shl 4),
  QPX(1 shl 4),
  SPE(1 shl 5),
  BOOKE(1 shl 6),
  PS(1 shl 7),
  M68K_000(1 shl 1),
  M68K_010(1 shl 2),
  M68K_020(1 shl 3),
  M68K_030(1 shl 4),
  M68K_040(1 shl 5),
  M68K_060(1 shl 6),
  BIG_ENDIAN(1 shl 31),
  MIPS32(1 shl 2),
  MIPS64(1 shl 3),
  M680X_6301(1 shl 1),
  M680X_6309(1 shl 2),
  M680X_6800(1 shl 3),
  M680X_6801(1 shl 4),
  M680X_6805(1 shl 5),
  M680X_6808(1 shl 6),
  M680X_6809(1 shl 7),
  M680X_6811(1 shl 8),
  M680X_CPU12(1 shl 9),
  M680X_HCS08(1 shl 10),
  BPF_CLASSIC(0),
  BPF_EXTENDED(1 shl 0),
  RISCV32(1 shl 0),
  RISCV64(1 shl 1),
  RISCVC(1 shl 2),
  MOS65XX_6502(1 shl 1),
  MOS65XX_65C02(1 shl 2),
  MOS65XX_W65C02(1 shl 3),
  MOS65XX_65816(1 shl 4),
  MOS65XX_65816_LONG_M(1 shl 5),
  MOS65XX_65816_LONG_X(1 shl 6),
  MOS65XX_65816_LONG_MX((1 shl 5) or (1 shl 6)),
  SH2(1 shl 1),
  SH2A(1 shl 2),
  SH3(1 shl 3),
  SH4(1 shl 4),
  SH4A(1 shl 5),
  SHFPU(1 shl 6),
  SHDSP(1 shl 7),
  TRICORE_110(1 shl 1),
  TRICORE_120(1 shl 2),
  TRICORE_130(1 shl 3),
  TRICORE_131(1 shl 4),
  TRICORE_160(1 shl 5),
  TRICORE_161(1 shl 6),
  TRICORE_162(1 shl 7),
  TRICORE_180(1 shl 8)
}
