@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package config

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractNativeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

fun KotlinNativeTarget.configureCapstoneBinaries() {
    binaries {
        val cfg: AbstractNativeLibrary.() -> Unit = {
            baseName = "libcapstone_kt"
            transitiveExport = false
        }
        sharedLib(configure = cfg)
        staticLib(configure = cfg)
    }
}

fun KotlinNativeTarget.configureAppleFramework() {
    binaries.framework {
        baseName = "CapstoneKtKit"
        isStatic = true
        exportKdoc.set(true)
        transitiveExport = false
    }
}