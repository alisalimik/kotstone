package config

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import platform.Host
import platform.Toolchains

fun KotlinMultiplatformExtension.configureTargets() {
    jvm()
    androidTarget()

    // JS / WASM
    js(IR) {
        nodejs()
        browser()
        compilerOptions {
            freeCompilerArgs.addAll("-Xes-long-as-bigint", "-XXLanguage:+JsAllowLongInExportedDeclarations")
        }
        binaries.library()
    }

    wasmJs {
        nodejs()
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

    // Windows
    if (Toolchains.mingwX64) {
        mingwX64()
    }

    // Android Native
    androidNativeArm64()
    androidNativeArm32()
    androidNativeX64()
    androidNativeX86()
}