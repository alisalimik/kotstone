package ir.alisalimik.kotstone.exp.wasm

import ir.alisalimik.kotstone.exp.INumericEnum
import ir.alisalimik.kotstone.internal.ExportedApi
import ir.alisalimik.kotstone.internal.WASM_GRP_CONTROL
import ir.alisalimik.kotstone.internal.WASM_GRP_ENDING
import ir.alisalimik.kotstone.internal.WASM_GRP_INVALID
import ir.alisalimik.kotstone.internal.WASM_GRP_MEMORY
import ir.alisalimik.kotstone.internal.WASM_GRP_NUMBERIC
import ir.alisalimik.kotstone.internal.WASM_GRP_PARAMETRIC
import ir.alisalimik.kotstone.internal.WASM_GRP_VARIABLE

@ExportedApi
actual enum class WasmInstructionGroup(override val value: Int) : INumericEnum {
  INVALID(WASM_GRP_INVALID), // /< = CS_GRP_INVALID
  NUMBERIC(WASM_GRP_NUMBERIC),
  PARAMETRIC(WASM_GRP_PARAMETRIC),
  VARIABLE(WASM_GRP_VARIABLE),
  MEMORY(WASM_GRP_MEMORY),
  CONTROL(WASM_GRP_CONTROL),
  ENDING(WASM_GRP_ENDING), // /< <-- mark the end of the list of groups
}
