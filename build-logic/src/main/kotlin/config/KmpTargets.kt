@file:Suppress("UnstableApiUsage")

package config

import com.android.build.api.dsl.MinSdkVersion
import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import platform.Host
import platform.toolchains
import kotlin.text.get

context(project: Project)
fun KotlinMultiplatformExtension.configureTargets() {
    jvm()

    androidLibrary {
        namespace = "ca.moheektech.kapstone"
        compileSdk = 36
        minSdk = 21

        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
        }

    }

    js(IR) {
        outputModuleName.set("capstone-kt")
        nodejs()
        browser()
        compilerOptions {
            freeCompilerArgs.add("-Xenable-suspend-function-exporting")
            freeCompilerArgs.add("-Xes-long-as-bigint")
            freeCompilerArgs.add("-XXLanguage:+JsAllowLongInExportedDeclarations")
        }
        useEsModules()
        generateTypeScriptDefinitions()
        binaries.library()
        configurePublishing()
    }

    wasmJs {
        outputModuleName.set("capstone-kt")
        nodejs()
        browser()
        compilerOptions {
            freeCompilerArgs.add("-Xwasm-use-new-exception-proposal")
        }
        binaries.library()
    }

    wasmWasi {
        nodejs()
        binaries.library()
    }

    // Apple targets (macOS only)
    if (Host.isMac) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
        macosX64()
        macosArm64()
        watchosX64()
        watchosArm64()
        watchosArm32()
        watchosDeviceArm64()
        watchosSimulatorArm64()
        tvosX64()
        tvosArm64()
        tvosSimulatorArm64()
    }
    linuxX64()
    linuxArm64()

    if (project.toolchains.mingwX64.get()) {
        mingwX64()
    }

    androidNativeArm64()
    androidNativeArm32()
    androidNativeX64()
    androidNativeX86()
}