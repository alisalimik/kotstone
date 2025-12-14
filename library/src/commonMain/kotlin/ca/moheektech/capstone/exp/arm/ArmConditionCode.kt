package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone ARM condition code.
 */
expect enum class ArmConditionCode : INumericEnum {
    EQ, NE, HS, LO, MI, PL, VS, VC,
    HI, LS, GE, LT, GT, LE, AL, UNDEF,
    Invalid
}
