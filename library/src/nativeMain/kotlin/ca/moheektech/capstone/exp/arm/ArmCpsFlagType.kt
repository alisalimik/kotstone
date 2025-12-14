package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

actual enum class ArmCpsFlagType(override val value: UInt): INumericEnum {
    INVALID(ARM_CPSFLAG_INVALID),
    F(ARM_CPSFLAG_F),
    I(ARM_CPSFLAG_I),
    A(ARM_CPSFLAG_A),
    NONE(ARM_CPSFLAG_NONE)
}
