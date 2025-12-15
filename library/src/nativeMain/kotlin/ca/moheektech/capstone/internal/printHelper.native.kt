// package ca.moheektech.capstone.internal
//
// import ca.moheektech.capstone.exp.aarch64.AArch64At
// import ca.moheektech.capstone.exp.aarch64.AArch64Bti
// import ca.moheektech.capstone.exp.aarch64.AArch64ConditionCode
// import ca.moheektech.capstone.exp.aarch64.AArch64Db
// import ca.moheektech.capstone.exp.aarch64.AArch64DbNxs
// import ca.moheektech.capstone.exp.aarch64.AArch64Dc
// import ca.moheektech.capstone.exp.aarch64.AArch64ExactFpImm
// import ca.moheektech.capstone.exp.aarch64.AArch64Extender
// import ca.moheektech.capstone.exp.aarch64.AArch64Ic
// import ca.moheektech.capstone.exp.aarch64.AArch64Instruction
// import ca.moheektech.capstone.exp.aarch64.AArch64InstructionGroup
// import ca.moheektech.capstone.exp.aarch64.AArch64Isb
// import ca.moheektech.capstone.exp.aarch64.AArch64OpType
// import ca.moheektech.capstone.exp.aarch64.AArch64PStateImm0_1
// import ca.moheektech.capstone.exp.aarch64.AArch64PStateImm0_15
// import ca.moheektech.capstone.exp.aarch64.AArch64Prfm
// import ca.moheektech.capstone.exp.aarch64.AArch64Psb
// import ca.moheektech.capstone.exp.aarch64.AArch64Register
// import ca.moheektech.capstone.exp.aarch64.AArch64Rprfm
// import ca.moheektech.capstone.exp.aarch64.AArch64Shifter
// import ca.moheektech.capstone.exp.aarch64.AArch64Svcr
// import ca.moheektech.capstone.exp.aarch64.AArch64SvePredPattern
// import ca.moheektech.capstone.exp.aarch64.AArch64SvePrfm
// import ca.moheektech.capstone.exp.aarch64.AArch64SveVectorLengthSpecifier
// import ca.moheektech.capstone.exp.aarch64.AArch64SystemRegister
// import ca.moheektech.capstone.exp.aarch64.AArch64Tlbi
// import ca.moheektech.capstone.exp.aarch64.AArch64Tsb
// import ca.moheektech.capstone.exp.aarch64.AArch64VectorLayout
// import ca.moheektech.capstone.exp.arm.*
// import ca.moheektech.capstone.exp.wasm.WasmInstruction
// import ca.moheektech.capstone.exp.wasm.WasmInstructionGroup
// import ca.moheektech.capstone.exp.wasm.WasmOpType
// import ca.moheektech.capstone.exp.x86.X86Instruction
// import ca.moheektech.capstone.exp.x86.X86InstructionGroup
// import ca.moheektech.capstone.exp.x86.X86Register
// import ca.moheektech.capstone.exp.x86.X86SseConditionCode
// import ca.moheektech.capstone.exp.x86.X86XopConditionCode
// import kotlinx.io.buffered
// import kotlinx.io.files.Path
// import kotlinx.io.files.SystemFileSystem
// import kotlinx.io.writeString
// import kotlin.collections.iterator
//
// private val aarch64 = mapOf<String, List<Pair<String, UInt>>>(
//    "AARCH64_AT_" to AArch64At.entries.map { it.name to it.value },
//    "AARCH64_BTI_" to AArch64Bti.entries.map { it.name to it.value },
//    "AArch64CC_" to AArch64ConditionCode.entries.map { it.name to it.value },
//    "AARCH64_DB_" to AArch64Db.entries.map { it.name to it.value },
//    "AARCH64_DBNXS_" to AArch64DbNxs.entries.map { it.name to it.value },
//    "AARCH64_DC_" to AArch64Dc.entries.map { it.name to it.value },
//    "AARCH64_EXACTFPIMM_" to AArch64ExactFpImm.entries.map { it.name to it.value },
//    "AARCH64_EXT_" to AArch64Extender.entries.map { it.name to it.value },
//    "AARCH64_IC_" to AArch64Ic.entries.map { it.name to it.value },
//    "AARCH64_INS_" to AArch64Instruction.entries.map { it.name to it.value },
//    "AARCH64_FEATURE_" to AArch64InstructionGroup.entries.map { it.name to it.value },
//    "AARCH64_ISB_" to AArch64Isb.entries.map { it.name to it.value },
//    "AARCH64_OP_" to AArch64OpType.entries.map { it.name to it.value },
//    "AARCH64_PRFM_" to AArch64Prfm.entries.map { it.name to it.value },
//    "AARCH64_PSB_" to AArch64Psb.entries.map { it.name to it.value },
//    "AARCH64_PSTATEIMM0_1_" to AArch64PStateImm0_1.entries.map { it.name to it.value },
//    "AARCH64_PSTATEIMM0_15_" to AArch64PStateImm0_15.entries.map { it.name to it.value },
//    "AARCH64_REG_" to AArch64Register.entries.map { it.name to it.value },
//    "AARCH64_RPRFM_" to AArch64Rprfm.entries.map { it.name to it.value },
//    "AARCH64_SFT_" to AArch64Shifter.entries.map { it.name to it.value },
//    "AARCH64_SVCR_" to AArch64Svcr.entries.map { it.name to it.value },
//    "AARCH64_SVEPREDPAT_" to AArch64SvePredPattern.entries.map { it.name to it.value },
//    "AARCH64_SVEPRFM_" to AArch64SvePrfm.entries.map { it.name to it.value },
//    "AARCH64_SVEVECLENSPECIFIER_" to AArch64SveVectorLengthSpecifier.entries.map { it.name to
// it.value },
//    "AARCH64_SYSREG_" to AArch64SystemRegister.entries.map { it.name to it.value },
//    "AARCH64_TLBI_" to AArch64Tlbi.entries.map { it.name to it.value },
//    "AARCH64_TSB_" to AArch64Tsb.entries.map { it.name to it.value },
//    "AARCH64LAYOUT_" to AArch64VectorLayout.entries.map { it.name to it.value },
//
//    )
//
// private val arm = mapOf<String, List<Pair<String, UInt>>>(
//    "ARM_BANKEDREG_" to ArmBankedRegister.entries.map { it.name to it.value },
//    "ARMCC_" to ArmConditionCode.entries.map { it.name to it.value },
//    "ARM_CPSFLAG_" to ArmCpsFlagType.entries.map { it.name to it.value },
//    "ARM_CPSMODE_" to ArmCpsModeType.entries.map { it.name to it.value },
//    "ARM_INS_" to ArmInstruction.entries.map { it.name to it.value },
//    "ARM_FEATURE_" to ArmInstructionGroup.entries.map { it.name to it.value },
//    "ARM_MB_" to ArmMemoryBarrierOption.entries.map { it.name to it.value },
//    "ARM_OP_" to ArmOpType.entries.map { it.name to it.value },
//    "ARM_REG_" to ArmRegister.entries.map { it.name to it.value },
//    "ARM_SETEND_" to ArmSetEndType.entries.map { it.name to it.value },
//    "ARM_SFT_" to ArmShifter.entries.map { it.name to it.value },
//    "ARM_FIELD_" to ArmSpsrCpsrBits.entries.map { it.name to it.value },
//    "ARM_MCLASSSYSREG_" to ArmSystemRegister.entries.map { it.name to it.value },
//    "ARM_VECTORDATA_" to ArmVectorDataType.entries.map { it.name to it.value },
//    )
//
//
// private val x86 = mapOf<String, List<Pair<String, UInt>>>(
//    "X86_INS_" to X86Instruction.entries.map { it.name to it.value },
//    "X86_GRP_" to X86InstructionGroup.entries.map { it.name to it.value },
//    "X86_REG_" to X86Register.entries.map { it.name to it.value },
//    "X86_SSE_" to X86SseConditionCode.entries.map { it.name to it.value },
//    "X86_XOP_" to X86XopConditionCode.entries.map { it.name to it.value },
// )
//
// private val wasm = mapOf<String, List<Pair<String, UInt>>>(
//    "WASM_INS_" to WasmInstruction.entries.map { it.name to it.value },
//    "WASM_GRP_" to WasmInstructionGroup.entries.map { it.name to it.value },
//    "WASM_OP_" to WasmOpType.entries.map { it.name to it.value }
// )
//
// internal fun printEnumValues() {
//    val output = Path("/Volumes/T7/AndroidProjects/Il2CPPDecompiler/capstone/interop/scripts",
// "WasmEnumValues.kt")
//    var builtText = "package ca.moheektech.capstone.internal\n"
//    builtText += "\nimport kotlin.js.JsStatic"
//    builtText += "\nimport kotlin.jvm.JvmStatic"
//    for (entry in wasm) {
//        builtText += "\n\n"
//        entry.value.forEach {
//            builtText += "@JsStatic\n"
//            builtText += "@JvmStatic\n"
//            builtText += "internal const val ${entry.key}${it.first} = ${it.second.toInt()}\n\n"
//        }
//    }
//    SystemFileSystem.sink(output).buffered().use {
//        it.writeString(builtText)
//        it.flush()
//    }
// }
