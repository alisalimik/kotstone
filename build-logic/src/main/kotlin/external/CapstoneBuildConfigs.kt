package external

import org.gradle.api.Project
import platform.Host
import platform.toolchains

object CapstoneBuildConfigs {

    private fun macOSConfig(targetName: String, arch: String, shared: Boolean = false): CapstoneBuildConfig {
        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeSystemName = "Darwin",
            cmakeSystemProcessor = arch,
            archFlags = listOf("-arch", arch),
            additionalCMakeArgs = listOf(
                "-DCMAKE_OSX_ARCHITECTURES=$arch"
            ),
            enabled = Host.isMac,
            buildShared = shared
        )
    }

    private fun iOSConfig(targetName: String, arch: String, sdk: String, minVersion: String = "12.0"): CapstoneBuildConfig {
        // Use raw compiler flags to avoid CMake's automatic iOS platform handling
        // which adds unwanted universal binary flags
        val target = when {
            sdk == "iphonesimulator" -> "$arch-apple-ios$minVersion-simulator"
            else -> "$arch-apple-ios$minVersion"
        }

        // Map SDK name to proper platform/SDK directory names
        val (platformName, sdkName) = when (sdk) {
            "iphoneos" -> "iPhoneOS" to "iPhoneOS"
            "iphonesimulator" -> "iPhoneSimulator" to "iPhoneSimulator"
            else -> sdk.replaceFirstChar { it.uppercase() } to sdk.replaceFirstChar { it.uppercase() }
        }

        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeSystemName = "Generic",
            cmakeSystemProcessor = arch,
            archFlags = listOf(
                "-target", target,
                "-isysroot", "/Applications/Xcode.app/Contents/Developer/Platforms/$platformName.platform/Developer/SDKs/$sdkName.sdk"
            ),
            additionalCMakeArgs = listOf(
                "-DCMAKE_CROSSCOMPILING=TRUE",
                "-DCMAKE_TRY_COMPILE_TARGET_TYPE=STATIC_LIBRARY"
            ),
            enabled = Host.isMac
        )
    }

    private fun watchOSConfig(targetName: String, arch: String, sdk: String, minVersion: String = "4.0", reuseFrom: String? = null): CapstoneBuildConfig {
        // Use -target triple for proper architecture detection in watchOS SDK
        val target = when {
            sdk == "watchsimulator" -> "$arch-apple-watchos$minVersion-simulator"
            else -> "$arch-apple-watchos$minVersion"
        }

        val (platformName, sdkName) = when (sdk) {
            "watchos" -> "WatchOS" to "WatchOS"
            "watchsimulator" -> "WatchSimulator" to "WatchSimulator"
            else -> "WatchOS" to "WatchOS"
        }

        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeSystemName = "Generic",
            cmakeSystemProcessor = arch,
            archFlags = listOf(
                "-target", target,
                "-isysroot", "/Applications/Xcode.app/Contents/Developer/Platforms/$platformName.platform/Developer/SDKs/$sdkName.sdk"
            ),
            additionalCMakeArgs = listOf(
                "-DCMAKE_CROSSCOMPILING=TRUE",
                "-DCMAKE_TRY_COMPILE_TARGET_TYPE=STATIC_LIBRARY"
            ),
            enabled = Host.isMac,
            reuseOutputFrom = reuseFrom
        )
    }

    private fun tvOSConfig(targetName: String, arch: String, sdk: String, minVersion: String = "12.0"): CapstoneBuildConfig {
        // Use -target triple for proper architecture detection in tvOS SDK
        val target = when {
            sdk == "appletvsimulator" -> "$arch-apple-tvos$minVersion-simulator"
            else -> "$arch-apple-tvos$minVersion"
        }

        val (platformName, sdkName) = when (sdk) {
            "appletvos" -> "AppleTVOS" to "AppleTVOS"
            "appletvsimulator" -> "AppleTVSimulator" to "AppleTVSimulator"
            else -> "AppleTVOS" to "AppleTVOS"
        }

        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeSystemName = "Generic",
            cmakeSystemProcessor = arch,
            archFlags = listOf(
                "-target", target,
                "-isysroot", "/Applications/Xcode.app/Contents/Developer/Platforms/$platformName.platform/Developer/SDKs/$sdkName.sdk"
            ),
            additionalCMakeArgs = listOf(
                "-DCMAKE_CROSSCOMPILING=TRUE",
                "-DCMAKE_TRY_COMPILE_TARGET_TYPE=STATIC_LIBRARY"
            ),
            enabled = Host.isMac
        )
    }

    private fun linuxConfig(project: Project, targetName: String, arch: String, triple: String? = null, shared: Boolean = false): CapstoneBuildConfig {
        val useZig = project.toolchains.hasZig.get()
        val useCrossGCC = triple != null && project.toolchains.commandExists("$triple-gcc").get()

        if (useZig) {
            println("Configuration: Enabling Linux target $targetName using Zig")
        } else {
            println("Configuration: Zig not found, checking for cross-GCC for $targetName...")
        }

        return when {
            useZig -> CapstoneBuildConfig(
                targetName = targetName,
                cmakeSystemName = "Linux", // Explicitly set for cross-compilation
                cCompiler = "zig",
                cxxCompiler = "zig",
                additionalCMakeArgs = listOf(
                    // Toolchain file handles compiler setup, we just pass arch flags here if needed
                    // Zig uses -target for cross compilation
                    "-DCMAKE_C_FLAGS=-target $arch-linux-gnu",
                    "-DCMAKE_CXX_FLAGS=-target $arch-linux-gnu"
                ),
                enabled = true,
                buildShared = shared
            )
            useCrossGCC -> CapstoneBuildConfig(
                targetName = targetName,
                cCompiler = "$triple-gcc",
                cxxCompiler = "$triple-g++",
                cmakeSystemName = "Linux",
                cmakeSystemProcessor = arch,
                additionalCMakeArgs = listOf(
                    "-DCMAKE_TRY_COMPILE_TARGET_TYPE=STATIC_LIBRARY",
                    "-DCMAKE_OSX_ARCHITECTURES=",
                    "-DCMAKE_OSX_SYSROOT="
                ),
                enabled = true,
                buildShared = shared
            )
            project.toolchains.nativeLinux.get() -> CapstoneBuildConfig(
                targetName = targetName,
                cmakeSystemName = "Linux", // Explicitly set even for native builds
                enabled = true,
                buildShared = shared
            )
            // Fallback for macOS hosts using Docker or similar (not implemented yet)
            else -> CapstoneBuildConfig(
                targetName = targetName,
                enabled = false,
                buildShared = shared
            )
        }
    }

    private fun mingwConfig(project: Project, targetName: String, arch: String, triple: String, shared: Boolean = false): CapstoneBuildConfig {
        val useZig = project.toolchains.hasZig.get()
        val useCrossGCC = project.toolchains.commandExists("$triple-gcc").get()

        return when {
            useZig -> CapstoneBuildConfig(
                targetName = targetName,
                cmakeSystemName = "Windows",
                cCompiler = "zig",
                cxxCompiler = "zig",
                additionalCMakeArgs = listOf(
                    "-DCMAKE_C_FLAGS=-target $arch-windows-gnu",
                    "-DCMAKE_CXX_FLAGS=-target $arch-windows-gnu"
                ),
                enabled = true,
                buildShared = shared
            )
            useCrossGCC -> CapstoneBuildConfig(
                targetName = targetName,
                cCompiler = "$triple-gcc",
                cxxCompiler = "$triple-g++",
                cmakeSystemName = "Windows",
                cmakeSystemProcessor = if (arch == "x86_64") "AMD64" else "x86",
                enabled = true,
                buildShared = shared
            )
            project.toolchains.mingwX64.get() && arch == "x86_64" -> CapstoneBuildConfig( // Fallback for native/other
                targetName = targetName,
                cmakeSystemName = "Windows",
                enabled = true,
                buildShared = shared
            )
            else -> CapstoneBuildConfig(
                targetName = targetName,
                enabled = false
            )
        }
    }

    private fun androidNativeConfig(targetName: String, abi: String, buildShared: Boolean = false): CapstoneBuildConfig {
        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeSystemName = "Android", // Explicitly set to Android
            cmakeToolchainFile = null, // Will be set later when we have project context
            additionalCMakeArgs = listOf(
                "-DANDROID_ABI=$abi",
                "-DANDROID_PLATFORM=android-21"
            ),
            enabled = false, // Will be enabled later when we have project context
            buildShared = buildShared
        )
    }

    private fun wasmConfig(project: Project, targetName: String, isWasi: Boolean = false): CapstoneBuildConfig {
        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeToolchainFile = null, // Will be set to Emscripten toolchain
            additionalCMakeArgs = if (isWasi) {
                listOf(
                    "-DCMAKE_C_FLAGS=-D_WASI_EMULATED_MMAN",
                    "-DCMAKE_CXX_FLAGS=-D_WASI_EMULATED_MMAN"
                )
            } else emptyList(),
            enabled = project.toolchains.hasEmscripten.get()
        )
    }

    fun getAndroidNdkPath(project: Project): String? {
        return System.getenv("ANDROID_NDK_ROOT")
            ?: System.getenv("ANDROID_NDK")
            ?: project.findProperty("ANDROID_NDK_ROOT") as? String
            ?: project.findProperty("ANDROID_NDK") as? String
    }

    fun enableAndroidConfigs(project: Project, configs: Map<String, CapstoneBuildConfig>): Map<String, CapstoneBuildConfig> {
        val ndkPath = getAndroidNdkPath(project)
        if (ndkPath == null) return configs

        val toolchainFile = "$ndkPath/build/cmake/android.toolchain.cmake"

        return configs.mapValues { (key, config) ->
            if (key.contains("android", ignoreCase = true)) {
                config.copy(
                    cmakeToolchainFile = toolchainFile,
                    additionalCMakeArgs = config.additionalCMakeArgs + "-DANDROID_NDK=$ndkPath",
                    enabled = true
                )
            } else {
                config
            }
        }
    }

    fun getConfigs(project: Project) = mapOf(
        // macOS
        "macosX64" to macOSConfig("macosX64", "x86_64"),
        "macosArm64" to macOSConfig("macosArm64", "arm64"),

        // macOS Shared (for JVM)
        "macosX64Shared" to macOSConfig("macosX64Shared", "x86_64", shared = true),
        "macosArm64Shared" to macOSConfig("macosArm64Shared", "arm64", shared = true),

        // iOS
        "iosX64" to iOSConfig("iosX64", "x86_64", "iphonesimulator"),
        "iosArm64" to iOSConfig("iosArm64", "arm64", "iphoneos"),
        "iosSimulatorArm64" to iOSConfig("iosSimulatorArm64", "arm64", "iphonesimulator"),

        // watchOS
        "watchosX64" to watchOSConfig("watchosX64", "x86_64", "watchsimulator"),
        "watchosArm64" to watchOSConfig("watchosArm64", "arm64_32", "watchos", minVersion = "5.0"),
        "watchosArm32" to watchOSConfig("watchosArm32", "armv7k", "watchos"),
        "watchosDeviceArm64" to watchOSConfig("watchosDeviceArm64", "arm64", "watchos", minVersion = "9.0"),
        "watchosSimulatorArm64" to watchOSConfig("watchosSimulatorArm64", "arm64", "watchsimulator"),

        // tvOS
        "tvosX64" to tvOSConfig("tvosX64", "x86_64", "appletvsimulator"),
        "tvosArm64" to tvOSConfig("tvosArm64", "arm64", "appletvos"),
        "tvosSimulatorArm64" to tvOSConfig("tvosSimulatorArm64", "arm64", "appletvsimulator"),

        // Linux
        "linuxX64" to linuxConfig(project, "linuxX64", "x86_64"),
        "linuxArm64" to linuxConfig(project, "linuxArm64", "aarch64", "aarch64-unknown-linux-gnu"),

        // Linux Shared (for JVM)
        "linuxX64Shared" to linuxConfig(project, "linuxX64Shared", "x86_64", shared = true),
        "linuxX86Shared" to linuxConfig(project, "linuxX86Shared", "x86", shared = true),
        "linuxArm64Shared" to linuxConfig(project, "linuxArm64Shared", "aarch64", "aarch64-unknown-linux-gnu", shared = true),
        "linuxArm32Shared" to linuxConfig(project, "linuxArm32Shared", "arm", "arm-unknown-linux-gnueabihf", shared = true),

        // Windows
        "mingwX64" to mingwConfig(project, "mingwX64", "x86_64", "x86_64-w64-mingw32"),

        // Windows Shared (for JVM)
        "mingwX64Shared" to mingwConfig(project, "mingwX64Shared", "x86_64", "x86_64-w64-mingw32", shared = true),
        "mingwX86Shared" to mingwConfig(project, "mingwX86Shared", "x86", "i686-w64-mingw32", shared = true),

        // Android Native (static for native targets)
        "androidNativeArm64" to androidNativeConfig("androidNativeArm64", "arm64-v8a"),
        "androidNativeArm32" to androidNativeConfig("androidNativeArm32", "armeabi-v7a"),
        "androidNativeX64" to androidNativeConfig("androidNativeX64", "x86_64"),
        "androidNativeX86" to androidNativeConfig("androidNativeX86", "x86"),

        // Android Shared (for Android runtime)
        "androidArm64Shared" to androidNativeConfig("androidArm64Shared", "arm64-v8a", buildShared = true),
        "androidArm32Shared" to androidNativeConfig("androidArm32Shared", "armeabi-v7a", buildShared = true),
        "androidX64Shared" to androidNativeConfig("androidX64Shared", "x86_64", buildShared = true),
        "androidX86Shared" to androidNativeConfig("androidX86Shared", "x86", buildShared = true),

        // WASM
        "wasmJs" to wasmConfig(project, "wasmJs", isWasi = false),
        "wasmWasi" to wasmConfig(project, "wasmWasi", isWasi = true)
    )

    // Mapping from target name to Android ABI for shared library output
    fun getAndroidAbi(targetName: String): String? = when(targetName) {
        "androidArm64Shared", "androidNativeArm64" -> "arm64-v8a"
        "androidArm32Shared", "androidNativeArm32" -> "armeabi-v7a"
        "androidX64Shared", "androidNativeX64" -> "x86_64"
        "androidX86Shared", "androidNativeX86" -> "x86"
        else -> null
    }

    // Mapping from target name to JVM platform classifier
    // These classifiers are used for publishing platform-specific JARs
    // Format: "capstone-{platform}-{arch}"
    fun getJvmPlatformClassifier(targetName: String): String? = when(targetName) {
        "macosX64Shared" -> "capstone-macos-x64"
        "macosArm64Shared" -> "capstone-macos-arm64"
        "linuxX64Shared" -> "capstone-linux-x64"
        "linuxX86Shared" -> "capstone-linux-x86"
        "linuxArm64Shared" -> "capstone-linux-arm64"
        "linuxArm32Shared" -> "capstone-linux-arm32"
        "mingwX64Shared" -> "capstone-windows-x64"
        "mingwX86Shared" -> "capstone-windows-x86"
        else -> null
    }

    // Returns all JVM shared library target names
    // These targets produce shared libraries for JVM consumption
    fun getAllJvmSharedTargets(): List<String> = listOf(
        "macosX64Shared",
        "macosArm64Shared",
        "linuxX64Shared",
        "linuxX86Shared",
        "linuxArm64Shared",
        "linuxArm32Shared",
        "mingwX64Shared",
        "mingwX86Shared"
    )
}
