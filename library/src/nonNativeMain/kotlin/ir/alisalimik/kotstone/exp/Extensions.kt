package ir.alisalimik.kotstone.exp

import ir.alisalimik.kotstone.exp.aarch64.AArch64Instruction
import ir.alisalimik.kotstone.exp.arm.ArmConditionCode
import ir.alisalimik.kotstone.exp.arm.ArmCpsFlagType
import ir.alisalimik.kotstone.exp.arm.ArmCpsModeType
import ir.alisalimik.kotstone.exp.arm.ArmInstruction
import ir.alisalimik.kotstone.exp.arm.ArmMemoryBarrierOption
import ir.alisalimik.kotstone.exp.arm.ArmOpType
import ir.alisalimik.kotstone.exp.arm.ArmSetEndType
import ir.alisalimik.kotstone.exp.arm.ArmShifter
import ir.alisalimik.kotstone.exp.arm.ArmVectorDataType
import ir.alisalimik.kotstone.exp.x86.X86Instruction
import ir.alisalimik.kotstone.internal.ARM_CPSFLAG_A
import ir.alisalimik.kotstone.internal.ARM_CPSFLAG_F
import ir.alisalimik.kotstone.internal.ARM_CPSFLAG_I
import ir.alisalimik.kotstone.internal.ARM_CPSFLAG_NONE
import ir.alisalimik.kotstone.internal.ARM_CPSMODE_ID
import ir.alisalimik.kotstone.internal.ARM_CPSMODE_IE
import ir.alisalimik.kotstone.internal.ARM_MB_ISH
import ir.alisalimik.kotstone.internal.ARM_MB_ISHLD
import ir.alisalimik.kotstone.internal.ARM_MB_ISHST
import ir.alisalimik.kotstone.internal.ARM_MB_LD
import ir.alisalimik.kotstone.internal.ARM_MB_NSH
import ir.alisalimik.kotstone.internal.ARM_MB_NSHLD
import ir.alisalimik.kotstone.internal.ARM_MB_NSHST
import ir.alisalimik.kotstone.internal.ARM_MB_OSH
import ir.alisalimik.kotstone.internal.ARM_MB_OSHLD
import ir.alisalimik.kotstone.internal.ARM_MB_OSHST
import ir.alisalimik.kotstone.internal.ARM_MB_ST
import ir.alisalimik.kotstone.internal.ARM_MB_SY
import ir.alisalimik.kotstone.internal.ARM_SETEND_BE
import ir.alisalimik.kotstone.internal.ARM_SETEND_LE
import ir.alisalimik.kotstone.internal.ARM_SFT_ASR
import ir.alisalimik.kotstone.internal.ARM_SFT_ASR_REG
import ir.alisalimik.kotstone.internal.ARM_SFT_LSL
import ir.alisalimik.kotstone.internal.ARM_SFT_LSL_REG
import ir.alisalimik.kotstone.internal.ARM_SFT_LSR
import ir.alisalimik.kotstone.internal.ARM_SFT_LSR_REG
import ir.alisalimik.kotstone.internal.ARM_SFT_REG
import ir.alisalimik.kotstone.internal.ARM_SFT_ROR
import ir.alisalimik.kotstone.internal.ARM_SFT_ROR_REG
import ir.alisalimik.kotstone.internal.ARM_SFT_RRX
import ir.alisalimik.kotstone.internal.ARM_SFT_UXTW
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16S16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16S32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16U16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F16U32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32S16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32S32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32U16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F32U32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64S16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64S32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64U16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_F64U32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_I16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_I32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_I64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_I8
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_P16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_P8
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S16F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S16F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S16F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S32F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S32F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S32F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_S8
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U16F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U16F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U16F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U32F16
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U32F32
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U32F64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U64
import ir.alisalimik.kotstone.internal.ARM_VECTORDATA_U8
import ir.alisalimik.kotstone.internal.CS_OP_FP
import ir.alisalimik.kotstone.internal.CS_OP_IMM
import ir.alisalimik.kotstone.internal.CS_OP_INVALID
import ir.alisalimik.kotstone.internal.CS_OP_MEM
import ir.alisalimik.kotstone.internal.CS_OP_PRED
import ir.alisalimik.kotstone.internal.CS_OP_REG
import ir.alisalimik.kotstone.internal.CS_OP_SPECIAL

private val x86InstructionMap by lazy { X86Instruction.entries.associateBy { it.value } }

actual fun Int.toX86Instruction(): X86Instruction =
    x86InstructionMap[this] ?: X86Instruction.INVALID

private val armInstructionMap by lazy { ArmInstruction.entries.associateBy { it.value } }

actual fun Int.toArmInstruction(): ArmInstruction =
    armInstructionMap[this] ?: ArmInstruction.INVALID

private val aarch64InstructionMap by lazy { AArch64Instruction.entries.associateBy { it.value } }

actual fun Int.toAArch64Instruction(): AArch64Instruction =
    aarch64InstructionMap[this] ?: AArch64Instruction.INVALID

internal actual fun Int.toArmOpType(): ArmOpType =
    when (this) {
      CS_OP_INVALID -> ArmOpType.INVALID
      CS_OP_REG -> ArmOpType.REG
      CS_OP_IMM -> ArmOpType.IMM
      CS_OP_FP -> ArmOpType.FP
      CS_OP_PRED -> ArmOpType.PRED
      (CS_OP_SPECIAL + 0) -> ArmOpType.CIMM
      (CS_OP_SPECIAL + 1) -> ArmOpType.PIMM
      (CS_OP_SPECIAL + 2) -> ArmOpType.SETEND
      (CS_OP_SPECIAL + 3) -> ArmOpType.SYSREG
      (CS_OP_SPECIAL + 4) -> ArmOpType.BANKEDREG
      (CS_OP_SPECIAL + 5) -> ArmOpType.SPSR
      (CS_OP_SPECIAL + 6) -> ArmOpType.CPSR
      (CS_OP_SPECIAL + 7) -> ArmOpType.SYSM
      (CS_OP_SPECIAL + 8) -> ArmOpType.VPRED_R
      (CS_OP_SPECIAL + 9) -> ArmOpType.VPRED_N
      CS_OP_MEM -> ArmOpType.MEM
      else -> ArmOpType.INVALID
    }

internal actual fun Int.toArmConditionCode(): ArmConditionCode =
    ArmConditionCode.entries.getOrNull(this) ?: ArmConditionCode.Invalid

internal actual fun Int.toArmVectorDataType(): ArmVectorDataType =
    when (this) {
      ARM_VECTORDATA_I8 -> ArmVectorDataType.I8
      ARM_VECTORDATA_I16 -> ArmVectorDataType.I16
      ARM_VECTORDATA_I32 -> ArmVectorDataType.I32
      ARM_VECTORDATA_I64 -> ArmVectorDataType.I64
      ARM_VECTORDATA_S8 -> ArmVectorDataType.S8
      ARM_VECTORDATA_S16 -> ArmVectorDataType.S16
      ARM_VECTORDATA_S32 -> ArmVectorDataType.S32
      ARM_VECTORDATA_S64 -> ArmVectorDataType.S64
      ARM_VECTORDATA_U8 -> ArmVectorDataType.U8
      ARM_VECTORDATA_U16 -> ArmVectorDataType.U16
      ARM_VECTORDATA_U32 -> ArmVectorDataType.U32
      ARM_VECTORDATA_U64 -> ArmVectorDataType.U64
      ARM_VECTORDATA_P8 -> ArmVectorDataType.P8
      ARM_VECTORDATA_P16 -> ArmVectorDataType.P16
      ARM_VECTORDATA_F16 -> ArmVectorDataType.F16
      ARM_VECTORDATA_F32 -> ArmVectorDataType.F32
      ARM_VECTORDATA_F64 -> ArmVectorDataType.F64
      ARM_VECTORDATA_F16F64 -> ArmVectorDataType.F16F64
      ARM_VECTORDATA_F64F16 -> ArmVectorDataType.F64F16
      ARM_VECTORDATA_F32F16 -> ArmVectorDataType.F32F16
      ARM_VECTORDATA_F16F32 -> ArmVectorDataType.F16F32
      ARM_VECTORDATA_F64F32 -> ArmVectorDataType.F64F32
      ARM_VECTORDATA_F32F64 -> ArmVectorDataType.F32F64
      ARM_VECTORDATA_S32F32 -> ArmVectorDataType.S32F32
      ARM_VECTORDATA_U32F32 -> ArmVectorDataType.U32F32
      ARM_VECTORDATA_F32S32 -> ArmVectorDataType.F32S32
      ARM_VECTORDATA_F32U32 -> ArmVectorDataType.F32U32
      ARM_VECTORDATA_F64S16 -> ArmVectorDataType.F64S16
      ARM_VECTORDATA_F32S16 -> ArmVectorDataType.F32S16
      ARM_VECTORDATA_F64S32 -> ArmVectorDataType.F64S32
      ARM_VECTORDATA_S16F64 -> ArmVectorDataType.S16F64
      ARM_VECTORDATA_S16F32 -> ArmVectorDataType.S16F32
      ARM_VECTORDATA_S32F64 -> ArmVectorDataType.S32F64
      ARM_VECTORDATA_U16F64 -> ArmVectorDataType.U16F64
      ARM_VECTORDATA_U16F32 -> ArmVectorDataType.U16F32
      ARM_VECTORDATA_U32F64 -> ArmVectorDataType.U32F64
      ARM_VECTORDATA_F64U16 -> ArmVectorDataType.F64U16
      ARM_VECTORDATA_F32U16 -> ArmVectorDataType.F32U16
      ARM_VECTORDATA_F64U32 -> ArmVectorDataType.F64U32
      ARM_VECTORDATA_F16U16 -> ArmVectorDataType.F16U16
      ARM_VECTORDATA_U16F16 -> ArmVectorDataType.U16F16
      ARM_VECTORDATA_F16U32 -> ArmVectorDataType.F16U32
      ARM_VECTORDATA_U32F16 -> ArmVectorDataType.U32F16
      ARM_VECTORDATA_F16S16 -> ArmVectorDataType.F16S16
      ARM_VECTORDATA_S16F16 -> ArmVectorDataType.S16F16
      ARM_VECTORDATA_F16S32 -> ArmVectorDataType.F16S32
      ARM_VECTORDATA_S32F16 -> ArmVectorDataType.S32F16
      else -> ArmVectorDataType.INVALID
    }

internal actual fun Int.toArmCpsModeType(): ArmCpsModeType =
    when (this) {
      ARM_CPSMODE_IE -> ArmCpsModeType.IE
      ARM_CPSMODE_ID -> ArmCpsModeType.ID
      else -> ArmCpsModeType.INVALID
    }

internal actual fun Int.toArmCpsFlagType(): ArmCpsFlagType =
    when (this) {
      ARM_CPSFLAG_F -> ArmCpsFlagType.F
      ARM_CPSFLAG_I -> ArmCpsFlagType.I
      ARM_CPSFLAG_A -> ArmCpsFlagType.A
      ARM_CPSFLAG_NONE -> ArmCpsFlagType.NONE
      else -> ArmCpsFlagType.INVALID
    }

internal actual fun Int.toArmShifter(): ArmShifter =
    when (this) {
      ARM_SFT_ASR -> ArmShifter.ASR
      ARM_SFT_LSL -> ArmShifter.LSL
      ARM_SFT_LSR -> ArmShifter.LSR
      ARM_SFT_ROR -> ArmShifter.ROR
      ARM_SFT_RRX -> ArmShifter.RRX
      ARM_SFT_UXTW -> ArmShifter.UXTW
      ARM_SFT_REG -> ArmShifter.REG
      ARM_SFT_ASR_REG -> ArmShifter.ASR_REG
      ARM_SFT_LSL_REG -> ArmShifter.LSL_REG
      ARM_SFT_LSR_REG -> ArmShifter.LSR_REG
      ARM_SFT_ROR_REG -> ArmShifter.ROR_REG
      else -> ArmShifter.INVALID
    }

internal actual fun Int.toArmSetEndType(): ArmSetEndType =
    when (this) {
      ARM_SETEND_BE -> ArmSetEndType.BE
      ARM_SETEND_LE -> ArmSetEndType.LE
      else -> ArmSetEndType.INVALID
    }

internal actual fun Int.toArmMemoryBarrierOption(): ArmMemoryBarrierOption =
    when (this) {
      ARM_MB_OSHLD -> ArmMemoryBarrierOption.OSHLD
      ARM_MB_OSHST -> ArmMemoryBarrierOption.OSHST
      ARM_MB_OSH -> ArmMemoryBarrierOption.OSH
      ARM_MB_NSHLD -> ArmMemoryBarrierOption.NSHLD
      ARM_MB_NSHST -> ArmMemoryBarrierOption.NSHST
      ARM_MB_NSH -> ArmMemoryBarrierOption.NSH
      ARM_MB_ISHLD -> ArmMemoryBarrierOption.ISHLD
      ARM_MB_ISHST -> ArmMemoryBarrierOption.ISHST
      ARM_MB_ISH -> ArmMemoryBarrierOption.ISH
      ARM_MB_LD -> ArmMemoryBarrierOption.LD
      ARM_MB_ST -> ArmMemoryBarrierOption.ST
      ARM_MB_SY -> ArmMemoryBarrierOption.SY
      else -> ArmMemoryBarrierOption.RESERVED_0
    }
