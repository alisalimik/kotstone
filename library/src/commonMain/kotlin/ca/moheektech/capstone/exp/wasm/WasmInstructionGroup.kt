package ca.moheektech.capstone.exp.wasm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone WASM instruction group.
 */
expect enum class WasmInstructionGroup : INumericEnum {

        INVALID, ///< = CS_GRP_INVALID

        NUMBERIC,
        PARAMETRIC,
        VARIABLE,
        MEMORY,
        CONTROL,

        ENDING, ///< <-- mark the end of the list of groups
    
}