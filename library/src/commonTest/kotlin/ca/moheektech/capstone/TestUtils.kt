package ca.moheektech.capstone

import ca.moheektech.capstone.api.CapstoneEngine
import ca.moheektech.capstone.enums.Architecture

/**
 * Check if an architecture is supported on this platform. Returns true if supported, false
 * otherwise.
 */
fun isArchitectureSupported(arch: Architecture): Boolean {
  return try {
    CapstoneEngine.isSupported(arch)
  } catch (e: Exception) {
    false
  }
}
