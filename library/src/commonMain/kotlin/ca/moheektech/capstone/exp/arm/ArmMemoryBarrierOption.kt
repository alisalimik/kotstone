package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone ARM memory barrier option.
 */
expect enum class ArmMemoryBarrierOption : INumericEnum {

            RESERVED_0,
            OSHLD,
            OSHST,
            OSH,
            RESERVED_4,
            NSHLD,
            NSHST,
            NSH,
            RESERVED_8,
            ISHLD,
            ISHST,
            ISH,
            RESERVED_12,
            LD,
            ST,
            SY,
    
}
