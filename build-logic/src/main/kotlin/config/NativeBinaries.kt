@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package config

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractNativeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkConfig

fun KotlinNativeTarget.configureCapstoneBinaries() {
    binaries {
        val cfg: AbstractNativeLibrary.() -> Unit = {
            baseName = "libkotstone"
            transitiveExport = false
        }
        sharedLib(configure = cfg)
        staticLib(configure = cfg)
    }
}

fun KotlinNativeTarget.configureAppleFramework(xcf: XCFrameworkConfig) {
    binaries.framework {
        baseName = "KotstoneKit"
        isStatic = true
        exportKdoc.set(true)
        transitiveExport = false
        xcf.add(this)
        configurePublishing()
    }
}