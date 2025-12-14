package ca.moheektech.capstone.internal.platform

import com.sun.jna.Union

internal class X86OpValue : Union() {
    @JvmField var reg: Int = 0
    @JvmField var imm: Long = 0
    @JvmField var mem: X86MemType = X86MemType()
}
