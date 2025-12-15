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
