package external.tasks

import external.buildCapstoneForAllTargets
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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

        buildCapstoneForAllTargets(project, nativeTargets)
    }
}