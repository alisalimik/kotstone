package ca.moheektech.capstone.exp.wasm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.WASM_GRP_CONTROL
import ca.moheektech.capstone.internal.WASM_GRP_ENDING
import ca.moheektech.capstone.internal.WASM_GRP_INVALID
import ca.moheektech.capstone.internal.WASM_GRP_MEMORY
import ca.moheektech.capstone.internal.WASM_GRP_NUMBERIC
import ca.moheektech.capstone.internal.WASM_GRP_PARAMETRIC
import ca.moheektech.capstone.internal.WASM_GRP_VARIABLE

actual enum class WasmInstructionGroup(override val value: Int): INumericEnum {
        INVALID(WASM_GRP_INVALID), ///< = CS_GRP_INVALID

        NUMBERIC(WASM_GRP_NUMBERIC),
        PARAMETRIC(WASM_GRP_PARAMETRIC),
        VARIABLE(WASM_GRP_VARIABLE),
        MEMORY(WASM_GRP_MEMORY),
        CONTROL(WASM_GRP_CONTROL),

        ENDING(WASM_GRP_ENDING), ///< <-- mark the end of the list of groups
    
}