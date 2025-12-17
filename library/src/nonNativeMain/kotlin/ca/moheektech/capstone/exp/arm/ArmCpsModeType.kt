package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@ca.moheektech.capstone.internal.ExportedApi
actual enum class ArmCpsModeType(override val value: Int) : INumericEnum {
  INVALID(ARM_CPSMODE_INVALID),
  IE(ARM_CPSMODE_IE),
  ID(ARM_CPSMODE_ID)
}
