package ca.moheektech.capstone.exp.wasm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.ExportedApi
import ca.moheektech.capstone.internal.WASM_OP_BRTABLE
import ca.moheektech.capstone.internal.WASM_OP_IMM
import ca.moheektech.capstone.internal.WASM_OP_INT7
import ca.moheektech.capstone.internal.WASM_OP_INVALID
import ca.moheektech.capstone.internal.WASM_OP_NONE
import ca.moheektech.capstone.internal.WASM_OP_UINT32
import ca.moheektech.capstone.internal.WASM_OP_UINT64
import ca.moheektech.capstone.internal.WASM_OP_VARUINT32
import ca.moheektech.capstone.internal.WASM_OP_VARUINT64

@ExportedApi
actual enum class WasmOpType(override val value: Int) : INumericEnum {
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
