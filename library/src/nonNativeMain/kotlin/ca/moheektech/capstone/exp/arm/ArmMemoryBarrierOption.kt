package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.*

@ExportedApi
actual enum class ArmMemoryBarrierOption(override val value: Int) : INumericEnum {

  RESERVED_0(ARM_MB_RESERVED_0),
  OSHLD(ARM_MB_OSHLD),
  OSHST(ARM_MB_OSHST),
  OSH(ARM_MB_OSH),
  RESERVED_4(ARM_MB_RESERVED_4),
  NSHLD(ARM_MB_NSHLD),
  NSHST(ARM_MB_NSHST),
  NSH(ARM_MB_NSH),
  RESERVED_8(ARM_MB_RESERVED_8),
  ISHLD(ARM_MB_ISHLD),
  ISHST(ARM_MB_ISHST),
  ISH(ARM_MB_ISH),
  RESERVED_12(ARM_MB_RESERVED_12),
  LD(ARM_MB_LD),
  ST(ARM_MB_ST),
  SY(ARM_MB_SY),
}
