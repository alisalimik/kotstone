package external

import external.tasks.BuildAllCapstoneTask
import external.tasks.BuildCapstoneTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import platform.toolchains
import java.io.File

internal fun createException(message: String): RuntimeException {
    return RuntimeException(message)
}

internal fun getZigToolchainFile(project: Project): File {
    return getZigToolchainFile(project.layout.buildDirectory.get().asFile)
}

internal fun getZigToolchainFile(buildDirectory: File): File {
    val toolchainFile = File(buildDirectory, "toolchains/zig-toolchain.cmake")
    if (!toolchainFile.exists()) {
        toolchainFile.parentFile.mkdirs()
        toolchainFile.writeText("""
            set(CMAKE_C_COMPILER "zig")
            set(CMAKE_CXX_COMPILER "zig")
            set(CMAKE_C_COMPILER_ARG1 "cc")
            set(CMAKE_CXX_COMPILER_ARG1 "c++")
            set(CMAKE_AR "zig" CACHE STRING "" FORCE)
            set(CMAKE_RANLIB "zig" CACHE STRING "" FORCE)
            
            # Override archive creation commands to use 'zig ar'
            set(CMAKE_C_ARCHIVE_CREATE "<CMAKE_AR> ar qc <TARGET> <OBJECTS>")
            set(CMAKE_C_ARCHIVE_APPEND "<CMAKE_AR> ar q <TARGET> <OBJECTS>")
            set(CMAKE_C_ARCHIVE_FINISH "<CMAKE_AR> ranlib <TARGET>")
            set(CMAKE_CXX_ARCHIVE_CREATE "<CMAKE_AR> ar qc <TARGET> <OBJECTS>")
            set(CMAKE_CXX_ARCHIVE_APPEND "<CMAKE_AR> ar q <TARGET> <OBJECTS>")
            set(CMAKE_CXX_ARCHIVE_FINISH "<CMAKE_AR> ranlib <TARGET>")

            # Fix for Zig + Ninja/Make dependency file issue by suppressing the flags
            set(CMAKE_DEPFILE_FLAGS_C "" CACHE STRING "" FORCE)
            set(CMAKE_DEPFILE_FLAGS_CXX "" CACHE STRING "" FORCE)
            set(CMAKE_C_LINK_DEPENDS_USE_COMPILER FALSE CACHE BOOL "" FORCE)
            set(CMAKE_CXX_LINK_DEPENDS_USE_COMPILER FALSE CACHE BOOL "" FORCE)
            set(CMAKE_LINK_DEPENDS_NO_SHARED ON CACHE BOOL "" FORCE)
        """.trimIndent())
    }
    return toolchainFile
}


/**
 * Registers Capstone build tasks for the project
 */
fun Project.registerCapstoneBuildTasks() {
    val kotlin = extensions.findByType(KotlinMultiplatformExtension::class.java)
    if (kotlin == null) {
        logger.warn("KotlinMultiplatformExtension not found, skipping Capstone task registration")
        return
    }

    val allBuildTaskNames = mutableListOf<String>()
    val androidBuildTaskNames = mutableListOf<String>()

    fun configureBuildCapstoneTask(task: BuildCapstoneTask) {
        task.rootProjectDir.set(rootProject.layout.projectDirectory)
        task.buildDirectory.set(layout.buildDirectory)
        task.hasNinja.set(this@registerCapstoneBuildTasks.toolchains.commandExists("ninja"))
        task.linuxX64OnMac.set(this@registerCapstoneBuildTasks.toolchains.linuxX64OnMac)
        task.nativeLinux.set(this@registerCapstoneBuildTasks.toolchains.nativeLinux)
        task.nativeWindows.set(this@registerCapstoneBuildTasks.toolchains.nativeWindows)
        task.mingwX64.set(this@registerCapstoneBuildTasks.toolchains.mingwX64)
        task.mingwX86.set(this@registerCapstoneBuildTasks.toolchains.mingwX86)
        task.hasMingwX64CrossGCC.set(this@registerCapstoneBuildTasks.toolchains.hasMingwX64CrossGCC)
        task.hasMingwX86CrossGCC.set(this@registerCapstoneBuildTasks.toolchains.hasMingwX86CrossGCC)
        task.hasEmscripten.set(this@registerCapstoneBuildTasks.toolchains.hasEmscripten)
        task.emscriptenToolchainFile.set(this@registerCapstoneBuildTasks.toolchains.findEmscriptenToolchain())
        task.emscriptenRoot.set(this@registerCapstoneBuildTasks.toolchains.getEmscriptenRoot())
        task.llvmNmPath.set(this@registerCapstoneBuildTasks.toolchains.getLlvmNm())
        // Set androidNdkPath with convention (empty string if not found)
        task.androidNdkPath.convention(provider {
            CapstoneBuildConfigs.getAndroidNdkPath(this@registerCapstoneBuildTasks) ?: ""
        })
        task.capstoneSource.set(rootProject.layout.projectDirectory.dir("library/interop/capstone"))
        task.outputDir.set(provider {
            CapstoneBuildConfigs.getOutputDir(this@registerCapstoneBuildTasks, task.targetName)
        }.get())
    }

    val nativeTargets = kotlin.targets.filterIsInstance<KotlinNativeTarget>()
    for (target in nativeTargets) {
        val taskName = "buildCapstone${target.targetName.capitalize()}"
        allBuildTaskNames.add(taskName)
        // Track Android native targets separately
        if (target.targetName.startsWith("androidNative")) {
            androidBuildTaskNames.add(taskName)
        }
        tasks.register(taskName, BuildCapstoneTask::class.java).configure {
            group = "capstone"
            description = "Build Capstone native library for ${target.targetName}"
            targetName = target.targetName
            configureBuildCapstoneTask(this)
        }
    }

    val sharedLibraryTargets = listOf(
        // JVM shared libraries
        "macosX64Shared",
        "macosArm64Shared",
        "linuxX86Shared",
        "linuxX64Shared",
        "linuxArm64Shared",
        "linuxArm32Shared",
        "mingwX64Shared",
        "mingwX86Shared",
        // Android shared libraries
        "androidArm64Shared",
        "androidArm32Shared",
        "androidX64Shared",
        "androidX86Shared",
        // WASM
        "wasmJs",
        "wasmWasi"
    )

    for (targetName in sharedLibraryTargets) {
        val taskName = "buildCapstone${targetName.capitalize()}"
        allBuildTaskNames.add(taskName)
        // Track Android shared library targets separately
        if (targetName.startsWith("android")) {
            androidBuildTaskNames.add(taskName)
        }
        tasks.register(taskName, BuildCapstoneTask::class.java).configure {
            group = "capstone"
            description = "Build Capstone shared library for $targetName"
            this.targetName = targetName
            configureBuildCapstoneTask(this)
        }
    }

    tasks.register("buildCapstoneAll", BuildAllCapstoneTask::class.java).configure {
        group = "capstone"
        description = "Build Capstone native library for all enabled targets"
        // Make it depend on all individual build tasks
        dependsOn(allBuildTaskNames)
    }

    // Alias for capstoneBuildAll (requested by user)
    tasks.register("capstoneBuildAll", BuildAllCapstoneTask::class.java).configure {
        group = "capstone"
        description = "Alias for buildCapstoneAll"
        dependsOn(allBuildTaskNames)
    }

    // Task to build all Android NDK targets
    tasks.register("buildCapstoneAndroid", BuildAllCapstoneTask::class.java).configure {
        group = "capstone"
        description = "Build Capstone native library for all Android NDK targets"
        // Make it depend on all Android build tasks
        dependsOn(androidBuildTaskNames)
    }

    // Make Android preBuild task depend on building all Android Capstone libraries
    // Use configureEach to be lazy and robust against plugin application order
    tasks.configureEach {
        if (name == "preBuild") {
            dependsOn("buildCapstoneAndroid")
            logger.info("Configured preBuild to depend on buildCapstoneAndroid")
        }
    }

    // Make cinterop tasks depend on corresponding Capstone build tasks
    tasks.configureEach {
        if (name.contains("cinterop", ignoreCase = true)) {
            val targetName = extractTargetFromCinteropTaskName(name)
            if (targetName != null) {
                val buildTaskName = "buildCapstone${targetName.capitalize()}"
                dependsOn(buildTaskName)
                logger.info("✓ Configured cinterop: $name to depend on $buildTaskName")
            }
        }
    }

    // Make Kotlin native compilation tasks depend on corresponding Capstone build tasks
    tasks.configureEach {
        if (name.contains("compileWasm")) {
            val targetName = extractTargetFromCompileTaskName(name)
            if (targetName != null) {
                val buildTaskName = "buildCapstone${targetName.capitalize()}"
                dependsOn(buildTaskName)
                logger.info("✓ Configured compile wasm: $name to depend on $buildTaskName")
            }
        }
    }

    // Make jvmTest depend on the appropriate shared library build task for the current host
    tasks.configureEach {
        if (name == "jvmTest") {
            val arch = System.getProperty("os.arch").lowercase()
            val isArm64 = arch.contains("arm64") || arch.contains("aarch64")
            val isArm32 = arch.contains("arm") || arch.startsWith("aarch")
            val isX64 = arch.contains("x86_64") || arch.contains("amd64")
            
            val targetName = when {
                platform.Host.isMac && isArm64 -> "macosArm64Shared"
                platform.Host.isMac -> "macosX64Shared"
                platform.Host.isLinux && isArm64 -> "linuxArm64Shared"
                platform.Host.isLinux && isX64 -> "linuxX64Shared"
                platform.Host.isLinux && isArm32 -> "linuxArm32Shared"
                platform.Host.isLinux -> "linuxX86Shared"
                platform.Host.isWindows && isX64 -> "mingwX64Shared"
                platform.Host.isWindows -> "mingwX86Shared"
                else -> null
            }

            if (targetName != null) {
                val buildTaskName = "buildCapstone${targetName.capitalize()}"
                dependsOn(buildTaskName)
                logger.info("✓ Configured jvmTest to depend on $buildTaskName")
            }
        }

        if (name == "jvmJar") {
            sharedLibraryTargets.take(8).forEach { targetName ->
                val buildTaskName = "buildCapstone${targetName.capitalize()}"
                dependsOn(buildTaskName)
                logger.info("✓ Configured jvmMain to depend on $buildTaskName")
            }

        }
    }
}

private fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

private fun extractTargetFromCinteropTaskName(taskName: String): String? {
    // Task name format: cinteropCapstone<TargetName>
    // We want to extract <TargetName> which starts with an uppercase letter
    val pattern = """cinteropCapstone([A-Z][a-zA-Z0-9]+)""".toRegex()
    val match = pattern.find(taskName)
    return match?.groupValues?.get(1)?.let {
        it.replaceFirstChar { char -> char.lowercase() }
    }
}

private fun extractTargetFromCompileTaskName(taskName: String): String? {
    // Task names like "compileKotlinAndroidNativeArm64", "compileKotlinIosArm64", etc.
    val pattern = """compileKotlin([A-Z][a-zA-Z0-9]+)""".toRegex()
    val match = pattern.find(taskName)
    return match?.groupValues?.get(1)?.let {
        it.replaceFirstChar { char -> char.lowercase() }
    }
}
