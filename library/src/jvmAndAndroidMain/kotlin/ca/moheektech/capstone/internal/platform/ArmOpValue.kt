package ca.moheektech.capstone.internal.platform

import com.sun.jna.Union

internal class ArmOpValue : Union() {
    @JvmField var reg: Int = 0
    @JvmField var imm: Int = 0
    @JvmField var fp: Double = 0.0
    @JvmField var mem: ArmMemType = ArmMemType()
    @JvmField var setend: Int = 0
}
