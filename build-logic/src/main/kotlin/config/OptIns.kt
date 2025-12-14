package config

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun KotlinSourceSet.nativeOptIns() {
    languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
    languageSettings.optIn("kotlin.experimental.ExperimentalNativeApi")
    languageSettings.optIn("kotlin.native.internal.InternalForKotlinNative")
}

fun KotlinSourceSet.jsOptIns() {
    languageSettings.optIn("kotlin.js.ExperimentalWasmJsInterop")
    languageSettings.optIn("kotlin.js.ExperimentalJsStatic")
}

fun KotlinSourceSet.wasmOptIns() {
    languageSettings.optIn("kotlin.wasm.ExperimentalWasmInterop")
    languageSettings.optIn("kotlin.js.ExperimentalWasmJsInterop")
}

fun KotlinSourceSet.commonOptIns() {
    languageSettings.optIn("kotlin.ExperimentalUnsignedTypes")
    languageSettings.optIn("kotlin.time.ExperimentalTime")
    languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
}