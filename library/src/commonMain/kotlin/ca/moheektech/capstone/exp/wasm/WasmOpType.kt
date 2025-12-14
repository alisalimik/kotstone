package ca.moheektech.capstone.exp.wasm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone WASM operand type.
 */
expect enum class WasmOpType : INumericEnum {
        INVALID,
        IMM,
        NONE,
        INT7,
        VARUINT32,
        VARUINT64,
        UINT32,
        UINT64,
        BRTABLE,
    
}