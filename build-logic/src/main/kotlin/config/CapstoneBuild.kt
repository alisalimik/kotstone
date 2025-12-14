package config

import org.gradle.api.Action
import org.gradle.process.ExecSpec
import org.gradle.api.Project
import platform.Host
import platform.Toolchains
import java.io.File
import org.gradle.api.tasks.Input
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

// Configuration for Capstone build
data class CapstoneBuildConfig(
    val targetName: String,
    val cmakeToolchainFile: String? = null,
    val cmakeSystemName: String? = null,
    val cmakeSystemProcessor: String? = null,
    val cCompiler: String? = null,
    val cxxCompiler: String? = null,
    val archFlags: List<String> = emptyList(),
    val additionalCMakeArgs: List<String> = emptyList(),
    val enabled: Boolean = true,
    val buildShared: Boolean = false,
    val reuseOutputFrom: String? = null // New property to reuse another target's output
)

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

    private fun linuxConfig(targetName: String, arch: String, triple: String? = null, shared: Boolean = false): CapstoneBuildConfig {
        val useZig = Toolchains.hasZig
        val useCrossGCC = triple != null && Toolchains.commandExists("$triple-gcc")

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
            Toolchains.nativeLinux -> CapstoneBuildConfig(
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

    private fun mingwConfig(targetName: String, arch: String, triple: String, shared: Boolean = false): CapstoneBuildConfig {
        val useZig = Toolchains.hasZig
        val useCrossGCC = Toolchains.commandExists("$triple-gcc")

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
            Toolchains.mingwX64 && arch == "x86_64" -> CapstoneBuildConfig( // Fallback for native/other
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

    private fun wasmConfig(targetName: String, isWasi: Boolean = false): CapstoneBuildConfig {
        return CapstoneBuildConfig(
            targetName = targetName,
            cmakeToolchainFile = null, // Will be set to Emscripten toolchain
            additionalCMakeArgs = if (isWasi) {
                listOf(
                    "-DCMAKE_C_FLAGS=-D_WASI_EMULATED_MMAN",
                    "-DCMAKE_CXX_FLAGS=-D_WASI_EMULATED_MMAN"
                )
            } else emptyList(),
            enabled = Toolchains.hasEmscripten
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

    val configs = mapOf(
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
        "linuxX64" to linuxConfig("linuxX64", "x86_64"),
        "linuxArm64" to linuxConfig("linuxArm64", "aarch64", "aarch64-unknown-linux-gnu"),
        
        // Linux Shared (for JVM)
        "linuxX64Shared" to linuxConfig("linuxX64Shared", "x86_64", shared = true),
        "linuxX86Shared" to linuxConfig("linuxX86Shared", "x86", shared = true),
        "linuxArm64Shared" to linuxConfig("linuxArm64Shared", "aarch64", "aarch64-unknown-linux-gnu", shared = true),
        "linuxArm32Shared" to linuxConfig("linuxArm32Shared", "arm", "arm-unknown-linux-gnueabihf", shared = true),

        // Windows
        "mingwX64" to mingwConfig("mingwX64", "x86_64", "x86_64-w64-mingw32"),

        // Windows Shared (for JVM)
        "mingwX64Shared" to mingwConfig("mingwX64Shared", "x86_64", "x86_64-w64-mingw32", shared = true),
        "mingwX86Shared" to mingwConfig("mingwX86Shared", "x86", "i686-w64-mingw32", shared = true),

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
        "wasmJs" to wasmConfig("wasmJs", isWasi = false),
        "wasmWasi" to wasmConfig("wasmWasi", isWasi = true)
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

fun buildCapstoneWasm(project: Project, targetName: String, config: CapstoneBuildConfig) {
    val projectDir = project.rootProject.projectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")
    val workDir = File(projectDir, "build/capstone_wasm_build")
    val buildDir = File(workDir, "build")
    val distDir = File(workDir, "dist")

    // Determine output directory
    val outputDir = when (targetName) {
        "wasmWasi" -> File(projectDir, "library/src/wasmWasiMain/resources")
        "wasmJs" -> File(projectDir, "library/src/webMain/resources")
        else -> File(projectDir, "library/src/webMain/resources")
    }

    val isWasi = targetName == "wasmWasi"

    // Check if output already exists
    val outputFile = if (isWasi) {
        File(outputDir, "capstone-wasi.wasm")
    } else {
        File(outputDir, "capstone.wasm")
    }

    if (outputFile.exists()) {
        project.logger.lifecycle("WASM library already exists for $targetName at $outputFile")
        return
    }

    project.logger.lifecycle("Building Capstone WASM for $targetName...")

    // Find Emscripten toolchain
    val toolchainFile = Toolchains.findEmscriptenToolchain()
    if (toolchainFile == null) {
        project.logger.error("Could not find Emscripten.cmake toolchain file")
        throw createException("Emscripten toolchain not found")
    }

    // Step 1: Configure CMake for static library
    project.logger.lifecycle("Configuring CMake for WebAssembly static library...")
    project.mkdir(buildDir)

    val cmakeConfigArgs = listOf(
        "cmake", "-S", capstoneSource.absolutePath, "-B", buildDir.absolutePath,
        "-DCMAKE_TOOLCHAIN_FILE=$toolchainFile",
        "-DCMAKE_BUILD_TYPE=Release",
        "-DCAPSTONE_BUILD_SHARED_LIBS=OFF",
        "-DCAPSTONE_BUILD_STATIC_LIBS=ON",
        "-DCAPSTONE_BUILD_CSTOOL=OFF",
        "-DCAPSTONE_INSTALL=OFF",
        "-DCAPSTONE_ARCHITECTURE_DEFAULT=ON"
    )

    project.providers.exec {
        commandLine(cmakeConfigArgs)
        isIgnoreExitValue = false
    }.result.get()

    // Step 2: Build static library
    project.logger.lifecycle("Building libcapstone.a...")
    val makeJobs = Runtime.getRuntime().availableProcessors()

    project.providers.exec {
        commandLine("cmake", "--build", buildDir.absolutePath, "-j$makeJobs")
        isIgnoreExitValue = false
    }.result.get()

    val staticLib = File(buildDir, "libcapstone.a")
    if (!staticLib.exists()) {
        throw createException("Static library build failed. File not found: ${staticLib.absolutePath}")
    }

    // Step 3: Generate export list from compiled library
    project.logger.lifecycle("Generating exported function list from compiled library...")
    val llvmNm = Toolchains.getLlvmNm()
    if (llvmNm == null) {
        throw createException("llvm-nm not found. Cannot extract symbols.")
    }

    val nmOutput = project.providers.exec {
        commandLine(llvmNm, staticLib.absolutePath)
        isIgnoreExitValue = false
    }.result.get().assertNormalExitValue()

    // Extract cs_* function symbols
    val symbolsOutput = java.io.ByteArrayOutputStream()
    project.providers.exec {
        commandLine(llvmNm, staticLib.absolutePath)
        standardOutput = symbolsOutput
        isIgnoreExitValue = false
    }.result.get()

    val capstoneFuncs = symbolsOutput.toString()
        .lines()
        .filter { it.contains(" T ") }
        .mapNotNull { line ->
            val match = "\\bcs_[a-zA-Z0-9_]*".toRegex().find(line)
            match?.value
        }
        .distinct()
        .sorted()
        .joinToString(",") { "'_$it'" }

    if (capstoneFuncs.isEmpty()) {
        throw createException("No functions found to export! Check symbol extraction.")
    }

    val funcCount = capstoneFuncs.split(",").size
    project.logger.lifecycle("Found $funcCount capstone functions to export.")

    // System functions required for Kotlin/Wasm
    val systemFuncs = "'_malloc','_free'"
    val finalExports = "[$systemFuncs,$capstoneFuncs]"

    project.mkdir(distDir)

    // Step 4: Link the appropriate module
    if (isWasi) {
        // WASI Module
        project.logger.lifecycle("Linking WASI module...")

        val emccArgs = listOf(
            "emcc", staticLib.absolutePath,
            "-o", File(distDir, "capstone-wasi.wasm").absolutePath,
            "-O3",
            "-s", "WASM=1",
            "-s", "STANDALONE_WASM=1",
            "-s", "EXPORTED_FUNCTIONS=$finalExports",
            "--no-entry",
            "-s", "EXPORTED_RUNTIME_METHODS=[]",
            "-D_WASI_EMULATED_MMAN"
        )

        project.providers.exec {
            commandLine(emccArgs)
            isIgnoreExitValue = false
        }.result.get()
    } else {
        // Web Module (JS/Wasm)
        project.logger.lifecycle("Linking Web (JS/Wasm) module...")

        val exportedRuntime = "['ccall','cwrap','getValue','setValue','UTF8ToString','writeArrayToMemory','addFunction','removeFunction']"

        val emccArgs = listOf(
            "emcc", staticLib.absolutePath,
            "-o", File(distDir, "capstone.js").absolutePath,
            "-O3",
            "-s", "WASM=1",
            "-s", "MODULARIZE=1",
            "-s", "EXPORTED_FUNCTIONS=$finalExports",
            "-s", "EXPORTED_RUNTIME_METHODS=$exportedRuntime",
            "-s", "ALLOW_MEMORY_GROWTH=1",
            "-s", "EXPORT_NAME='CapstoneModule'",
            "-s", "SINGLE_FILE=0",
            "-s", "NO_EXIT_RUNTIME=1"
        )

        project.providers.exec {
            commandLine(emccArgs)
            isIgnoreExitValue = false
        }.result.get()
    }

    // Step 5: Copy outputs to resource directory
    project.logger.lifecycle("Copying outputs for $targetName...")
    project.mkdir(outputDir)

    if (isWasi) {
        val wasmFile = File(distDir, "capstone-wasi.wasm")
        if (wasmFile.exists()) {
            project.copy {
                from(wasmFile)
                into(outputDir)
            }
            project.logger.lifecycle("✓ Copied capstone-wasi.wasm to ${File(outputDir, "capstone-wasi.wasm")}")
        } else {
            throw createException("WASI build failed. File not found: ${wasmFile.absolutePath}")
        }
    } else {
        val jsFile = File(distDir, "capstone.js")
        val wasmFile = File(distDir, "capstone.wasm")

        if (jsFile.exists() && wasmFile.exists()) {
            project.copy {
                from(jsFile, wasmFile)
                into(outputDir)
            }
            project.logger.lifecycle("✓ Copied capstone.js and capstone.wasm to $outputDir")
        } else {
            throw createException("Web build failed. Files not found: ${jsFile.absolutePath}, ${wasmFile.absolutePath}")
        }
    }

    project.logger.lifecycle("✓ Build Complete for $targetName!")
}

fun buildCapstoneForTarget(project: Project, targetName: String) {
    // Enable Android configs if NDK is available
    val enabledConfigs = CapstoneBuildConfigs.enableAndroidConfigs(project, CapstoneBuildConfigs.configs)

    val config = enabledConfigs[targetName] ?: return
    if (!config.enabled) {
        project.logger.warn("Skipping $targetName: build not enabled (missing toolchain or on incompatible host)")
        return
    }

    // WASM targets use a different build process
    if (targetName == "wasmJs" || targetName == "wasmWasi") {
        buildCapstoneWasm(project, targetName, config)
        return
    }

    val projectDir = project.rootProject.projectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")

    // Reuse logic: if configured to reuse, point to the source target's build/output
    val sourceTargetName = config.reuseOutputFrom ?: targetName
    val buildDir = File(projectDir, "build/capstone/$sourceTargetName")

    // Determine output directory based on target type
    val outputDir = when {
        // WASM WASI resources go to src/wasmWasiMain/resources/
        targetName == "wasmWasi" -> File(projectDir, "library/src/wasmWasiMain/resources")
        // WASM JS and JS resources go to src/webMain/resources/
        targetName == "wasmJs" -> File(projectDir, "library/src/webMain/resources")
        // Android shared libraries go to linked-android/<abi>/
        config.buildShared && CapstoneBuildConfigs.getAndroidAbi(targetName) != null -> {
            val abi = CapstoneBuildConfigs.getAndroidAbi(targetName)!!
            File(projectDir, "library/interop/linked-android/$abi")
        }
        // JVM shared libraries go to src/jvmMain/resources/libs/<classifier>/
        config.buildShared && CapstoneBuildConfigs.getJvmPlatformClassifier(targetName) != null -> {
            val classifier = CapstoneBuildConfigs.getJvmPlatformClassifier(targetName)!!
            File(projectDir, "library/src/jvmMain/resources/libs/$classifier")
        }
        // Default: static libraries go to interop/lib/<target>/
        else -> File(projectDir, "library/interop/lib/$targetName")
    }

    val includeDir = File(projectDir, "library/interop/include")
    
    // If reusing output and source hasn't been built yet, we should probably build the source first
    // usage of this function assumes build order or that the user calls buildCapstoneAll which does all.
    // If we are strictly reusing, we might skip the CMake build command for THIS target and just copy.
    
    if (config.reuseOutputFrom != null) {
        project.logger.lifecycle("Target $targetName is configured to reuse output from ${config.reuseOutputFrom}")
        
        // Ensure source target is built (recursively call? or assume existence? Recursion might be safer but circular dep risk)
        // Ideally, we assume the source target is either already built or will be built.
        // For robustness, let's check if source binary exists. If not, trigger build for source.
        val sourceConfig = CapstoneBuildConfigs.configs[config.reuseOutputFrom]
        if (sourceConfig != null) {
             // We can't easily recurse cleanly without passing state, but we can verify existence.
             // Given the context, we will attempt to find the source library.
             // If not found, we build the source target.
             val sourceExecName = "buildCapstone${config.reuseOutputFrom.replaceFirstChar { it.uppercase() }}"
             // In a task execution, we can't invoke another task easily.
             // We will assume the BuildAll task correctly orders or executes dependencies.
             // BUT, if we are running just for this target, we need the logic.
             // Better: explicitly run the build logic for the source target context if reuse
             
             // Simple approach: Run the build logic for the SOURCE target if we are reusing.
             // Then copy to our output.
             // But wait, if we run logic for source, it will output to source output dir.
             // We just need to copy from source output dir to our output dir.
             
             buildCapstoneForTarget(project, config.reuseOutputFrom)
             // After building source, continue to copy phase using source build dir
        }
    }

    // If NOT reusing (or after building source), check if we need to build
    // Note: If reusing, we already called buildCapstoneForTarget(source), so buildDir (set to source's build dir) is populated.
    
    // However, we skipped the check "if outputDir exists loop" logic for this target.
    
    if (outputDir.exists()) {
       val libFile = if (config.cmakeSystemName == "Windows") {
            if (config.buildShared) File(outputDir, "capstone.dll") else File(outputDir, "libcapstone.a")
       } else if (config.buildShared) {
             File(outputDir, "libcapstone.${if (config.cmakeSystemName == "Darwin") "dylib" else "so"}")
       } else {
            File(outputDir, "libcapstone.a")
       }

       if (libFile.exists()) {
           project.logger.lifecycle("Library already exists for $targetName at $libFile")
           return
       }
    }

    project.logger.lifecycle("Configuring Capstone for $targetName...")
    
    // If reusing, we SKIP the CMake configuration and build for THIS target,
    // beacuse we already built the source target above.
    if (config.reuseOutputFrom == null) {
        val cmakeConfigArgs = mutableListOf("cmake", "-S", capstoneSource.absolutePath, "-B", buildDir.absolutePath)
        
        // Set Build Type
        cmakeConfigArgs.add("-DCMAKE_BUILD_TYPE=Release")

        if (Toolchains.commandExists("ninja")) {
            cmakeConfigArgs.add("-GNinja")
        }
        
        // Disable CSTOOL
        cmakeConfigArgs.add("-DCAPSTONE_BUILD_CSTOOL=OFF")
        cmakeConfigArgs.add("-DCAPSTONE_BUILD_TESTS=OFF")
        
        // Position Independent Code (needed for shared libs / android)
        cmakeConfigArgs.add("-DCMAKE_POSITION_INDEPENDENT_CODE=ON")

        // Static vs Shared
        // Disable linker dependency tracking for all targets to avoid Zig/Ninja issues
        cmakeConfigArgs.add("-DCMAKE_C_LINK_DEPENDS_USE_COMPILER=FALSE")
        cmakeConfigArgs.add("-DCMAKE_CXX_LINK_DEPENDS_USE_COMPILER=FALSE")

        if (config.buildShared) {
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_STATIC_LIBS=OFF")
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_SHARED_LIBS=ON")
        } else {
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_STATIC_LIBS=ON")
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_SHARED_LIBS=OFF")
        }

        if (config.cmakeToolchainFile != null) {
            cmakeConfigArgs.add("-DCMAKE_TOOLCHAIN_FILE=${config.cmakeToolchainFile}")
        } else if (config.cCompiler == "zig") {
            // Auto-inject Zig toolchain file
            val toolchainFile = getZigToolchainFile(project)
            cmakeConfigArgs.add("-DCMAKE_TOOLCHAIN_FILE=${toolchainFile.absolutePath}")
            
            // For Windows Mingw with Zig, we need RC checking disabled or handled
            if (config.cmakeSystemName == "Windows") {
                cmakeConfigArgs.add("-DCMAKE_RC_COMPILER_INIT=windres")
            }
        }

        if (config.cmakeSystemName != null) {
            cmakeConfigArgs.add("-DCMAKE_SYSTEM_NAME=${config.cmakeSystemName}")
        }

        if (config.cmakeSystemProcessor != null) {
            cmakeConfigArgs.add("-DCMAKE_SYSTEM_PROCESSOR=${config.cmakeSystemProcessor}")
        }
        
        if (config.cCompiler != null) {
            cmakeConfigArgs.add("-DCMAKE_C_COMPILER=${config.cCompiler}")
        }
        
        if (config.cxxCompiler != null) {
            cmakeConfigArgs.add("-DCMAKE_CXX_COMPILER=${config.cxxCompiler}")
        }
        cmakeConfigArgs.add("-DCMAKE_LINK_DEPENDS_NO_SHARED=ON")

        // Handle architecture flags based on platform
        if (config.archFlags.isNotEmpty()) {
            val hasTargetTriple = config.archFlags.contains("-target")

            when {
                // Apple cross-compilation (iOS, watchOS, tvOS) uses -target triple
                // This includes configs with no system name (raw compiler flags) or Darwin/iOS/watchOS/tvOS system names
                hasTargetTriple -> {
                    // For cross-compilation with -target triple, pass all flags directly to CMAKE_C_FLAGS
                    // This includes -target, -isysroot, etc.
                    val flags = config.archFlags.joinToString(" ")
                    cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
                    cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
                }
                // Native macOS/Darwin uses CMAKE_OSX_ARCHITECTURES (no -target flag)
                config.cmakeSystemName == "Darwin" -> {
                    // No additional flags needed for native macOS
                }
                // Windows doesn't need arch flags
                config.cmakeSystemName == "Windows" -> {
                    // No additional flags needed
                }
                // Other platforms (Linux, Android native) add arch flags
                else -> {
                    val flags = config.archFlags.joinToString(" ")
                    cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
                    cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
                }
            }
        }

        cmakeConfigArgs.addAll(config.additionalCMakeArgs)

        project.mkdir(buildDir)
        
        project.providers.exec(Action<ExecSpec> {
            commandLine(cmakeConfigArgs)
            isIgnoreExitValue = false
        }).result.get()

        project.logger.lifecycle("Building Capstone for $targetName...")
        val buildTarget = if (config.buildShared) "capstone_shared" else "capstone_static"
        
        project.providers.exec(Action {
            commandLine("cmake", "--build", buildDir.absolutePath, "--config", "Release", "--parallel", "--target", buildTarget)
            isIgnoreExitValue = false
        }).result.get()
    } else {
         project.logger.lifecycle("Skipping build for $targetName (reusing output from ${config.reuseOutputFrom})")
    }

    project.logger.lifecycle("Copying outputs for $targetName...")
    project.mkdir(outputDir)

    val libExtension = when {
        config.cmakeSystemName == "Windows" && config.buildShared -> "dll"
        config.cmakeSystemName == "Windows" -> "a"
        config.buildShared && config.cmakeSystemName == "Darwin" -> "dylib"
        config.buildShared -> "so"
        else -> "a" // Default to static library
    }

    val expectedLibName = when (config.cmakeSystemName) {
        "Windows" if config.buildShared -> "capstone.dll"
        "Windows" -> "libcapstone.a"
        null -> "libcapstone.$libExtension" // No system name (e.g., iOS cross-compilation)
        else -> "libcapstone.$libExtension"
    }

    val builtLibFile = project.fileTree(buildDir) {
        include("**/*.$libExtension*")
        exclude("**/*.cmake", "**/*.pc")
    }.files.firstOrNull { file ->
        if (config.buildShared) {
             file.name.contains("capstone") && file.name.contains(".$libExtension") && !file.name.endsWith(".a") 
        } else {
             file.name == expectedLibName || file.name == "capstone.lib" // Just in case msvc usage
        }
    }

    if (builtLibFile != null && builtLibFile.exists()) {
        project.copy {
            from(builtLibFile)
            into(outputDir)
            rename { expectedLibName } // Always rename to the expected name (e.g. libcapstone.dylib)
        }
        project.logger.lifecycle("✓ Copied ${builtLibFile.name} to ${File(outputDir, expectedLibName)}")
    } else {
         project.logger.warn("⚠️ Could not find built library file in $buildDir")
         throw createException("Failed to find built library for $targetName")
    }
}

private fun getZigToolchainFile(project: Project): File {
    val toolchainFile = File(project.layout.buildDirectory.get().asFile, "toolchains/zig-toolchain.cmake")
    if (!toolchainFile.exists()) {
        toolchainFile.parentFile.mkdirs()
        toolchainFile.writeText("""
            set(CMAKE_C_COMPILER "zig")
            set(CMAKE_CXX_COMPILER "zig")
            set(CMAKE_C_COMPILER_ARG1 "cc")
            set(CMAKE_CXX_COMPILER_ARG1 "c++")
            set(CMAKE_AR "zig" CACHE STRING "" FORCE)
            set(CMAKE_RANLIB "zig" CACHE STRING "" FORCE)
            set(CMAKE_C_COMPILER_AR "zig" CACHE STRING "" FORCE)
            set(CMAKE_CXX_COMPILER_AR "zig" CACHE STRING "" FORCE)
            
            # Fix for Zig + Ninja/Make dependency file issue by suppressing the flags
            set(CMAKE_DEPFILE_FLAGS_C "" CACHE STRING "" FORCE)
            set(CMAKE_DEPFILE_FLAGS_CXX "" CACHE STRING "" FORCE)
            set(CMAKE_C_LINK_DEPENDS_USE_COMPILER FALSE CACHE BOOL "" FORCE)
            set(CMAKE_CXX_LINK_DEPENDS_USE_COMPILER FALSE CACHE BOOL "" FORCE)
            set(CMAKE_LINK_DEPENDS_NO_SHARED ON CACHE BOOL "" FORCE)
        """.trimIndent())
    }
    return toolchainFile
}

fun buildCapstoneForAllTargets(project: Project, targets: Collection<KotlinNativeTarget>) {
    val targetNames = targets.map { it.targetName }.toSet()

    // Also build shared libs for host if applicable
    val extraTargets = mutableListOf<String>()

    // JVM shared libraries
    if (Host.isMac) {
         if (targetNames.contains("macosX64")) extraTargets.add("macosX64Shared")
         if (targetNames.contains("macosArm64")) extraTargets.add("macosArm64Shared")
    }
    if (Toolchains.linuxX64OnMac || Toolchains.nativeLinux) {
         if (targetNames.contains("linuxX64")) extraTargets.add("linuxX64")
         if (targetNames.contains("linuxArm64")) extraTargets.add("linuxArm64")
        if (targetNames.contains("linuxX64")) extraTargets.add("linuxX64Shared")
        if (targetNames.contains("linuxArm64")) extraTargets.add("linuxArm64Shared")
        extraTargets.add("linuxX86Shared")
//        if (Toolchains.linuxArm32) extraTargets.add("linuxArm32Shared")
    }
    if (Toolchains.mingwX64 || Toolchains.mingwX86) {
         if (targetNames.contains("mingwX64")) extraTargets.add("mingwX64Shared")
         if (Toolchains.mingwX86) extraTargets.add("mingwX86Shared")
    }

    // Android shared libraries (for Android runtime)
    if (targetNames.contains("androidNativeArm64")) extraTargets.add("androidArm64Shared")
    if (targetNames.contains("androidNativeArm32")) extraTargets.add("androidArm32Shared")
    if (targetNames.contains("androidNativeX64")) extraTargets.add("androidX64Shared")
    if (targetNames.contains("androidNativeX86")) extraTargets.add("androidX86Shared")

    // WASM targets (if Emscripten is available)
    if (Toolchains.hasEmscripten) {
        extraTargets.add("wasmJs")
        extraTargets.add("wasmWasi")
    }

    targets.forEach { target ->
        buildCapstoneForTarget(project, target.targetName)
    }

    extraTargets.forEach { targetName ->
        buildCapstoneForTarget(project, targetName)
    }
}

fun createException(message: String): RuntimeException {
    return RuntimeException(message)
}
