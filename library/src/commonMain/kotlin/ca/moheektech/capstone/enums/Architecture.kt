@file:ExportedApi

package ca.moheektech.capstone.enums

import ca.moheektech.capstone.internal.ExportedApi
import kotlin.js.JsStatic
import kotlin.jvm.JvmStatic

/**
 * Capstone supported architectures.
 *
 * Maps to cs_arch enum from capstone.h
 */
enum class Architecture(val value: Int) {
  /** ARM architecture (including Thumb, Thumb-2) */
  ARM(0),

  /** ARM64 (AArch64) architecture */
  ARM64(1),

  /** MIPS architecture */
  MIPS(2),

  /** X86 architecture */
  X86(3),

  /** PowerPC architecture */
  PPC(4),

  /** Sparc architecture */
  SPARC(5),

  /** SystemZ architecture */
  SYSZ(6),

  /** XCore architecture */
  XCORE(7),

  /** M68K architecture */
  M68K(8),

  /** TMS320C64x architecture */
  TMS320C64X(9),

  /** M680X architecture */
  M680X(10),

  /** Ethereum VM architecture */
  EVM(11),

  /** MOS65XX architecture (including MOS6502) */
  MOS65XX(12),

  /** WebAssembly architecture */
  WASM(13),

  /** Berkeley Packet Filter architecture */
  BPF(14),

  /** RISC-V architecture */
  RISCV(15),

  /** SuperH architecture */
  SH(16),

  /** TriCore architecture */
  TRICORE(17),

  /** Alpha architecture */
  ALPHA(18),

  /** HPPA architecture */
  HPPA(19),

  /** LoongArch architecture */
  LOONGARCH(20),

  /** Xtensa architecture */
  XTENSA(21),

  /** ARC architecture */
  ARC(22),

  /** Max value (used for bounds checking) */
  MAX(23),

  /** All architectures (for cs_support() queries) */
  ALL(0xFFFF);

  companion object {
    /** Convert integer value to Architecture enum */
    @JsStatic
    @JvmStatic
    fun fromValue(value: Int): Architecture? = entries.firstOrNull { it.value == value }
  }
}
