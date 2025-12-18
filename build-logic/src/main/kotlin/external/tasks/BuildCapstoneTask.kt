package external.tasks

import external.BuildContext
import external.buildCapstoneForTarget
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

/**
 * Task to build Capstone for a specific target
 */
abstract class BuildCapstoneTask @Inject constructor(
    private val execOperations: ExecOperations,
    private val fileSystemOperations: FileSystemOperations
) : DefaultTask() {
    @get:Input
    var targetName: String = ""

    @get:Internal
    abstract val rootProjectDir: DirectoryProperty

    @get:Input
    abstract val hasNinja: Property<Boolean>

    @get:Input
    abstract val linuxX64OnMac: Property<Boolean>

    @get:Input
    abstract val nativeLinux: Property<Boolean>

    @get:Input
    abstract val nativeWindows: Property<Boolean>

    @get:Input
    abstract val mingwX64: Property<Boolean>

    @get:Input
    abstract val mingwX86: Property<Boolean>

    @get:Input
    abstract val hasMingwX64CrossGCC: Property<Boolean>

    @get:Input
    abstract val hasMingwX86CrossGCC: Property<Boolean>

    @get:Input
    abstract val hasEmscripten: Property<Boolean>

    @get:Input
    abstract val emscriptenToolchainFile: Property<String>

    @get:Input
    abstract val emscriptenRoot: Property<String>

    @get:Input
    abstract val llvmNmPath: Property<String>

    @get:Input
    abstract val androidNdkPath: Property<String>

    @get:Internal
    abstract val buildDirectory: DirectoryProperty

    @get:InputDirectory
    abstract val capstoneSource: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun buildCapstone() {
        val buildContext = BuildContext(
            rootProjectDir = rootProjectDir.get().asFile,
            buildDirectory = buildDirectory.get().asFile,
            hasNinja = hasNinja.get(),
            linuxX64OnMac = linuxX64OnMac.get(),
            nativeLinux = nativeLinux.get(),
            nativeWindows = nativeWindows.get(),
            mingwX64 = mingwX64.get(),
            mingwX86 = mingwX86.get(),
            hasMingwX64CrossGCC = hasMingwX64CrossGCC.get(),
            hasMingwX86CrossGCC = hasMingwX86CrossGCC.get(),
            hasEmscripten = hasEmscripten.get(),
            emscriptenToolchainFile = emscriptenToolchainFile.get(),
            emscriptenRoot = emscriptenRoot.orNull?.takeIf { it.isNotEmpty() }?.let { java.io.File(it) },
            llvmNmPath = llvmNmPath.get(),
            androidNdkPath = androidNdkPath.orNull?.takeIf { it.isNotEmpty() },
            logger = logger,
            execOperations = execOperations,
            fileSystemOperations = fileSystemOperations
        )
        buildCapstoneForTarget(buildContext, targetName)
    }
}
