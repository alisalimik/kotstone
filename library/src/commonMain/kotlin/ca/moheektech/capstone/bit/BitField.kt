package ca.moheektech.capstone.bit

import kotlin.jvm.JvmInline

/**
 * A type-safe bit field implementation for efficient flag operations.
 *
 * This class provides a zero-cost abstraction over integer bit operations while maintaining type
 * safety through Kotlin's inline value classes. It's designed to work with enum classes that
 * represent bit flags, ensuring compile-time safety and runtime efficiency.
 *
 * **Key Features:**
 * - Zero runtime overhead through inline value classes
 * - Type-safe flag operations with enum constraints
 * - Immutable by default with functional operations
 * - Comprehensive operator overloading for natural syntax
 * - Full interoperability with integer types when needed
 *
 * **Usage Examples:**
 *
 * ```kotlin
 * enum class FilePermissions(val bit: Int) {
 *     READ(1 shl 0),
 *     WRITE(1 shl 1),
 *     EXECUTE(1 shl 2);
 *
 *     companion object : BitFieldEnum<FilePermissions> {
 *         override fun fromValue(value: ULong): FilePermissions? {
 *             return entries.find { it.bit.toULong() == value }
 *         }
 *         override fun BitFieldEnum.Companion.allFlags(): BitField<FilePermissions> {
 *             return BitField(entries.fold(0UL) { acc, perm -> acc or perm.bit.toULong() })
 *         }
 *     }
 * }
 *
 * // Create and manipulate bit fields
 * val permissions = BitField<FilePermissions>()
 *     .setFlag(FilePermissions.READ)
 *     .setFlag(FilePermissions.WRITE)
 *
 * if (permissions.hasFlag(FilePermissions.READ)) {
 *     println("Has read permission")
 * }
 *
 * val combined = permissions or BitField(FilePermissions.EXECUTE)
 * val readOnly = permissions and BitField(FilePermissions.READ)
 * ```
 *
 * @param T The enum type representing individual flags
 */
@JvmInline
value class BitField<T>(internal val value: ULong = 0UL) where T : Enum<T> {

  companion object {
    /** Empty bit field with no flags set */
    fun <T> empty(): BitField<T> where T : Enum<T> = BitField(0UL)

    /** Creates a BitField from a raw ULong value */
    fun <T> fromValue(value: ULong): BitField<T> where T : Enum<T> = BitField(value)

    /** Creates a BitField from multiple flags */
    fun <T> of(vararg flags: T): BitField<T> where T : Enum<T> {
      return flags.fold(empty<T>()) { acc, flag -> acc.setFlag(flag) }
    }

    /** Creates a BitField from a collection of flags */
    fun <T> fromFlags(flags: Collection<T>): BitField<T> where T : Enum<T> {
      return flags.fold(empty<T>()) { acc, flag -> acc.setFlag(flag) }
    }
  }

  // --- Core Flag Operations ---

  /**
   * Sets the specified flag in this bit field. Returns a new BitField with the flag set.
   *
   * @param flag The flag to set
   * @return New BitField with the flag set
   */
  fun setFlag(flag: T): BitField<T> {
    val flagValue = if (flag is IFlagValue) flag.flag else (1UL shl (flag as Enum<*>).ordinal)
    return BitField(value or flagValue)
  }

  /**
   * Sets the specified flag from another BitField.
   *
   * @param other BitField containing the flag(s) to set
   * @return New BitField with the flag(s) set
   */
  fun setFlag(other: BitField<T>): BitField<T> {
    return BitField(value or other.value)
  }

  /**
   * Checks if the specified flag is set in this bit field.
   *
   * @param flag The flag to check
   * @return true if the flag is set, false otherwise
   */
  fun hasFlag(flag: T): Boolean {
    val flagValue = if (flag is IFlagValue) flag.flag else (1UL shl (flag as Enum<*>).ordinal)
    return (value and flagValue) != 0UL
  }

  /**
   * Checks if any of the flags in the other BitField are set.
   *
   * @param other BitField containing flag(s) to check
   * @return true if any flag is set, false otherwise
   */
  fun hasAnyFlag(other: BitField<T>): Boolean {
    return (value and other.value) != 0UL
  }

  /**
   * Checks if all flags in the other BitField are set.
   *
   * @param other BitField containing flag(s) to check
   * @return true if all flags are set, false otherwise
   */
  fun hasAllFlags(other: BitField<T>): Boolean {
    return (value and other.value) == other.value
  }

  /**
   * Clears the specified flag from this bit field. Returns a new BitField with the flag cleared.
   *
   * @param flag The flag to clear
   * @return New BitField with the flag cleared
   */
  fun clearFlag(flag: T): BitField<T> {
    val flagValue = if (flag is IFlagValue) flag.flag else (1UL shl (flag as Enum<*>).ordinal)
    return BitField(value and flagValue.inv())
  }

  /**
   * Clears the specified flags from another BitField.
   *
   * @param other BitField containing the flag(s) to clear
   * @return New BitField with the flag(s) cleared
   */
  fun clearFlag(other: BitField<T>): BitField<T> {
    return BitField(value and other.value.inv())
  }

  /**
   * Toggles the specified flag in this bit field.
   *
   * @param flag The flag to toggle
   * @return New BitField with the flag toggled
   */
  fun toggleFlag(flag: T): BitField<T> {
    val flagValue = if (flag is IFlagValue) flag.flag else (1UL shl (flag as Enum<*>).ordinal)
    return BitField(value xor flagValue)
  }

  /**
   * Clears all flags, returning an empty bit field.
   *
   * @return Empty BitField
   */
  fun clear(): BitField<T> = BitField(0UL)

  /**
   * Checks if this bit field is empty (no flags set).
   *
   * @return true if no flags are set, false otherwise
   */
  fun isEmpty(): Boolean = value == 0UL

  /**
   * Checks if this bit field is not empty (has flags set).
   *
   * @return true if any flags are set, false otherwise
   */
  fun isNotEmpty(): Boolean = value != 0UL

  // --- Combination Operations ---

  /**
   * Returns the union of this bit field and another (logical OR).
   *
   * @param other The other bit field to combine with
   * @return BitField containing all flags from both bit fields
   */
  fun getCombined(other: BitField<T>): BitField<T> {
    return BitField(value or other.value)
  }

  /**
   * Returns the intersection of this bit field and another (logical AND).
   *
   * @param other The other bit field to intersect with
   * @return BitField containing only flags present in both bit fields
   */
  fun getShared(other: BitField<T>): BitField<T> {
    return BitField(value and other.value)
  }

  /**
   * Returns the symmetric difference of this bit field and another (logical XOR).
   *
   * @param other The other bit field to compare with
   * @return BitField containing flags present in either bit field but not both
   */
  fun getDifferent(other: BitField<T>): BitField<T> {
    return BitField(value xor other.value)
  }

  /**
   * Returns the complement of this bit field (logical NOT). Note: This inverts all bits, which may
   * include more than just the enum values.
   *
   * @return BitField with all bits inverted
   */
  fun getInverted(): BitField<T> {
    return BitField(value.inv())
  }

  // --- Operator Overloads ---

  /** Logical OR operator - combines two bit fields */
  operator fun plus(other: BitField<T>): BitField<T> = getCombined(other)

  operator fun plus(flag: T): BitField<T> = setFlag(flag)

  /** Logical AND operator - intersects two bit fields */
  operator fun times(other: BitField<T>): BitField<T> = getShared(other)

  /** Subtraction operator - removes flags from this bit field */
  operator fun minus(other: BitField<T>): BitField<T> = clearFlag(other)

  operator fun minus(flag: T): BitField<T> = clearFlag(flag)

  /** Contains operator - checks if flag is set */
  operator fun contains(flag: T): Boolean = hasFlag(flag)

  operator fun contains(other: BitField<T>): Boolean = hasAllFlags(other)

  // --- Bitwise Operators ---

  /** Bitwise OR */
  infix fun or(other: BitField<T>): BitField<T> = getCombined(other)

  infix fun or(flag: T): BitField<T> = setFlag(flag)

  /** Bitwise AND */
  infix fun and(other: BitField<T>): BitField<T> = getShared(other)

  infix fun and(flag: T): BitField<T> =
      if (hasFlag(flag)) BitField((flag as Enum<*>).ordinal.let { 1UL shl it }) else empty()

  /** Bitwise XOR */
  infix fun xor(other: BitField<T>): BitField<T> = getDifferent(other)

  infix fun xor(flag: T): BitField<T> = toggleFlag(flag)

  /** Bitwise NOT */
  operator fun not(): BitField<T> = getInverted()

  // --- Utility Operations ---

  /**
   * Returns the number of flags set in this bit field.
   *
   * @return Count of set flags
   */
  fun countSetFlags(): Int = value.countOneBits()

  /**
   * Returns the position of the lowest set bit (0-based).
   *
   * @return Position of lowest set bit, or -1 if empty
   */
  fun lowestSetBit(): Int {
    return if (isEmpty()) -1 else value.countTrailingZeroBits()
  }

  /**
   * Returns the position of the highest set bit (0-based).
   *
   * @return Position of highest set bit, or -1 if empty
   */
  fun highestSetBit(): Int {
    return if (isEmpty()) -1 else (63 - value.countLeadingZeroBits())
  }

  /**
   * Iterates over all set flags in this bit field.
   *
   * @param enumValues Array of all enum values (typically from enumValues<T>())
   * @param action Function to execute for each set flag
   */
  inline fun forEachSetFlag(enumValues: Array<T>, action: (T) -> Unit) {
    enumValues.forEach { flag ->
      if (hasFlag(flag)) {
        action(flag)
      }
    }
  }

  /**
   * Returns a list of all flags that are set in this bit field.
   *
   * @param enumValues Array of all enum values (typically from enumValues<T>())
   * @return List of set flags
   */
  fun getSetFlags(enumValues: Array<T>): List<T> {
    return enumValues.filter { hasFlag(it) }
  }

  /**
   * Creates a sequence of all set flags.
   *
   * @param enumValues Array of all enum values
   * @return Sequence of set flags
   */
  fun setFlagsSequence(enumValues: Array<T>): Sequence<T> {
    return enumValues.asSequence().filter { hasFlag(it) }
  }

  // --- Type Conversions ---

  /**
   * Converts this BitField to its underlying ULong value.
   *
   * @return The raw bit field value
   */
  fun toULong(): ULong = value

  /**
   * Converts this BitField to Long.
   *
   * @return The bit field value as Long
   */
  fun toLong(): Long = value.toLong()

  /**
   * Converts this BitField to Int. Note: This may truncate the value if it doesn't fit in 32 bits.
   *
   * @return The bit field value as Int
   */
  fun toInt(): Int = value.toInt()

  /**
   * Converts this BitField to UInt. Note: This may truncate the value if it doesn't fit in 32 bits.
   *
   * @return The bit field value as UInt
   */
  fun toUInt(): UInt = value.toUInt()

  // --- String Representation ---

  /**
   * Returns a string representation showing the binary value.
   *
   * @return Binary string representation
   */
  fun toBinaryString(): String = value.toString(2)

  /**
   * Returns a hexadecimal string representation.
   *
   * @return Hexadecimal string representation
   */
  fun toHexString(): String = "0x${value.toString(16).uppercase()}"

  /**
   * Returns a detailed string representation with set flags.
   *
   * @param enumValues Array of all enum values for flag name lookup
   * @return Detailed string showing set flags
   */
  fun toDetailedString(enumValues: Array<T>): String {
    if (isEmpty()) return "BitField(empty)"

    val setFlags = getSetFlags(enumValues)
    return "BitField(${setFlags.joinToString(", ")} | ${toHexString()})"
  }

  override fun toString(): String = toHexString()

  /**
   * Compares this BitField with another based on their underlying values.
   *
   * @param other The other BitField to compare with
   * @return Comparison result
   */
  fun compareTo(other: BitField<T>): Int = value.compareTo(other.value)
}
