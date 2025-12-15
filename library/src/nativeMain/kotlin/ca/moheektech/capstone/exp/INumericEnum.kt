package ca.moheektech.capstone.exp

internal actual interface INumericEnum {
  val value: UInt

  actual fun toInt(): Int {
    return value.toInt()
  }
}
