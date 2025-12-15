package config

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import external.CapstoneBuildConfigs
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import java.io.File

fun Project.configurePublishing() {
    plugins.withId("com.vanniktech.maven.publish") {

        extensions.configure<MavenPublishBaseExtension> {

            publishToMavenCentral()

            signAllPublications()

            pom {
                name.set("Kapstone")
                description.set("High-level Kotlin Multiplatform wrapper for the Capstone disassembly engine")
                inceptionYear.set("2025")
                url.set("https://github.com/alisalimik/kapstone")

                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("alisalimi")
                        name.set("Ali Salimi")
                        url.set("https://github.com/alisalimik")
                    }
                }

                scm {
                    url.set("https://github.com/alisalimik/kapstone")
                    connection.set("scm:git:https://github.com/alisalimik/kapstone.git")
                    developerConnection.set("scm:git:ssh://git@github.com:alisalimik/kapstone.git")
                }
            }
        }

        // Ensure reproducible artifacts
        tasks.withType<AbstractArchiveTask>().configureEach {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
        }

        // Create platform-specific JVM artifacts with classifiers
        createPlatformSpecificJars()
    }
}

/**
 * Creates platform-specific JAR artifacts for JVM native libraries
 * These are published with Maven classifiers so consumers can download only the libraries they need
 *
 * The classifier format matches CapstoneBuildConfigs.getJvmPlatformClassifier()
 *
 * Directory structure:
 *   Build output: library/src/jvmMain/resources/libs/{classifier}/
 *   JAR content:  libs/{classifier}/
 *   Classifier:   {classifier} (e.g., "capstone-macos-x64")
 */
private fun Project.createPlatformSpecificJars() {
    // Get all JVM shared library targets from CapstoneBuildConfigs
    val jvmSharedTargets = CapstoneBuildConfigs.getAllJvmSharedTargets()

    jvmSharedTargets.forEach { targetName ->
        val classifier = CapstoneBuildConfigs.getJvmPlatformClassifier(targetName)

        if (classifier != null) {
            val taskName = "jvmJar${classifier.replace("-", "").replaceFirstChar { it.uppercase() }}"

            // Create a JAR task for each platform
            val jarTask = tasks.register<Jar>(taskName) {
                archiveClassifier.set(classifier)

                // Include native library from resources
                // Path matches CapstoneBuild.kt output directory
                from(File(projectDir, "library/src/jvmMain/resources/libs/$classifier")) {
                    into("libs/$classifier")
                    include("**/*")
                }

                // Optionally include JVM classes if needed (currently disabled)
                // from(tasks.named("compileKotlinJvm"))
            }

            // Register the JAR with Maven publishing
            afterEvaluate {
                extensions.configure<PublishingExtension> {
                    publications {
                        // Create or update the JVM publication to include platform-specific JARs
                        if (findByName("jvm") is MavenPublication) {
                            (findByName("jvm") as MavenPublication).artifact(jarTask)
                        }
                    }
                }
            }
        }
    }
}
