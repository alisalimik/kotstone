@file:ExportedApi

package ca.moheektech.capstone.error

import ca.moheektech.capstone.internal.ExportedApi
import kotlin.js.JsStatic
import kotlin.jvm.JvmStatic

/** Capstone error codes matching cs_err enum from capstone.h */
enum class ErrorCode(val value: Int) {
  /** No error - operation was successful */
  OK(0),

  /** Out of memory */
  OUT_OF_MEMORY(1),

  /** Unsupported architecture */
  UNSUPPORTED_ARCH(2),

  /** Invalid handle */
  INVALID_HANDLE(3),

  /** Invalid csh argument */
  INVALID_CSH(4),

  /** Invalid/unsupported mode */
  INVALID_MODE(5),

  /** Invalid/unsupported option */
  INVALID_OPTION(6),

  /** Information is unavailable because detail option is OFF */
  DETAIL_OFF(7),

  /** Dynamic memory management uninitialized (see CS_OPT_MEM) */
  MEM_SETUP(8),

  /** Unsupported version (bindings) */
  UNSUPPORTED_VERSION(9),

  /** Access irrelevant data in "diet" engine */
  DIET(10),

  /** Access irrelevant data in SKIPDATA mode */
  SKIPDATA(11),

  /** X86 AT&T syntax is unsupported (opt-out at compile time) */
  X86_ATT(12),

  /** X86 Intel syntax is unsupported (opt-out at compile time) */
  X86_INTEL(13),

  /** X86 MASM syntax is unsupported (opt-out at compile time) */
  X86_MASM(14);

  companion object {
    /** Convert integer error code to ErrorCode enum */
    @JsStatic
    @JvmStatic
    fun fromValue(value: Int): ErrorCode = entries.firstOrNull { it.value == value } ?: OK
  }
}
