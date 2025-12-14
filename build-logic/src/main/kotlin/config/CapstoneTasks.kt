package config

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

/**
 * Task to build Capstone for a specific target
 */
abstract class BuildCapstoneTask : DefaultTask() {
    @get:Input
    var targetName: String = ""

    @TaskAction
    fun buildCapstone() {
        // Pass project explicitly
        buildCapstoneForTarget(project, targetName)
    }
}

/**
 * Task to build Capstone for all native targets
 */
abstract class BuildAllCapstoneTask : DefaultTask() {

    @TaskAction
    fun buildAllCapstone() {
        val kotlin = project.extensions.findByType(KotlinMultiplatformExtension::class.java)
        if (kotlin == null) {
            project.logger.warn("KotlinMultiplatformExtension not found, skipping Capstone build")
            return
        }

        val nativeTargets = kotlin.targets.filterIsInstance<KotlinNativeTarget>()

        // Pass project explicitly
        buildCapstoneForAllTargets(project, nativeTargets)
    }
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

    // Task to build all targets
    tasks.register("buildCapstoneAll", BuildAllCapstoneTask::class.java).configure {
        group = "capstone"
        description = "Build Capstone native library for all enabled targets"
    }

    // Create individual tasks for each native target
    val nativeTargets = kotlin.targets.filterIsInstance<KotlinNativeTarget>()
    for (target in nativeTargets) {
        tasks.register("buildCapstone${target.targetName.capitalize()}", BuildCapstoneTask::class.java).configure {
            group = "capstone"
            description = "Build Capstone native library for ${target.targetName}"
            targetName = target.targetName
        }
    }

    // Create tasks for shared library builds (JVM and Android) and WASM
    val sharedLibraryTargets = listOf(
        // JVM shared libraries
        "macosX64Shared",
        "macosArm64Shared",
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
        tasks.register("buildCapstone${targetName.capitalize()}", BuildCapstoneTask::class.java).configure {
            group = "capstone"
            description = "Build Capstone shared library for $targetName"
            this.targetName = targetName
        }
    }

    // Add dependency so cinterop tasks depend on Capstone build
    afterEvaluate {
        tasks.matching { it.name.contains("cinterop", ignoreCase = true) }.configureEach(object : Action<Task> {
            override fun execute(cinteropTask: Task) {
                val targetName = extractTargetFromCinteropTaskName(cinteropTask.name)
                if (targetName != null) {
                    val capstoneBuildTask = tasks.findByName("buildCapstone${targetName.capitalize()}")
                    if (capstoneBuildTask != null) {
                        cinteropTask.dependsOn(capstoneBuildTask)
                    }
                }
            }
        })
    }
}

private fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

private fun extractTargetFromCinteropTaskName(taskName: String): String? {
    // Task names are typically like "cinteropCapstoneIosArm64"
    val pattern = """cinterop\w+([A-Z][a-zA-Z0-9]+)""".toRegex()
    val match = pattern.find(taskName)
    return match?.groupValues?.get(1)?.let {
        // Convert from PascalCase to camelCase
        it.replaceFirstChar { char -> char.lowercase() }
    }
}
