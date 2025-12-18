package ir.alisalimik.kotstone.exp.aarch64

import ir.alisalimik.kotstone.exp.INumericEnum

expect enum class AArch64VectorLayout : INumericEnum {
  INVALID,
  VL_B,
  VL_H,
  VL_S,
  VL_D,
  VL_Q,
  VL_4B,
  VL_2H,
  VL_1S,
  VL_8B,
  VL_4H,
  VL_2S,
  VL_1D,
  VL_16B,
  VL_8H,
  VL_4S,
  VL_2D,
  VL_1Q,
  VL_64B,
  VL_32H,
  VL_16S,
  VL_8D,
  VL_COMPLETE,
}
