package ca.moheektech.capstone.bit

/** Creates a BitField using builder pattern. */
inline fun <T : Enum<T>> buildBitField(builder: BitFieldBuilder<T>.() -> Unit): BitField<T> {
  return BitFieldBuilder<T>().apply(builder).build()
}

/** Creates a BitField using DSL syntax. */
internal inline fun <T : Enum<T>> bitField(builder: BitFieldScope<T>.() -> Unit): BitField<T> {
  return BitFieldScope<T>().apply(builder).build()
}

/** Creates a BitField from this enum value. */
fun <T : Enum<T>> T.toBitField(): BitField<T> = BitField<T>().setFlag(this)

/** Creates a BitField from a collection of enum values. */
fun <T : Enum<T>> Collection<T>.toBitField(): BitField<T> = BitField.fromFlags(this)

/** Creates a BitField from an array of enum values. */
fun <T : Enum<T>> Array<T>.toBitField(): BitField<T> = BitField.fromFlags(this.toList())

/** Applies a transformation to a BitField if it's not empty. */
inline fun <T : Enum<T>, R> BitField<T>.ifNotEmpty(transform: (BitField<T>) -> R): R? {
  return if (isNotEmpty()) transform(this) else null
}

/** Applies an action to each set flag. */
inline fun <T : Enum<T>> BitField<T>.forEachFlag(enumValues: Array<T>, action: (T) -> Unit) {
  forEachSetFlag(enumValues, action)
}

/** Maps the set flags to a list of results. */
inline fun <T : Enum<T>, R> BitField<T>.mapFlags(
    enumValues: Array<T>,
    transform: (T) -> R
): List<R> {
  return getSetFlags(enumValues).map(transform)
}

/** Filters the set flags based on a predicate. */
inline fun <T : Enum<T>> BitField<T>.filterFlags(
    enumValues: Array<T>,
    predicate: (T) -> Boolean
): List<T> {
  return getSetFlags(enumValues).filter(predicate)
}

/** Checks if any set flag matches the predicate. */
inline fun <T : Enum<T>> BitField<T>.anyFlag(
    enumValues: Array<T>,
    predicate: (T) -> Boolean
): Boolean {
  return getSetFlags(enumValues).any(predicate)
}

/** Checks if all set flags match the predicate. */
inline fun <T : Enum<T>> BitField<T>.allFlags(
    enumValues: Array<T>,
    predicate: (T) -> Boolean
): Boolean {
  return getSetFlags(enumValues).all(predicate)
}
