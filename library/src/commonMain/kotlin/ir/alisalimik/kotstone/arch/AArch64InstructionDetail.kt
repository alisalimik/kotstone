package ir.alisalimik.kotstone.arch

import ir.alisalimik.kotstone.exp.aarch64.AArch64ConditionCode
import ir.alisalimik.kotstone.internal.ExportedApi

/**
 * AArch64 (ARM64) instruction details.
 *
 * @property cc Condition code for conditional instructions
 * @property updateFlags True if instruction updates condition flags
 * @property writeback True if instruction uses writeback addressing
 * @property postIndex True if post-indexed, false if pre-indexed
 * @property operands List of operands for this instruction
 */
@ExportedApi
data class AArch64InstructionDetail(
    val cc: AArch64ConditionCode = AArch64ConditionCode.Invalid,
    val updateFlags: Boolean = false,
    val writeback: Boolean = false,
    val postIndex: Boolean = false,
    val operands: List<AArch64Operand> = emptyList()
)
