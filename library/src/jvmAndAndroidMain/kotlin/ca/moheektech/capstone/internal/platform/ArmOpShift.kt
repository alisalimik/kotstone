package ca.moheektech.capstone.internal.platform

import com.sun.jna.Structure

internal class ArmOpShift : Structure() {
    @JvmField var type: Int = 0
    @JvmField var value: Int = 0

    override fun getFieldOrder() = listOf("type", "value")
}
