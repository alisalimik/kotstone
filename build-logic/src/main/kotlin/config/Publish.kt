package config

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import external.CapstoneBuildConfigs
import org.gradle.api.Project
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.attributes.Usage
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.nativeplatform.MachineArchitecture
import org.gradle.nativeplatform.OperatingSystemFamily
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinSoftwareComponentWithCoordinatesAndPublication
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.npm.PackageJson
import java.io.File
import java.io.FileInputStream
import java.util.Properties

val publishProperties by lazy {
    loadProperties(File("publish.properties"))
}

fun getNamespace(): String {
    return publishProperties["GROUP"].toString()
}

fun getModuleName(): String {
    return publishProperties["POM_NAME"].toString().lowercase()
}

fun Project.configureProjectMeta() {
    group = publishProperties["GROUP"].toString()
    version = publishProperties["VERSION"].toString()
}

fun Project.configurePublishing() {
    plugins.withId("com.vanniktech.maven.publish") {

        extensions.configure<MavenPublishBaseExtension> {

            publishToMavenCentral()

            signAllPublications()

            pom {
                name.set(publishProperties["POM_NAME"].toString())
                description.set(publishProperties["POM_DESCRIPTION"].toString())
                inceptionYear.set("2025")
                url.set(publishProperties["POM_URL"].toString())
                licenses {
                    license {
                        name.set(publishProperties["POM_LICENSE_NAME"].toString())
                        url.set(publishProperties["POM_LICENSE_URL"].toString())
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set(publishProperties["POM_DEVELOPER_ID"].toString())
                        name.set(publishProperties["POM_DEVELOPER_NAME"].toString())
                        url.set(publishProperties["POM_DEVELOPER_URL"].toString())
                        email.set(publishProperties["POM_DEVELOPER_EMAIL"].toString())
                    }
                }

                scm {
                    url.set(publishProperties["POM_SCM_URL"].toString())
                    connection.set(publishProperties["POM_SCM_CONNECTION"].toString())
                    developerConnection.set(publishProperties["POM_SCM_DEV_CONNECTION"].toString())
                }
            }
        }

        // Ensure reproducible artifacts
        tasks.withType<AbstractArchiveTask>().configureEach {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
        }

        // Configure npm publishing tasks
        configureNpmPublishing()
    }
}

///**
// * Creates platform-specific JAR artifacts for JVM native libraries
// * These are published as variants so Gradle consumers can automatically pick the right one.
// */
//context(project: Project)
//internal fun KotlinMultiplatformExtension.createPlatformSpecificJars() {
//    // Get all JVM shared library targets from CapstoneBuildConfigs
//    val jvmSharedTargets = CapstoneBuildConfigs.getAllJvmSharedTargets()
//
//    // Exclude native libraries from the main JVM JAR
//    project.tasks.named<Jar>("jvmJar") {
//        exclude("libs/**")
//    }
//
//    publishing.adhocSoftwareComponent {
//        jvmSharedTargets.forEach { targetName ->
//            val classifier =
//                CapstoneBuildConfigs.getJvmPlatformClassifier(targetName) ?: return@forEach
//
//            // Define attributes for this target
//            val (osFamily, arch) = getOsAndArchAttributes(targetName)
//
//            val taskName =
//                "jvmJar${classifier.replace("-", "").replaceFirstChar { it.uppercase() }}"
//            val configName =
//                "jvmVariant${classifier.replace("-", "").replaceFirstChar { it.uppercase() }}"
//
//            // 1. Create a JAR task for this platform
//            val jarTask = project.tasks.register<Jar>(taskName) {
//                archiveClassifier.set(classifier)
//                // Include native library from resources
//                from(File(project.projectDir, "src/jvmMain/resources/libs/$classifier")) {
//                    into("libs/$classifier")
//                    include("**/*")
//                }
//            }
//
//            // 2. Create an outgoing configuration for this variant
//            val variantConfig = project.configurations.create(configName) {
//                isCanBeResolved = true
//                isCanBeConsumed = true
//
//
//                // Set attributes so Gradle knows when to use this variant
//                attributes {
//                    attribute(
//                        OperatingSystemFamily.OPERATING_SYSTEM_ATTRIBUTE, project.objects.named(
//                            OperatingSystemFamily::class.java, osFamily
//                        )
//                    )
//                    attribute(
//                        MachineArchitecture.ARCHITECTURE_ATTRIBUTE, project.objects.named(
//                            MachineArchitecture::class.java, arch
//                        )
//                    )
//                    // Standard JVM attributes to match the consumer's request
//                    attribute(
//                        Usage.USAGE_ATTRIBUTE,
//                        project.objects.named(Usage::class.java, Usage.JAVA_RUNTIME)
//                    )
//                    attribute(
//                        LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
//                        project.objects.named(LibraryElements::class.java, LibraryElements.JAR)
//                    )
//                    attribute(
//                        Bundling.BUNDLING_ATTRIBUTE,
//                        project.objects.named(Bundling::class.java, Bundling.EXTERNAL)
//                    )
//
//                }
//
//                // Add the JAR artifact
//                outgoing.artifact(jarTask)
//            }
//
//            // 3. Register this configuration as a variant of the 'java' component
//            addVariantsFromConfiguration(variantConfig) {
//                mapToMavenScope("runtime")
//            }
//            // 4. Also publish as a classifier (legacy Maven support) using the old method
//            // This ensures Maven users can still manually pick the classifier
//            project.afterEvaluate {
//                extensions.configure<PublishingExtension> {
//                    publications {
//                        if (findByName("jvm") is MavenPublication) {
//                            (findByName("jvm") as MavenPublication).artifact(jarTask)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Suppress("UnstableApiUsage")
//private fun getOsAndArchAttributes(targetName: String): Pair<String, String> {
//    fun getArch(): String {
//        return when {
//            targetName.contains("Arm64") -> MachineArchitecture.ARM64
//            targetName.contains("x86") -> MachineArchitecture.X86
//            targetName.contains("x64") -> MachineArchitecture.X86_64
//            else -> "arm"
//        }
//    }
//    return when {
//        targetName.contains("macos", ignoreCase = true) -> {
//            OperatingSystemFamily.MACOS
//        }
//
//        targetName.contains("linux", ignoreCase = true)  -> {
//            OperatingSystemFamily.LINUX
//        }
//
//        targetName.contains("mingw", ignoreCase = true) ->  {
//            OperatingSystemFamily.WINDOWS
//        }
//        else -> "unknown"
//    } to getArch()
//}

/**
 * Configures npm publishing tasks for publishing to npm registry and GitHub Packages
 */
private fun Project.configureNpmPublishing() {
    val jsDistDir = File(layout.buildDirectory.get().asFile, "dist/js/productionLibrary")

    // Task to publish to npm registry
    tasks.register("publishToNpm") {
        group = "publishing"
        description = "Publish Kotlin/JS library to npm registry"

        dependsOn("jsProductionLibraryCompileSync")

        doLast {
            val npmToken = System.getenv("NPM_TOKEN")
            if (npmToken.isNullOrEmpty()) {
                throw IllegalStateException("NPM_TOKEN environment variable is not set. Required for publishing to npm.")
            }

            if (!jsDistDir.exists()) {
                throw IllegalStateException("JS distribution directory does not exist: ${jsDistDir.absolutePath}")
            }

            // Create .npmrc file with authentication
            val npmrcFile = File(jsDistDir, ".npmrc")
            npmrcFile.writeText("//registry.npmjs.org/:_authToken=\${NPM_TOKEN}\n")

            // Publish to npm
            providers.exec {
                workingDir = jsDistDir
                environment("NPM_TOKEN", npmToken)
                commandLine("npm", "publish", "--access", "public")
            }

            // Clean up .npmrc
            npmrcFile.delete()
        }
    }

    // Task to publish to GitHub Packages
    tasks.register("publishToGitHubPackages") {
        group = "publishing"
        description = "Publish Kotlin/JS library to GitHub Packages npm registry"

        dependsOn("jsProductionLibraryCompileSync")

        doLast {
            val githubToken = System.getenv("GITHUB_TOKEN")
            if (githubToken.isNullOrEmpty()) {
                throw IllegalStateException("GITHUB_TOKEN environment variable is not set. Required for publishing to GitHub Packages.")
            }

            if (!jsDistDir.exists()) {
                throw IllegalStateException("JS distribution directory does not exist: ${jsDistDir.absolutePath}")
            }

            // Create .npmrc file with GitHub Packages authentication
            val npmrcFile = File(jsDistDir, ".npmrc")
            npmrcFile.writeText(
                $$"""
                @kapstone:registry=https://npm.pkg.github.com
                //npm.pkg.github.com/:_authToken=${GITHUB_TOKEN}
            """.trimIndent())

            // Publish to GitHub Packages
            providers.exec {
                workingDir = jsDistDir
                environment("GITHUB_TOKEN", githubToken)
                commandLine("npm", "publish", "--registry", "https://npm.pkg.github.com")
            }

            // Clean up .npmrc
            npmrcFile.delete()
        }
    }

    // Task to publish to both registries
    tasks.register("publishJsToAllRegistries") {
        group = "publishing"
        description = "Publish Kotlin/JS library to both npm and GitHub Packages registries"

        dependsOn("publishToNpm", "publishToGitHubPackages")
    }
}

private fun loadProperties(file: File): Properties {
    return Properties().apply {
        FileInputStream(file).use { load(it) }
    }
}

fun KotlinJsTargetDsl.configurePublishing() {
    compilations["main"].packageJson {
        name = "@${publishProperties["POM_DEVELOPER_ID"] ?: "alisalimik"}/${getModuleName()}"
        main = "${getModuleName()}.mjs"
        types = "${getModuleName()}.d.mts"
        private = false
        version = publishProperties["VERSION"]?.toString() ?: "1.0.0-alpha01"
        customField("keywords", listOf("kapstone", "capstone", "disassembler", "decompiler"))
        customField("license", publishProperties["POM_LICENSE_NAME"])
        customField("description", publishProperties["POM_DESCRIPTION"])
        customField("repository", mapOf(
            "type" to "git",
            "url" to "git+" + publishProperties["POM_URL"]
        ))
        customField("author", mapOf(
            "name" to publishProperties["POM_DEVELOPER_NAME"],
            "email" to publishProperties["POM_DEVELOPER_EMAIL"],
            "url" to publishProperties["POM_DEVELOPER_URL"]
        ))
    }
}

fun Framework.configurePublishing() {
    val pubVersion = publishProperties["VERSION"].toString()
    binaryOption("bundleId", publishProperties["GROUP"].toString())
    binaryOption("bundleVersion", generateVersionCode(pubVersion).toString())
    binaryOption("bundleShortVersionString", pubVersion)
}

private fun generateVersionCode(versionName: String): Int {
    val regex = Regex("""^(\d+)\.(\d+)\.(\d+)(?:-(alpha|beta|rc)?(\d+)?)?$""")
    val match = regex.matchEntire(versionName)
        ?: throw IllegalArgumentException("Invalid version format: $versionName")

    val major = match.groupValues[1].toInt()
    val minor = match.groupValues[2].toInt()
    val patch = match.groupValues[3].toInt()
    val type = match.groupValues[4]
    val suffixNum = match.groupValues[5].toIntOrNull() ?: 0

    val base = major * 10_000 + minor * 100 + patch

    val statusScore = when (type) {
        "alpha" -> 1
        "beta"  -> 2
        "rc"    -> 3
        ""      -> 9
        else    -> 0
    }

    return base * 100 + statusScore * 10 + suffixNum
}