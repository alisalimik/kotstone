package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.arm_mem_bo_opt as opt

actual enum class ArmMemoryBarrierOption(override val value: UInt) : INumericEnum {

  RESERVED_0(opt.ARM_MB_RESERVED_0.value),
  OSHLD(opt.ARM_MB_OSHLD.value),
  OSHST(opt.ARM_MB_OSHST.value),
  OSH(opt.ARM_MB_OSH.value),
  RESERVED_4(opt.ARM_MB_RESERVED_4.value),
  NSHLD(opt.ARM_MB_NSHLD.value),
  NSHST(opt.ARM_MB_NSHST.value),
  NSH(opt.ARM_MB_NSH.value),
  RESERVED_8(opt.ARM_MB_RESERVED_8.value),
  ISHLD(opt.ARM_MB_ISHLD.value),
  ISHST(opt.ARM_MB_ISHST.value),
  ISH(opt.ARM_MB_ISH.value),
  RESERVED_12(opt.ARM_MB_RESERVED_12.value),
  LD(opt.ARM_MB_LD.value),
  ST(opt.ARM_MB_ST.value),
  SY(opt.ARM_MB_SY.value),
}
