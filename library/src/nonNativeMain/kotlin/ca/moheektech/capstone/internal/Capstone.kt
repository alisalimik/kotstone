@file:OptIn(ExperimentalJsStatic::class)

package ca.moheektech.capstone.internal

import kotlin.js.ExperimentalJsStatic

// Capstone API version
internal const val CS_API_MAJOR: Int = 5
internal const val CS_API_MINOR: Int = 0

// architectures
internal const val CS_ARCH_ARM: Int = 0
internal const val CS_ARCH_ARM64: Int = 1
internal const val CS_ARCH_MIPS: Int = 2
internal const val CS_ARCH_X86: Int = 3
internal const val CS_ARCH_PPC: Int = 4
internal const val CS_ARCH_SPARC: Int = 5
internal const val CS_ARCH_SYSZ: Int = 6
internal const val CS_ARCH_XCORE: Int = 7
internal const val CS_ARCH_M68K: Int = 8
internal const val CS_ARCH_TMS320C64X: Int = 9
internal const val CS_ARCH_M680X: Int = 10
internal const val CS_ARCH_MAX: Int = 11
internal const val CS_ARCH_ALL: Int = 0xFFFF // query id for cs_support()

// disasm mode
internal const val CS_MODE_LITTLE_ENDIAN: Int = 0 // little-endian mode (default mode)
internal const val CS_MODE_ARM: Int = 0 // 32-bit ARM
internal const val CS_MODE_16: Int = 1 shl 1 // 16-bit mode for X86
internal const val CS_MODE_32: Int = 1 shl 2 // 32-bit mode for X86
internal const val CS_MODE_64: Int = 1 shl 3 // 64-bit mode for X86, PPC
internal const val CS_MODE_THUMB: Int = 1 shl 4 // ARM's Thumb mode, including Thumb-2
internal const val CS_MODE_MCLASS: Int = 1 shl 5 // ARM's Cortex-M series
internal const val CS_MODE_V8: Int = 1 shl 6 // ARMv8 A32 encodings for ARM
internal const val CS_MODE_MICRO: Int = 1 shl 4 // MicroMips mode (Mips arch)
internal const val CS_MODE_MIPS3: Int = 1 shl 5 // Mips III ISA
internal const val CS_MODE_MIPS32R6: Int = 1 shl 6 // Mips32r6 ISA
internal const val CS_MODE_MIPS2: Int = 1 shl 7 // Mips II ISA
internal const val CS_MODE_BIG_ENDIAN: Int = 1 shl 31 // big-endian mode
internal const val CS_MODE_V9: Int = 1 shl 4 // SparcV9 mode (Sparc arch)
internal const val CS_MODE_MIPS32: Int = CS_MODE_32 // Mips32 ISA
internal const val CS_MODE_MIPS64: Int = CS_MODE_64 // Mips64 ISA
internal const val CS_MODE_QPX: Int = 1 shl 4 // Quad Processing eXtensions mode (PPC)
internal const val CS_MODE_SPE: Int = 1 shl 5 // Signal Processing Engine mode (PPC)
internal const val CS_MODE_BOOKE: Int = 1 shl 6 // Book-E mode (PPC)
internal const val CS_MODE_PS: Int = 1 shl 7 // Paired-singles mode (PPC)
internal const val CS_MODE_M680X_6301: Int = 1 shl 1 // M680X Hitachi 6301,6303 mode
internal const val CS_MODE_M680X_6309: Int = 1 shl 2 // M680X Hitachi 6309 mode
internal const val CS_MODE_M680X_6800: Int = 1 shl 3 // M680X Motorola 6800,6802 mode
internal const val CS_MODE_M680X_6801: Int = 1 shl 4 // M680X Motorola 6801,6803 mode
internal const val CS_MODE_M680X_6805: Int = 1 shl 5 // M680X Motorola 6805 mode
internal const val CS_MODE_M680X_6808: Int = 1 shl 6 // M680X Motorola 6808 mode
internal const val CS_MODE_M680X_6809: Int = 1 shl 7 // M680X Motorola 6809 mode
internal const val CS_MODE_M680X_6811: Int = 1 shl 8 // M680X Motorola/Freescale 68HC11 mode
internal const val CS_MODE_M680X_CPU12: Int = 1 shl 9 // M680X Motorola/Freescale/NXP CPU12 mode
internal const val CS_MODE_M680X_HCS08: Int = 1 shl 10 // M680X Freescale HCS08 mode
internal const val CS_MODE_TRICORE_110 = 1 shl 1 // /< Tricore 1.1
internal const val CS_MODE_TRICORE_120 = 1 shl 2 // /< Tricore 1.2
internal const val CS_MODE_TRICORE_130 = 1 shl 3 // /< Tricore 1.3
internal const val CS_MODE_TRICORE_131 = 1 shl 4 // /< Tricore 1.3.1
internal const val CS_MODE_TRICORE_160 = 1 shl 5 // /< Tricore 1.6
internal const val CS_MODE_TRICORE_161 = 1 shl 6 // /< Tricore 1.6.1
internal const val CS_MODE_TRICORE_162 = 1 shl 7 // /< Tricore 1.6.2
internal const val CS_MODE_TRICORE_180 = 1 shl 8 // /< Tricore 1.8.0

internal const val CS_MODE_M68K_000 = 1 shl 1 // /< M68K 68000 mode
internal const val CS_MODE_M68K_010 = 1 shl 2 // /< M68K 68010 mode
internal const val CS_MODE_M68K_020 = 1 shl 3 // /< M68K 68020 mode
internal const val CS_MODE_M68K_030 = 1 shl 4 // /< M68K 68030 mode
internal const val CS_MODE_M68K_040 = 1 shl 5 // /< M68K 68040 mode
internal const val CS_MODE_M68K_060 = 1 shl 6 // /< M68K 68060 mode

internal const val CS_MODE_SH2 = 1 shl 1 // /< SH2
internal const val CS_MODE_SH2A = 1 shl 2 // /< SH2A
internal const val CS_MODE_SH3 = 1 shl 3 // /< SH3
internal const val CS_MODE_SH4 = 1 shl 4 // /< SH4
internal const val CS_MODE_SH4A = 1 shl 5 // /< SH4A
internal const val CS_MODE_SHFPU = 1 shl 6 // /< w/ FPU
internal const val CS_MODE_SHDSP = 1 shl 7 // /< w/ DSP

internal const val CS_MODE_BPF_CLASSIC = 0 // /< Classic BPF mode (default)
internal const val CS_MODE_BPF_EXTENDED = 1 shl 0 // /< Extended BPF mode
internal const val CS_MODE_RISCV32 = 1 shl 0 // /< RISCV RV32G
internal const val CS_MODE_RISCV64 = 1 shl 1 // /< RISCV RV64G
internal const val CS_MODE_RISCVC = 1 shl 2 // /< RISCV compressed instructure mode
internal const val CS_MODE_MOS65XX_6502 = 1 shl 1 // /< MOS65XXX MOS 6502
internal const val CS_MODE_MOS65XX_65C02 = 1 shl 2 // /< MOS65XXX WDC 65c02
internal const val CS_MODE_MOS65XX_W65C02 = 1 shl 3 // /< MOS65XXX WDC W65c02
internal const val CS_MODE_MOS65XX_65816 = 1 shl 4 // /< MOS65XXX WDC 65816, 8-bit m/x
internal const val CS_MODE_MOS65XX_65816_LONG_M =
    (1 shl 5) // /< MOS65XXX WDC 65816, 16-bit m, 8-bit x
internal const val CS_MODE_MOS65XX_65816_LONG_X =
    (1 shl 6) // /< MOS65XXX WDC 65816, 8-bit m, 16-bit x
internal const val CS_MODE_MOS65XX_65816_LONG_MX =
    CS_MODE_MOS65XX_65816_LONG_M or CS_MODE_MOS65XX_65816_LONG_X

// Capstone error
internal const val CS_ERR_OK: Int = 0
internal const val CS_ERR_MEM: Int = 1 // Out-Of-Memory error
internal const val CS_ERR_ARCH: Int = 2 // Unsupported architecture
internal const val CS_ERR_HANDLE: Int = 3 // Invalid handle
internal const val CS_ERR_CSH: Int = 4 // Invalid csh argument
internal const val CS_ERR_MODE: Int = 5 // Invalid/unsupported mode
internal const val CS_ERR_OPTION: Int = 6 // Invalid/unsupported option: cs_option()
internal const val CS_ERR_DETAIL: Int = 7 // Invalid/unsupported option: cs_option()
internal const val CS_ERR_MEMSETUP: Int = 8
internal const val CS_ERR_VERSION: Int = 9 // Unsupported version (bindings)
internal const val CS_ERR_DIET: Int = 10 // Information irrelevant in diet engine
internal const val CS_ERR_SKIPDATA: Int =
    11 // Access irrelevant data for "data" instruction in SKIPDATA mode
internal const val CS_ERR_X86_ATT: Int =
    12 // X86 AT&T syntax is unsupported (opt-out at compile time)
internal const val CS_ERR_X86_INTEL: Int =
    13 // X86 Intel syntax is unsupported (opt-out at compile time)

// Capstone option type
internal const val CS_OPT_SYNTAX: Int = 1 // Intel X86 asm syntax (CS_ARCH_X86 arch)
internal const val CS_OPT_DETAIL: Int = 2 // Break down instruction structure into details
internal const val CS_OPT_MODE: Int = 3 // Change engine's mode at run-time

// Capstone option value
internal const val CS_OPT_OFF: Int = 0 // Turn OFF an option - default option of CS_OPT_DETAIL
internal const val CS_OPT_SYNTAX_INTEL: Int =
    1 // Intel X86 asm syntax - default syntax on X86 (CS_OPT_SYNTAX,  CS_ARCH_X86)
internal const val CS_OPT_SYNTAX_ATT: Int = 2 // ATT asm syntax (CS_OPT_SYNTAX, CS_ARCH_X86)
internal const val CS_OPT_ON: Int = 3 // Turn ON an option (CS_OPT_DETAIL)
internal const val CS_OPT_SYNTAX_NOREGNAME: Int =
    3 // PPC asm syntax: Prints register name with only number (CS_OPT_SYNTAX)

// Common instruction operand types - to be consistent across all architectures.
internal const val CS_OP_INVALID: Int = 0
internal const val CS_OP_REG: Int = 1
internal const val CS_OP_IMM: Int = 2
internal const val CS_OP_MEM: Int = 3
internal const val CS_OP_FP: Int = 4
internal const val CS_OP_SPECIAL: Int = 5
internal const val CS_OP_PRED: Int = 6
internal const val PPC_OP_CRX: Int = 7

// Common instruction operand access types - to be consistent across all architectures.
// It is possible to combine access types, for example: CS_AC_READ | CS_AC_WRITE
internal const val CS_AC_INVALID: Int = 0
internal const val CS_AC_READ: Int = 1 shl 0
internal const val CS_AC_WRITE: Int = 1 shl 1

// Common instruction groups - to be consistent across all architectures.
internal const val CS_GRP_INVALID: Int = 0 // uninitialized/invalid group.
internal const val CS_GRP_JUMP: Int = 1 // all jump instructions (conditional+direct+indirect jumps)
internal const val CS_GRP_CALL: Int = 2 // all call instructions
internal const val CS_GRP_RET: Int = 3 // all return instructions
internal const val CS_GRP_INT: Int = 4 // all interrupt instructions (int+syscall)
internal const val CS_GRP_IRET: Int = 5 // all interrupt return instructions
internal const val CS_GRP_PRIVILEGE: Int = 6 // all privileged instructions

// Query id for cs_support()
internal const val CS_SUPPORT_DIET: Int = CS_ARCH_ALL + 1 // diet mode
internal const val CS_SUPPORT_X86_REDUCE: Int = CS_ARCH_ALL + 2 // X86 reduce mode
