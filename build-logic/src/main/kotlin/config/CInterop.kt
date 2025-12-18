package config

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

@Suppress("UnstableApiUsage")
context(project: Project)
fun KotlinMultiplatformExtension.createCInterop() {
    val projectDir = project.isolated.projectDirectory
    val nativeTargets = this@createCInterop.targets.filterIsInstance<KotlinNativeTarget>()

    for (target in nativeTargets) {
        target.compilations.getByName("main") {
            cinterops.create("capstone") {
                definitionFile.set(projectDir.file("interop/capstone.def"))
                includeDirs(projectDir.file("interop/capstone/include").asFile)
                extraOpts("-libraryPath", projectDir.file("interop/lib/${target.targetName}").asFile.absolutePath)
            }
        }
    }
}