package config

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family

fun KotlinMultiplatformExtension.configureSourceSets() {
    applyDefaultHierarchyTemplate {
        common {
            group("nonNative") {
                withJvm()
                withAndroidTarget()
                withWasmJs()
                withWasmWasi()
                withJs()
            }

            group("jvmAndAndroid") {
                withJvm()
                withAndroidTarget()
            }
        }
    }

    // Apply common opt-ins to ALL source sets
    sourceSets.all {
        commonOptIns()
    }

    // Apply native opt-ins to all native-related source sets, excluding nonNative
    val nativeKeywords = listOf("native", "apple", "ios", "macos", "linux", "mingw", "watchos", "tvos")
    sourceSets.matching { ss -> 
        nativeKeywords.any { keyword -> ss.name.contains(keyword, ignoreCase = true) } &&
        !ss.name.contains("nonNative", ignoreCase = true)
    }.configureEach {
        nativeOptIns()
    }

    sourceSets.matching { it.name.contains("web", true) }.configureEach {
        jsOptIns()
        wasmOptIns()
    }

    // Wasm
    sourceSets.matching { it.name.contains("wasmJs", true) }.configureEach {
        wasmOptIns()
        jsOptIns()
    }
    
    // JS
    sourceSets.matching { it.name.contains("js", true) }.configureEach {
        jsOptIns()
        wasmOptIns()
    }

    targets.filterIsInstance<KotlinNativeTarget>().forEach {
        it.configureCapstoneBinaries()
        if (it.konanTarget.family in appleFamilies) {
            it.configureAppleFramework()
        }
    }

}

val appleFamilies = arrayOf(Family.OSX, Family.IOS, Family.TVOS, Family.WATCHOS)