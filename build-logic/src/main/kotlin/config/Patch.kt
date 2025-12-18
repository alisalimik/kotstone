package config

import org.gradle.api.Project
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

fun Project.patchWASITestRunner() {
    val wasmResourcesDir = file("src/wasmWasiMain/resources")
    val buildDirectory = layout.buildDirectory
    val projectName = name

    tasks.named("wasmWasiNodeTest").configure {
        dependsOn("wasmWasiProcessResources")

        doFirst {
            val outDir = buildDirectory
                .dir("compileSync/wasmWasi/test/testDevelopmentExecutable/kotlin")
                .get().asFile

            if (wasmResourcesDir.exists()) {
                wasmResourcesDir.walkTopDown().forEach { source ->
                    val relative = source.relativeTo(wasmResourcesDir)
                    val target = outDir.resolve(relative)
                    if (source.isDirectory) {
                        target.mkdirs()
                    } else {
                        source.copyTo(target, overwrite = true)
                    }
                }
            }

            val runner = outDir.listFiles()?.firstOrNull { it.name.endsWith("-test.mjs") }
            if (runner != null && runner.exists()) {
                var text = runner.readText()
                if (!text.contains("capstone-bridge.mjs")) {
                    text = "import * as CapstoneBridge from './capstone-bridge.mjs';\n$text"
                    text = text.replace(
                        "wasi.getImportObject()",
                        "Object.assign(wasi.getImportObject(), { './capstone-bridge.mjs': CapstoneBridge })"
                    )
                    runner.writeText(text)
                }
            }
        }
    }
}


fun Project.patchCinteropConfigCache() {
    tasks.withType<KotlinNativeCompile>()
        .named { it.endsWith("MainKotlinMetadata") }
        .configureEach { notCompatibleWithConfigurationCache("Workaround for KT-76147") }

    tasks.matching { it.name.contains("commonizeCInterop") }
        .configureEach { notCompatibleWithConfigurationCache("Workaround for configuration cache issue in Dokka/Commonizer") }
}

fun Project.patchTestTask() {
    tasks.withType<AbstractTestTask> {
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}

fun Project.patchEs2015() {
    tasks.withType<KotlinJsCompile>().configureEach {
        compilerOptions {
            target.set("es2015")
        }
    }
}