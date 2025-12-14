package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class ArmSetEndType(override val value: UInt): INumericEnum {

        INVALID(ARM_SETEND_INVALID), ///< Uninitialized.
        BE(ARM_SETEND_BE), ///< BE operand.
        LE(ARM_SETEND_LE), ///< LE operand
    
}
