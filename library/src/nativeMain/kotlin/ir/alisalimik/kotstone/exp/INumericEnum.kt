package ir.alisalimik.kotstone.exp

internal actual interface INumericEnum {
  val value: UInt

  actual fun toInt(): Int {
    return value.toInt()
  }
}
