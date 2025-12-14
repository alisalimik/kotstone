package platform

import org.gradle.internal.os.OperatingSystem

object Host {
    val os: OperatingSystem = OperatingSystem.current()

    val isMac = os.isMacOsX
    val isLinux = os.isLinux
    val isWindows = os.isWindows

    val isLinuxArm64: Boolean =
        isLinux && System.getProperty("os.arch") in setOf("aarch64", "arm64")
}