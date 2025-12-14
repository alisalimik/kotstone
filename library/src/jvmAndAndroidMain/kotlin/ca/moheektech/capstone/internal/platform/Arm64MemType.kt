package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

// ARM64 structures
internal class Arm64MemType : Structure() {
    @JvmField var base: Int = 0
    @JvmField var index: Int = 0
    @JvmField var disp: Int = 0

    override fun getFieldOrder() = listOf("base", "index", "disp")
}