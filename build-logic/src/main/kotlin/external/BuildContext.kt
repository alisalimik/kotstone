package external

import org.gradle.api.file.FileSystemOperations
import org.gradle.api.logging.Logger
import org.gradle.process.ExecOperations
import java.io.File

/**
 * Context object containing all necessary information for building Capstone.
 * This allows tasks to be configuration cache compatible by capturing
 * all needed data at configuration time and using injected services.
 */
data class BuildContext(
    val rootProjectDir: File,
    val buildDirectory: File,
    val hasNinja: Boolean,
    val linuxX64OnMac: Boolean,
    val nativeLinux: Boolean,
    val mingwX64: Boolean,
    val mingwX86: Boolean,
    val hasEmscripten: Boolean,
    val emscriptenToolchainFile: String,
    val llvmNmPath: String,
    val androidNdkPath: String?,
    val logger: Logger,
    val execOperations: ExecOperations,
    val fileSystemOperations: FileSystemOperations
)
