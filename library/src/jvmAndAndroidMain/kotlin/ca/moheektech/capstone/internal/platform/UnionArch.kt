package ca.moheektech.capstone.internal.platform

import com.sun.jna.Union

/**
 * Union for architecture-specific details
 */
internal open class UnionArch : Union() {
    @JvmField var arm: ArmUnionOpInfo? = null
    @JvmField var arm64: Arm64UnionOpInfo? = null
    @JvmField var x86: X86UnionOpInfo? = null
    // Add other architectures as needed
}
