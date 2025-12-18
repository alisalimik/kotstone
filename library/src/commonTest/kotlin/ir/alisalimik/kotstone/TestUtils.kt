package ir.alisalimik.kotstone

import ir.alisalimik.kotstone.api.CapstoneEngine
import ir.alisalimik.kotstone.enums.Architecture

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
