package ir.alisalimik.kotstone.bit

/**
 * DSL for creating BitFields with natural syntax.
 *
 * Usage:
 * ```kotlin
 * val permissions = bitField<FilePermissions> {
 *     +FilePermissions.READ
 *     +FilePermissions.WRITE
 *     if (needsExecute) +FilePermissions.EXECUTE
 * }
 * ```
 */
@DslMarker annotation class BitFieldDsl
