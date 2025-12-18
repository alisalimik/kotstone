package external

import org.gradle.api.Project
import platform.Host
import platform.toolchains
import java.io.File

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

    private fun linuxConfig(project: Project, targetName: String, arch: String, triple: String? = null, shared: Boolean = false, abi: String = "gnu"): CapstoneBuildConfig {
        val useZig = project.toolchains.hasZig.get()
        val useCrossGCC = triple != null && project.toolchains.commandExists("$triple-gcc").get()

        // Prefer cross-GCC over Zig for better reliability
        if (useCrossGCC) {
            println("Configuration: Enabling Linux target $targetName using cross-GCC ($triple-gcc)")
        } else if (useZig) {
            println("Configuration: Enabling Linux target $targetName using Zig")
        } else {
            println("Configuration: No cross-compilation toolchain found for $targetName")
        }

        return when {
            // Prefer cross-GCC first for better reliability on macOS
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
            useZig -> CapstoneBuildConfig(
                targetName = targetName,
                cmakeSystemName = "Linux", // Explicitly set for cross-compilation
                cCompiler = "zig",
                cxxCompiler = "zig",
                additionalCMakeArgs = listOf(
                    // Toolchain file handles compiler setup, we just pass arch flags here if needed
                    // Zig uses -target for cross compilation
                    "-DCMAKE_C_FLAGS=-target $arch-linux-$abi",
                    "-DCMAKE_CXX_FLAGS=-target $arch-linux-$abi"
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
        val isNativeWindows = Host.isWindows

        // Debug logging
        println("Configuration: mingwConfig for $targetName (triple=$triple, arch=$arch)")
        println("  - isNativeWindows: $isNativeWindows")
        println("  - useZig: $useZig")
        println("  - useCrossGCC ($triple-gcc): $useCrossGCC")
        println("  - shared: $shared")

        return when {
            // On native Windows, use the native MinGW compiler (let CMake find it)
            isNativeWindows && project.toolchains.mingwX64.get() && arch == "x86_64" -> {
                println("Configuration: Enabling Windows target $targetName using native MinGW")
                CapstoneBuildConfig(
                    targetName = targetName,
                    cmakeSystemName = "Windows",
                    enabled = true,
                    buildShared = shared
                )
            }
            isNativeWindows && project.toolchains.mingwX86.get() && arch == "x86" -> {
                println("Configuration: Enabling Windows target $targetName using native MinGW")
                CapstoneBuildConfig(
                    targetName = targetName,
                    cmakeSystemName = "Windows",
                    enabled = true,
                    buildShared = shared
                )
            }
            // Cross-compilation: prefer MinGW cross-GCC over Zig
            useCrossGCC -> {
                println("Configuration: Enabling Windows target $targetName using cross-GCC ($triple-gcc)")
                CapstoneBuildConfig(
                    targetName = targetName,
                    cCompiler = "$triple-gcc",
                    cxxCompiler = "$triple-g++",
                    cmakeSystemName = "Windows",
                    cmakeSystemProcessor = if (arch == "x86_64") "AMD64" else "x86",
                    enabled = true,
                    buildShared = shared
                )
            }
            // Cross-compilation: use Zig as fallback
            useZig && !isNativeWindows -> {
                println("Configuration: Enabling Windows target $targetName using Zig")
                CapstoneBuildConfig(
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
            }
            else -> {
                println("Configuration: No cross-compilation toolchain found for $targetName")
                CapstoneBuildConfig(
                    targetName = targetName,
                    enabled = false,
                    buildShared = shared
                )
            }
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
        // First, check environment variables
        System.getenv("ANDROID_NDK_ROOT")?.let { return it }
        System.getenv("ANDROID_NDK")?.let { return it }

        // Check gradle properties
        (project.findProperty("ANDROID_NDK_ROOT") as? String)?.let { return it }
        (project.findProperty("ANDROID_NDK") as? String)?.let { return it }

        // Auto-detect Android SDK location
        val sdkRoot = System.getenv("ANDROID_SDK_ROOT")
            ?: System.getenv("ANDROID_HOME")
            ?: getDefaultAndroidSdkPath()

        if (sdkRoot == null) {
            project.logger.warn("Android SDK not found. Set ANDROID_SDK_ROOT or ANDROID_HOME environment variable.")
            return null
        }

        val sdkDir = File(sdkRoot)
        if (!sdkDir.exists()) {
            project.logger.warn("Android SDK directory not found at: $sdkRoot")
            return null
        }

        // Find the latest NDK version in the SDK
        val ndkDir = File(sdkDir, "ndk")
        if (!ndkDir.exists() || !ndkDir.isDirectory) {
            project.logger.warn("Android NDK directory not found at: ${ndkDir.absolutePath}")
            return null
        }

        val ndkVersions = ndkDir.listFiles()?.filter { it.isDirectory }?.sortedByDescending { it.name }
        if (ndkVersions.isNullOrEmpty()) {
            project.logger.warn("No NDK versions found in: ${ndkDir.absolutePath}")
            return null
        }

        val latestNdk = ndkVersions.first()
        project.logger.info("Auto-detected Android NDK: ${latestNdk.absolutePath}")
        return latestNdk.absolutePath
    }

    private fun getDefaultAndroidSdkPath(): String? {
        val osName = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")

        return when {
            osName.contains("mac") -> "$userHome/Library/Android/sdk"
            osName.contains("win") -> System.getenv("LOCALAPPDATA")?.let { "$it\\Android\\Sdk" }
            osName.contains("linux") -> "$userHome/Android/Sdk"
            else -> null
        }?.let { path ->
            if (File(path).exists()) path else null
        }
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
        "linuxArm32Hfp" to linuxConfig(project, "linuxArm32Hfp", "arm", "arm-unknown-linux-gnueabihf", abi = "gnueabihf"),

        // Linux Shared (for JVM)
        "linuxX64Shared" to linuxConfig(project, "linuxX64Shared", "x86_64", shared = true),
        "linuxX86Shared" to linuxConfig(project, "linuxX86Shared", "x86", shared = true),
        "linuxArm64Shared" to linuxConfig(project, "linuxArm64Shared", "aarch64", "aarch64-unknown-linux-gnu", shared = true),
        "linuxArm32Shared" to linuxConfig(project, "linuxArm32Shared", "arm", "arm-unknown-linux-gnueabihf", shared = true, abi = "gnueabihf"),

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

    /**
     * Configuration-cache compatible version of getConfigs that uses BuildContext
     * instead of Project
     */
    fun getConfigsFromContext(buildContext: BuildContext): Map<String, CapstoneBuildConfig> {
        // Helper to create Android configs with NDK from context
        fun androidConfigFromContext(targetName: String, abi: String, buildShared: Boolean = false): CapstoneBuildConfig {
            val ndkPath = buildContext.androidNdkPath
            return if (ndkPath != null) {
                val toolchainFile = "$ndkPath/build/cmake/android.toolchain.cmake"
                CapstoneBuildConfig(
                    targetName = targetName,
                    cmakeSystemName = "Android",
                    cmakeToolchainFile = toolchainFile,
                    additionalCMakeArgs = listOf(
                        "-DANDROID_ABI=$abi",
                        "-DANDROID_PLATFORM=android-21",
                        "-DANDROID_NDK=$ndkPath"
                    ),
                    enabled = true,
                    buildShared = buildShared
                )
            } else {
                CapstoneBuildConfig(
                    targetName = targetName,
                    cmakeSystemName = "Android",
                    additionalCMakeArgs = listOf(
                        "-DANDROID_ABI=$abi",
                        "-DANDROID_PLATFORM=android-21"
                    ),
                    enabled = false,
                    buildShared = buildShared
                )
            }
        }

        // Helper to create Linux configs with captured toolchain state
        fun linuxConfigFromContext(targetName: String, arch: String, triple: String? = null, shared: Boolean = false, abi: String = "gnu"): CapstoneBuildConfig {
            // For configuration cache compatibility, we use simplified logic
            // Zig cross-compilation from macOS to Linux
            val isLinuxOnMac = buildContext.linuxX64OnMac
            val isNativeLinux = buildContext.nativeLinux

            return when {
                isLinuxOnMac -> CapstoneBuildConfig(
                    targetName = targetName,
                    cmakeSystemName = "Linux",
                    cCompiler = "zig",
                    cxxCompiler = "zig",
                    additionalCMakeArgs = listOf(
                        "-DCMAKE_C_FLAGS=-target $arch-linux-$abi",
                        "-DCMAKE_CXX_FLAGS=-target $arch-linux-$abi",
                        // Add linker configuration for Zig
                        "-DCMAKE_EXE_LINKER_FLAGS=-target $arch-linux-$abi",
                        "-DCMAKE_SHARED_LINKER_FLAGS=-target $arch-linux-$abi"
                    ),
                    enabled = true,
                    buildShared = shared
                )
                isNativeLinux -> CapstoneBuildConfig(
                    targetName = targetName,
                    cmakeSystemName = "Linux",
                    enabled = true,
                    buildShared = shared
                )
                else -> CapstoneBuildConfig(
                    targetName = targetName,
                    enabled = false,
                    buildShared = shared
                )
            }
        }

        // Helper to create mingw configs
        fun mingwConfigFromContext(targetName: String, arch: String, shared: Boolean = false): CapstoneBuildConfig {
            val isNativeWindows = buildContext.nativeWindows
            val hasCrossGCC = if (arch == "x86_64") buildContext.hasMingwX64CrossGCC else buildContext.hasMingwX86CrossGCC
            val triple = if (arch == "x86_64") "x86_64-w64-mingw32" else "i686-w64-mingw32"

            // Debug logging
            buildContext.logger.lifecycle("Configuration: mingwConfigFromContext for $targetName (triple=$triple, arch=$arch)")
            buildContext.logger.lifecycle("  - isNativeWindows: $isNativeWindows")
            buildContext.logger.lifecycle("  - hasCrossGCC: $hasCrossGCC")
            buildContext.logger.lifecycle("  - mingwX64: ${buildContext.mingwX64}, mingwX86: ${buildContext.mingwX86}")

            return when {
                // On native Windows, use the native MinGW compiler
                isNativeWindows && buildContext.mingwX64 && arch == "x86_64" -> {
                    buildContext.logger.lifecycle("Configuration: Using native MinGW for $targetName")
                    CapstoneBuildConfig(
                        targetName = targetName,
                        cmakeSystemName = "Windows",
                        enabled = true,
                        buildShared = shared
                    )
                }
                isNativeWindows && buildContext.mingwX86 && arch == "x86" -> {
                    buildContext.logger.lifecycle("Configuration: Using native MinGW for $targetName")
                    CapstoneBuildConfig(
                        targetName = targetName,
                        cmakeSystemName = "Windows",
                        enabled = true,
                        buildShared = shared
                    )
                }
                // Cross-compilation: prefer MinGW cross-GCC over Zig
                hasCrossGCC -> {
                    buildContext.logger.lifecycle("Configuration: Using MinGW cross-GCC for $targetName ($triple-gcc)")
                    CapstoneBuildConfig(
                        targetName = targetName,
                        cCompiler = "$triple-gcc",
                        cxxCompiler = "$triple-g++",
                        cmakeSystemName = "Windows",
                        cmakeSystemProcessor = if (arch == "x86_64") "AMD64" else "x86",
                        enabled = true,
                        buildShared = shared
                    )
                }
                // Cross-compilation: use Zig as fallback
                !isNativeWindows && (buildContext.mingwX64 || buildContext.mingwX86) -> {
                    buildContext.logger.lifecycle("Configuration: Using Zig for $targetName (MinGW cross-GCC not found)")
                    CapstoneBuildConfig(
                        targetName = targetName,
                        cmakeSystemName = "Windows",
                        cCompiler = "zig",
                        cxxCompiler = "zig",
                        additionalCMakeArgs = listOf(
                            "-DCMAKE_C_FLAGS=-target $arch-windows-gnu",
                            "-DCMAKE_CXX_FLAGS=-target $arch-windows-gnu",
                            "-DCMAKE_EXE_LINKER_FLAGS=-target $arch-windows-gnu",
                            "-DCMAKE_SHARED_LINKER_FLAGS=-target $arch-windows-gnu"
                        ),
                        enabled = true,
                        buildShared = shared
                    )
                }
                else -> {
                    buildContext.logger.lifecycle("Configuration: No toolchain found for $targetName")
                    CapstoneBuildConfig(
                        targetName = targetName,
                        enabled = false,
                        buildShared = shared
                    )
                }
            }
        }

        return mapOf(
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

            // Linux - use simplified configs
            "linuxX64" to linuxConfigFromContext("linuxX64", "x86_64"),
            "linuxArm64" to linuxConfigFromContext("linuxArm64", "aarch64"),
            "linuxArm32Hfp" to linuxConfigFromContext("linuxArm32Hfp", "arm", abi = "gnueabihf"),

            // Linux Shared (for JVM)
            "linuxX64Shared" to linuxConfigFromContext("linuxX64Shared", "x86_64", shared = true),
            "linuxX86Shared" to linuxConfigFromContext("linuxX86Shared", "x86", shared = true),
            "linuxArm64Shared" to linuxConfigFromContext("linuxArm64Shared", "aarch64", shared = true),
            "linuxArm32Shared" to linuxConfigFromContext("linuxArm32Shared", "arm", shared = true, abi = "gnueabihf"),

            // Windows
            "mingwX64" to mingwConfigFromContext("mingwX64", "x86_64"),

            // Windows Shared (for JVM)
            "mingwX64Shared" to mingwConfigFromContext("mingwX64Shared", "x86_64", shared = true),
            "mingwX86Shared" to mingwConfigFromContext("mingwX86Shared", "x86", shared = true),

            // Android Native (static for native targets)
            "androidNativeArm64" to androidConfigFromContext("androidNativeArm64", "arm64-v8a"),
            "androidNativeArm32" to androidConfigFromContext("androidNativeArm32", "armeabi-v7a"),
            "androidNativeX64" to androidConfigFromContext("androidNativeX64", "x86_64"),
            "androidNativeX86" to androidConfigFromContext("androidNativeX86", "x86"),

            // Android Shared (for Android runtime)
            "androidArm64Shared" to androidConfigFromContext("androidArm64Shared", "arm64-v8a", buildShared = true),
            "androidArm32Shared" to androidConfigFromContext("androidArm32Shared", "armeabi-v7a", buildShared = true),
            "androidX64Shared" to androidConfigFromContext("androidX64Shared", "x86_64", buildShared = true),
            "androidX86Shared" to androidConfigFromContext("androidX86Shared", "x86", buildShared = true),

            // WASM (configuration cache compatible - check emscripten from context)
            "wasmJs" to CapstoneBuildConfig(
                targetName = "wasmJs",
                enabled = buildContext.hasEmscripten
            ),
            "wasmWasi" to CapstoneBuildConfig(
                targetName = "wasmWasi",
                enabled = buildContext.hasEmscripten
            )
        )
    }
    /**
     * Calculates the output directory for the given target.
     * This is used to set the @OutputDirectory for the Gradle task.
     */
    fun getOutputDir(project: Project, targetName: String): File {
        val projectDir = project.rootProject.projectDir
        
        // WASM targets
        if (targetName == "wasmWasi") {
            return File(projectDir, "library/src/wasmWasiMain/resources")
        }
        if (targetName == "wasmJs") {
            return File(projectDir, "library/src/webMain/resources")
        }

        // Native targets
        val configs = getConfigs(project)
        val config = configs[targetName] ?: return File(projectDir, "library/interop/lib/$targetName") // Fallback
        
        return when {
            config.buildShared && getAndroidAbi(targetName) != null -> {
                val abi = getAndroidAbi(targetName)!!
                File(projectDir, "library/interop/linked-android/$abi")
            }
            config.buildShared && getJvmPlatformClassifier(targetName) != null -> {
                val classifier = getJvmPlatformClassifier(targetName)!!
                File(projectDir, "library/src/jvmMain/resources/libs/$classifier")
            }
            else -> File(projectDir, "library/interop/lib/$targetName")
        }
    }
}
