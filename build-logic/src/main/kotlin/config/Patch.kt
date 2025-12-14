package config

import org.gradle.api.Project
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

fun Project.patchWASITestRunner() {
    val wasmResourcesDir = file("src/wasmWasiMain/resources")

    tasks.named("wasmWasiNodeTest").configure {
        dependsOn("wasmWasiProcessResources")

        doFirst {
            val outDir = layout.buildDirectory
                .dir("compileSync/wasmWasi/test/testDevelopmentExecutable/kotlin")
                .get().asFile

            if (wasmResourcesDir.exists()) {
                copy {
                    from(wasmResourcesDir)
                    into(outDir)
                }
            }

            val runner = outDir.resolve("${project.name}-capstone-test.mjs")
            if (runner.exists()) {
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