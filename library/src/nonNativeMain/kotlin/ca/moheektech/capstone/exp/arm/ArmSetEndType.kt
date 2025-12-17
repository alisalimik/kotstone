package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@ca.moheektech.capstone.internal.ExportedApi
actual enum class ArmSetEndType(override val value: Int) : INumericEnum {
  INVALID(ARM_SETEND_INVALID), // /< Uninitialized.
  BE(ARM_SETEND_BE), // /< BE operand.
  LE(ARM_SETEND_LE), // /< LE operand
}
