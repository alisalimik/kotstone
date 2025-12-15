import config.configureArgs
import config.configureCompatibility
import config.configureDetekt
import config.configurePublishing
import config.configureSourceSets
import config.configureTargets
import config.createCInterop
import config.patchCinteropConfigCache
import config.patchTestTask
import config.patchWASITestRunner
import external.registerCapstoneBuildTasks
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import kotlinx.validation.ApiValidationExtension
import kotlinx.validation.BinaryCompatibilityValidatorPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KapstoneProjectPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        plugins.apply("org.jetbrains.kotlin.multiplatform")
        plugins.apply("com.android.library")
        plugins.apply("org.jetbrains.dokka")
        plugins.apply("com.ncorti.ktfmt.gradle")
        plugins.apply("io.gitlab.arturbosch.detekt")
        plugins.apply("com.vanniktech.maven.publish")
        plugins.apply("org.jetbrains.kotlinx.binary-compatibility-validator")

        plugins.withId("com.android.library") {
            extensions.configure(KotlinMultiplatformExtension::class.java) {
                configureArgs()
                configureTargets()
                createCInterop()
                configureSourceSets()
            }

            extensions.configure(DetektExtension::class.java) {
                configureDetekt()
            }

            extensions.configure(ApiValidationExtension::class.java) {
                configureCompatibility()
            }

            patchCinteropConfigCache()
            patchTestTask()
            patchWASITestRunner()
            configurePublishing()

            registerCapstoneBuildTasks()
        }
    }
}