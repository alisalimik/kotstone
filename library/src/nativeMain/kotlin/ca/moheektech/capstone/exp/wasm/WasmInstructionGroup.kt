package ca.moheektech.capstone.exp.wasm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class WasmInstructionGroup(override val value: UInt) : INumericEnum {
  INVALID(WASM_GRP_INVALID), // /< = CS_GRP_INVALID
  NUMBERIC(WASM_GRP_NUMBERIC),
  PARAMETRIC(WASM_GRP_PARAMETRIC),
  VARIABLE(WASM_GRP_VARIABLE),
  MEMORY(WASM_GRP_MEMORY),
  CONTROL(WASM_GRP_CONTROL),
  ENDING(WASM_GRP_ENDING), // /< <-- mark the end of the list of groups
}
