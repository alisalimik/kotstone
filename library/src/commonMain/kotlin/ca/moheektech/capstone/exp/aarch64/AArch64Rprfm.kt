package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone AArch64 range prefetch.
 */
expect enum class AArch64Rprfm : INumericEnum {

    // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_RPRFM> begin
    // clang-format off
    PLDKEEP, PLDSTRM, PSTKEEP, PSTSTRM,

    // clang-format on
    // generated content <AArch64GenCSSystemOperandsEnum.inc:GET_ENUM_VALUES_RPRFM> end
    ENDING,

}
