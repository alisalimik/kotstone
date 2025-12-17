package ca.moheektech.capstone.bit

/**
 * Builder class for constructing BitFields fluently.
 */
class BitFieldBuilder<T : Enum<T>> {
    private var bitField = BitField<T>()
    
    fun set(flag: T): BitFieldBuilder<T> = apply { bitField = bitField.setFlag(flag) }
    fun set(vararg flags: T): BitFieldBuilder<T> = apply { 
        flags.forEach { bitField = bitField.setFlag(it) }
    }
    fun clear(flag: T): BitFieldBuilder<T> = apply { bitField = bitField.clearFlag(flag) }
    fun toggle(flag: T): BitFieldBuilder<T> = apply { bitField = bitField.toggleFlag(flag) }
    fun combine(other: BitField<T>): BitFieldBuilder<T> = apply { bitField = bitField.getCombined(other) }
    
    fun build(): BitField<T> = bitField
}