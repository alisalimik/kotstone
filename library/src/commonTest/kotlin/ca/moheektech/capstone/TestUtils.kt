package ca.moheektech.capstone

import ca.moheektech.capstone.api.CapstoneEngine
import ca.moheektech.capstone.enums.Architecture

expect suspend fun initCapstoneTesting()

/**
 * Check if an architecture is supported on this platform.
 * Returns true if supported, false otherwise.
 */
fun isArchitectureSupported(arch: Architecture): Boolean {
    return try {
        CapstoneEngine.isSupported(arch)
    } catch (e: Exception) {
        false
    }
}

/**
 * Skip a test if the architecture is not supported.
 * Call this at the start of architecture-specific tests.
 */
fun requireArchitectureSupport(arch: Architecture) {
    if (!isArchitectureSupported(arch)) {
        println("Skipping test: ${arch.name} architecture not supported on this platform")
        // We can't truly skip in kotlin.test without assumption framework,
        // so we'll just return early from tests that check this
    }
}
