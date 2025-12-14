package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone ARM setend type.
 */
expect enum class ArmSetEndType : INumericEnum {

        INVALID, ///< Uninitialized.
        BE, ///< BE operand.
        LE, ///< LE operand
    
}
