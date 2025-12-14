package config

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.configureArgs() {
    compilerOptions {
        freeCompilerArgs.addAll("-Xcontext-parameters", "-Xexpect-actual-classes")
    }
}