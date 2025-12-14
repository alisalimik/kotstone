package ca.moheektech.capstone.internal.platform

import com.sun.jna.Union

internal class Arm64OpValue : Union() {
    @JvmField var reg: Int = 0
    @JvmField var imm: Long = 0
    @JvmField var fp: Double = 0.0
    @JvmField var mem: Arm64MemType = Arm64MemType()
}
