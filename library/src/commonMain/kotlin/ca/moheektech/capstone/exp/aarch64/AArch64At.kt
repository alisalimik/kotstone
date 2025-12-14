package ca.moheektech.capstone.exp.aarch64

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone AArch64 AT operations.
 */
expect enum class AArch64At : INumericEnum {
    S12E0R, S12E0W, S12E1R, S12E1W,
    S1E0R, S1E0W, S1E1A, S1E1R,
    S1E1RP, S1E1W, S1E1WP, S1E2A,
    S1E2R, S1E2W, S1E3A, S1E3R,
    S1E3W,
    ENDING,

}
