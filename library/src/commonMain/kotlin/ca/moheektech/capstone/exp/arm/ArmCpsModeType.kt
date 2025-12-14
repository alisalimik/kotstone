package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone ARM CPS mode type.
 */
expect enum class ArmCpsModeType : INumericEnum {
    INVALID,
    IE,
    ID
}
