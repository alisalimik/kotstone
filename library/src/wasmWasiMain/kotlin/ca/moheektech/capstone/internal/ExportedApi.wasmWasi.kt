package ca.moheektech.capstone.internal

@Target(
    allowedTargets =
        [
            AnnotationTarget.CLASS,
            AnnotationTarget.PROPERTY,
            AnnotationTarget.FUNCTION,
            AnnotationTarget.FILE])
@Retention(value = AnnotationRetention.BINARY)
actual annotation class ExportedApi
