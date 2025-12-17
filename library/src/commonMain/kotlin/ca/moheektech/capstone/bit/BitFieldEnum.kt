package ca.moheektech.capstone.bit

/**
 * Interface for enum classes that want to work seamlessly with BitField.
 * Implementing this interface provides additional convenience methods.
 */
interface BitFieldEnum<T : Enum<T>> {
    /**
     * Converts a raw value back to an enum instance.
     * 
     * @param value The raw bit value
     * @return The corresponding enum instance, or null if not found
     */
    fun fromValue(value: ULong): T?
    
    /**
     * Returns a BitField with all possible flags set.
     * 
     * @return BitField containing all enum values
     */
    fun allFlags(): BitField<T>
}
