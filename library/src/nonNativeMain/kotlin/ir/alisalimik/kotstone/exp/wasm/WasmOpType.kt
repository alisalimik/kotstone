package ir.alisalimik.kotstone.exp.wasm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.internal.WASM_OP_BRTABLE
import ir.alisalimik.kotstone.internal.WASM_OP_IMM
import ir.alisalimik.kotstone.internal.WASM_OP_INT7
import ir.alisalimik.kotstone.internal.WASM_OP_INVALID
import ir.alisalimik.kotstone.internal.WASM_OP_NONE
import ir.alisalimik.kotstone.internal.WASM_OP_UINT32
import ir.alisalimik.kotstone.internal.WASM_OP_UINT64
import ir.alisalimik.kotstone.internal.WASM_OP_VARUINT32
import ir.alisalimik.kotstone.internal.WASM_OP_VARUINT64

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
