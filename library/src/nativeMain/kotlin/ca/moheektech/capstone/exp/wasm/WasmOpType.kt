package ca.moheektech.capstone.exp.wasm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class WasmOpType(override val value: UInt): INumericEnum {
        INVALID(WASM_OP_INVALID),
        IMM(WASM_OP_IMM),
        NONE(WASM_OP_NONE),
        INT7(WASM_OP_INT7),
        VARUINT32(WASM_OP_VARUINT32),
        VARUINT64(WASM_OP_VARUINT64),
        UINT32(WASM_OP_UINT32),
        UINT64(WASM_OP_UINT64),
        BRTABLE(WASM_OP_BRTABLE)
}