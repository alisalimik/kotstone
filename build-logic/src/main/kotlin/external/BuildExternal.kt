package external


import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import platform.Host
import platform.toolchains
import java.io.File


/**
 * Configuration cache compatible version of buildCapstoneWasm
 */
fun buildCapstoneWasmFromContext(buildContext: BuildContext, targetName: String) {
    val projectDir = buildContext.rootProjectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")
    val workDir = File(buildContext.buildDirectory, "capstone_wasm_build/$targetName")
    val sourceDir = File(workDir, "source")  // Where we'll put the wrapper CMakeLists.txt
    val buildDir = File(workDir, "build")    // Separate build directory
    val distDir = File(workDir, "dist")

    val outputDir = when (targetName) {
        "wasmWasi" -> File(projectDir, "library/src/wasmWasiMain/resources")
        "wasmJs" -> File(projectDir, "library/src/webMain/resources")
        else -> File(projectDir, "library/src/webMain/resources")
    }

    val isWasi = targetName == "wasmWasi"

    val outputFile = if (isWasi) {
        File(outputDir, "capstone-wasi.wasm")
    } else {
        File(outputDir, "capstone.wasm")
    }

    if (outputFile.exists()) {
        buildContext.logger.lifecycle("WASM library already exists for $targetName at $outputFile")
        return
    }

    buildContext.logger.lifecycle("Building Capstone WASM for $targetName...")

    val toolchainFile = buildContext.emscriptenToolchainFile
    if (toolchainFile.isEmpty()) {
        buildContext.logger.error("Could not find Emscripten.cmake toolchain file")
        throw createException("Emscripten toolchain not found")
    }

    val emRoot = buildContext.emscriptenRoot
    if (emRoot == null || !emRoot.exists()) {
        buildContext.logger.error("Could not find Emscripten root")
        throw createException("Emscripten root not found")
    }

    val emcc = File(emRoot, "emcc").absolutePath
    val emcpp = File(emRoot, "em++").absolutePath

    buildContext.logger.lifecycle("Configuring CMake for WebAssembly static library...")
    sourceDir.mkdirs()
    buildDir.mkdirs()

    // Create a wrapper CMakeLists.txt to avoid PROJECT_IS_TOP_LEVEL being true for Capstone
    // This prevents CPackConfig.txt from being included, which causes errors in some environments
    val wrapperCMakeFile = File(sourceDir, "CMakeLists.txt")
    wrapperCMakeFile.writeText("""
        cmake_minimum_required(VERSION 3.15)
        project(CapstoneWasmWrapper)

        # Set options to disable unwanted features
        set(CAPSTONE_BUILD_SHARED_LIBS OFF CACHE BOOL "" FORCE)
        set(CAPSTONE_BUILD_STATIC_LIBS ON CACHE BOOL "" FORCE)
        set(CAPSTONE_BUILD_CSTOOL OFF CACHE BOOL "" FORCE)
        set(CAPSTONE_INSTALL OFF CACHE BOOL "" FORCE)
        set(CAPSTONE_ARCHITECTURE_DEFAULT ON CACHE BOOL "" FORCE)

        add_subdirectory("${capstoneSource.absolutePath}" capstone)
    """.trimIndent())

    val cmakeConfigArgs = listOf(
        "cmake",
        "-S", sourceDir.absolutePath,  // Source directory (where wrapper CMakeLists.txt is)
        "-B", buildDir.absolutePath,   // Build directory (separate from source)
        "-DCMAKE_TOOLCHAIN_FILE=$toolchainFile",
        "-DCMAKE_C_COMPILER=$emcc",
        "-DCMAKE_CXX_COMPILER=$emcpp",
        "-DCMAKE_BUILD_TYPE=Release"
    )

    buildContext.execOperations.exec {
        commandLine(cmakeConfigArgs)
        isIgnoreExitValue = false
    }

    buildContext.logger.lifecycle("Building libcapstone.a...")
    val makeJobs = Runtime.getRuntime().availableProcessors()

    buildContext.execOperations.exec {
        commandLine("cmake", "--build", buildDir.absolutePath, "-j$makeJobs")
        isIgnoreExitValue = false
    }

    // Since we are using add_subdirectory, the static lib is in the subdirectory
    val staticLib = File(buildDir, "capstone/libcapstone.a")
    if (!staticLib.exists()) {
        throw createException("Static library build failed. File not found: ${staticLib.absolutePath}")
    }

    // Step 3: Generate export list from compiled library
    buildContext.logger.lifecycle("Generating exported function list from compiled library...")
    val llvmNm = buildContext.llvmNmPath
    if (llvmNm.isEmpty()) {
        throw createException("llvm-nm not found. Cannot extract symbols.")
    }

    buildContext.execOperations.exec {
        commandLine(llvmNm, staticLib.absolutePath)
        isIgnoreExitValue = false
    }

    val symbolsOutput = java.io.ByteArrayOutputStream()
    buildContext.execOperations.exec {
        commandLine(llvmNm, staticLib.absolutePath)
        standardOutput = symbolsOutput
        isIgnoreExitValue = false
    }

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
    buildContext.logger.lifecycle("Found $funcCount capstone functions to export.")

    val systemFuncs = "'_malloc','_free'"
    val finalExports = "[$systemFuncs,$capstoneFuncs]"

    distDir.mkdirs()

    if (isWasi) {
        buildContext.logger.lifecycle("Linking WASI module...")

        val emccArgs = listOf(
            emcc, staticLib.absolutePath,
            "-o", File(distDir, "capstone-wasi.wasm").absolutePath,
            "-O3",
            "-s", "WASM=1",
            "-s", "STANDALONE_WASM=1",
            "-s", "EXPORTED_FUNCTIONS=$finalExports",
            "--no-entry",
            "-s", "EXPORTED_RUNTIME_METHODS=[]",
            "-D_WASI_EMULATED_MMAN"
        )

        buildContext.execOperations.exec {
            commandLine(emccArgs)
            isIgnoreExitValue = false
        }
    } else {
        // Web Module (JS/Wasm)
        buildContext.logger.lifecycle("Linking Web (JS/Wasm) module...")

        val exportedRuntime = "['ccall','cwrap','getValue','setValue','UTF8ToString','writeArrayToMemory','addFunction','removeFunction']"

        val emccArgs = listOf(
            emcc, staticLib.absolutePath,
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

        buildContext.execOperations.exec {
            commandLine(emccArgs)
            isIgnoreExitValue = false
        }
    }

    // Step 5: Copy outputs to resource directory
    buildContext.logger.lifecycle("Copying outputs for $targetName...")
    outputDir.mkdirs()

    if (isWasi) {
        val wasmFile = File(distDir, "capstone-wasi.wasm")
        if (wasmFile.exists()) {
            buildContext.fileSystemOperations.copy {
                from(wasmFile)
                into(outputDir)
            }
            buildContext.logger.lifecycle("✓ Copied capstone-wasi.wasm to ${File(outputDir, "capstone-wasi.wasm")}")
        } else {
            throw createException("WASI build failed. File not found: ${wasmFile.absolutePath}")
        }
    } else {
        val jsFile = File(distDir, "capstone.js")
        val wasmFile = File(distDir, "capstone.wasm")

        if (jsFile.exists() && wasmFile.exists()) {
            buildContext.fileSystemOperations.copy {
                from(jsFile, wasmFile)
                into(outputDir)
            }
            buildContext.logger.lifecycle("✓ Copied capstone.js and capstone.wasm to $outputDir")
        } else {
            throw createException("Web build failed. Files not found: ${jsFile.absolutePath}, ${wasmFile.absolutePath}")
        }
    }

    buildContext.logger.lifecycle("✓ Build Complete for $targetName!")
}

fun buildCapstoneWasm(project: Project, targetName: String) {
    val projectDir = project.rootProject.projectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")
    val workDir = File(projectDir, "external/capstone_wasm_build")
    val buildDir = File(workDir, "external")
    val distDir = File(workDir, "dist")

    val outputDir = when (targetName) {
        "wasmWasi" -> File(projectDir, "library/src/wasmWasiMain/resources")
        "wasmJs" -> File(projectDir, "library/src/webMain/resources")
        else -> File(projectDir, "library/src/webMain/resources")
    }

    val isWasi = targetName == "wasmWasi"

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

    val toolchainFile = project.toolchains.findEmscriptenToolchain().get()
    if (toolchainFile.isEmpty()) {
        project.logger.error("Could not find Emscripten.cmake toolchain file")
        throw createException("Emscripten toolchain not found")
    }

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
    val llvmNm = project.toolchains.getLlvmNm().get()
    if (llvmNm.isEmpty()) {
        throw createException("llvm-nm not found. Cannot extract symbols.")
    }

    project.providers.exec {
        commandLine(llvmNm, staticLib.absolutePath)
        isIgnoreExitValue = false
    }.result.get().assertNormalExitValue()

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

    val systemFuncs = "'_malloc','_free'"
    val finalExports = "[$systemFuncs,$capstoneFuncs]"

    project.mkdir(distDir)

    if (isWasi) {
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
    val enabledConfigs = CapstoneBuildConfigs.enableAndroidConfigs(project, CapstoneBuildConfigs.getConfigs(project))

    val config = enabledConfigs[targetName] ?: return
    if (!config.enabled) {
        project.logger.warn("Skipping $targetName: build not enabled (missing toolchain or on incompatible host)")
        return
    }

    // WASM targets use a different build process
    if (targetName == "wasmJs" || targetName == "wasmWasi") {
        buildCapstoneWasm(project, targetName)
        return
    }

    val projectDir = project.rootProject.projectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")

    // Reuse logic: if configured to reuse, point to the source target's build/output
    val sourceTargetName = config.reuseOutputFrom ?: targetName
    val buildDir = File(projectDir, "build/capstone/$sourceTargetName")

    val outputDir = when {
        config.buildShared && CapstoneBuildConfigs.getAndroidAbi(targetName) != null -> {
            val abi = CapstoneBuildConfigs.getAndroidAbi(targetName)!!
            File(projectDir, "library/interop/linked-android/$abi")
        }
        config.buildShared && CapstoneBuildConfigs.getJvmPlatformClassifier(targetName) != null -> {
            val classifier = CapstoneBuildConfigs.getJvmPlatformClassifier(targetName)!!
            File(projectDir, "library/src/jvmMain/resources/libs/$classifier")
        }
        else -> File(projectDir, "library/interop/lib/$targetName")
    }

    if (config.reuseOutputFrom != null) {
        project.logger.lifecycle("Target $targetName is configured to reuse output from ${config.reuseOutputFrom}")

        val sourceConfig = CapstoneBuildConfigs.getConfigs(project)[config.reuseOutputFrom]
        if (sourceConfig != null) {
            buildCapstoneForTarget(project, config.reuseOutputFrom)
        }
    }

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

    if (config.reuseOutputFrom == null) {
        val cmakeConfigArgs = mutableListOf("cmake", "-S", capstoneSource.absolutePath, "-B", buildDir.absolutePath)

        cmakeConfigArgs.add("-DCMAKE_BUILD_TYPE=Release")

        if (project.toolchains.commandExists("ninja").get()) {
            cmakeConfigArgs.add("-GNinja")
        }

        cmakeConfigArgs.add("-DCAPSTONE_BUILD_CSTOOL=OFF")
        cmakeConfigArgs.add("-DCAPSTONE_BUILD_TESTS=OFF")

        cmakeConfigArgs.add("-DCMAKE_POSITION_INDEPENDENT_CODE=ON")

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
            val toolchainFile = getZigToolchainFile(project)
            cmakeConfigArgs.add("-DCMAKE_TOOLCHAIN_FILE=${toolchainFile.absolutePath}")

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

        if (config.archFlags.isNotEmpty()) {
            val hasTargetTriple = config.archFlags.contains("-target")

            when {
                hasTargetTriple -> {
                    val flags = config.archFlags.joinToString(" ")
                    cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
                    cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
                }
                config.cmakeSystemName == "Darwin" -> {
                }
                config.cmakeSystemName == "Windows" -> {
                }
                else -> {
                    val flags = config.archFlags.joinToString(" ")
                    cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
                    cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
                }
            }
        }

        cmakeConfigArgs.addAll(config.additionalCMakeArgs)

        project.mkdir(buildDir)

        project.providers.exec(Action {
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
        else -> "a"
    }

    val expectedLibName = when (config.cmakeSystemName) {
        "Windows" if config.buildShared -> "capstone.dll"
        "Windows" -> "libcapstone.a"
        null -> "libcapstone.$libExtension"
        else -> "libcapstone.$libExtension"
    }

    val builtLibFile = project.fileTree(buildDir) {
        include("**/*.$libExtension*")
        exclude("**/*.cmake", "**/*.pc")
    }.files.firstOrNull { file ->
        if (config.buildShared) {
            file.name.contains("capstone") && file.name.contains(".$libExtension") && !file.name.endsWith(".a")
        } else {
            file.name == expectedLibName || file.name == "capstone.lib"
        }
    }

    if (builtLibFile != null && builtLibFile.exists()) {
        project.copy {
            from(builtLibFile)
            into(outputDir)
            rename { expectedLibName }
        }
        project.logger.lifecycle("✓ Copied ${builtLibFile.name} to ${File(outputDir, expectedLibName)}")
    } else {
        project.logger.warn("⚠️ Could not find built library file in $buildDir")
        throw createException("Failed to find built library for $targetName")
    }
}

/**
 * Configuration cache compatible version of buildCapstoneForTarget that uses BuildContext
 * instead of Project
 */
fun buildCapstoneForTarget(buildContext: BuildContext, targetName: String) {
    // Get configs at execution time (they depend on system state)
    val enabledConfigs = CapstoneBuildConfigs.getConfigsFromContext(buildContext)

    val config = enabledConfigs[targetName] ?: return
    if (!config.enabled) {
        buildContext.logger.warn("Skipping $targetName: build not enabled (missing toolchain or on incompatible host)")
        return
    }

    // WASM targets use a different build process
    if (targetName == "wasmJs" || targetName == "wasmWasi") {
        buildCapstoneWasmFromContext(buildContext, targetName)
        return
    }

    val projectDir = buildContext.rootProjectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")

    // Reuse logic: if configured to reuse, point to the source target's build/output
    val sourceTargetName = config.reuseOutputFrom ?: targetName
    val buildDir = File(projectDir, "build/capstone/$sourceTargetName")

    val outputDir = when {
        config.buildShared && CapstoneBuildConfigs.getAndroidAbi(targetName) != null -> {
            val abi = CapstoneBuildConfigs.getAndroidAbi(targetName)!!
            File(projectDir, "library/interop/linked-android/$abi")
        }
        config.buildShared && CapstoneBuildConfigs.getJvmPlatformClassifier(targetName) != null -> {
            val classifier = CapstoneBuildConfigs.getJvmPlatformClassifier(targetName)!!
            File(projectDir, "library/src/jvmMain/resources/libs/$classifier")
        }
        else -> File(projectDir, "library/interop/lib/$targetName")
    }

    if (config.reuseOutputFrom != null) {
        buildContext.logger.lifecycle("Target $targetName is configured to reuse output from ${config.reuseOutputFrom}")

        val sourceConfig = enabledConfigs[config.reuseOutputFrom]
        if (sourceConfig != null) {
            buildCapstoneForTarget(buildContext, config.reuseOutputFrom)
        }
    }

    if (outputDir.exists()) {
        val libFile = if (config.cmakeSystemName == "Windows") {
            if (config.buildShared) File(outputDir, "capstone.dll") else File(outputDir, "libcapstone.a")
        } else if (config.buildShared) {
            File(outputDir, "libcapstone.${if (config.cmakeSystemName == "Darwin") "dylib" else "so"}")
        } else {
            File(outputDir, "libcapstone.a")
        }

        if (libFile.exists()) {
            buildContext.logger.lifecycle("Library already exists for $targetName at $libFile")
            return
        }
    }

    buildContext.logger.lifecycle("Configuring Capstone for $targetName...")

    if (config.reuseOutputFrom == null) {
        val cmakeConfigArgs = mutableListOf("cmake", "-S", capstoneSource.absolutePath, "-B", buildDir.absolutePath)

        cmakeConfigArgs.add("-DCMAKE_BUILD_TYPE=Release")

        if (buildContext.hasNinja) {
            cmakeConfigArgs.add("-GNinja")
        }

        cmakeConfigArgs.add("-DCAPSTONE_BUILD_CSTOOL=OFF")
        cmakeConfigArgs.add("-DCAPSTONE_BUILD_TESTS=OFF")

        cmakeConfigArgs.add("-DCMAKE_POSITION_INDEPENDENT_CODE=ON")

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
            val toolchainFile = getZigToolchainFile(buildContext.buildDirectory)
            cmakeConfigArgs.add("-DCMAKE_TOOLCHAIN_FILE=${toolchainFile.absolutePath}")

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

        if (config.archFlags.isNotEmpty()) {
            val hasTargetTriple = config.archFlags.contains("-target")

            when {
                hasTargetTriple -> {
                    val flags = config.archFlags.joinToString(" ")
                    cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
                    cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
                }
                config.cmakeSystemName == "Darwin" -> {
                }
                config.cmakeSystemName == "Windows" -> {
                }
                else -> {
                    val flags = config.archFlags.joinToString(" ")
                    cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
                    cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
                }
            }
        }

        cmakeConfigArgs.addAll(config.additionalCMakeArgs)

        buildDir.mkdirs()

        buildContext.execOperations.exec {
            commandLine(cmakeConfigArgs)
            isIgnoreExitValue = false
        }

        buildContext.logger.lifecycle("Building Capstone for $targetName...")
        val buildTarget = if (config.buildShared) "capstone_shared" else "capstone_static"

        buildContext.execOperations.exec {
            commandLine("cmake", "--build", buildDir.absolutePath, "--config", "Release", "--parallel", "--target", buildTarget)
            isIgnoreExitValue = false
        }
    } else {
        buildContext.logger.lifecycle("Skipping build for $targetName (reusing output from ${config.reuseOutputFrom})")
    }

    buildContext.logger.lifecycle("Copying outputs for $targetName...")
    outputDir.mkdirs()

    val libExtension = when {
        config.cmakeSystemName == "Windows" && config.buildShared -> "dll"
        config.cmakeSystemName == "Windows" -> "a"
        config.buildShared && config.cmakeSystemName == "Darwin" -> "dylib"
        config.buildShared -> "so"
        else -> "a"
    }

    val expectedLibName = when (config.cmakeSystemName) {
        "Windows" if config.buildShared -> "capstone.dll"
        "Windows" -> "libcapstone.a"
        null -> "libcapstone.$libExtension"
        else -> "libcapstone.$libExtension"
    }

    // Find the built library file
    val builtLibFile = buildDir.walk()
        .filter { it.isFile && it.extension == libExtension || it.name.endsWith(".$libExtension") }
        .filter { !it.path.contains(".cmake") && !it.name.endsWith(".pc") }
        .firstOrNull { file ->
            if (config.buildShared) {
                file.name.contains("capstone") && file.name.contains(".$libExtension") && !file.name.endsWith(".a")
            } else {
                file.name == expectedLibName || file.name == "capstone.lib"
            }
        }

    if (builtLibFile != null && builtLibFile.exists()) {
        buildContext.fileSystemOperations.copy {
            from(builtLibFile)
            into(outputDir)
            rename { expectedLibName }
        }
        buildContext.logger.lifecycle("✓ Copied ${builtLibFile.name} to ${File(outputDir, expectedLibName)}")
    } else {
        buildContext.logger.warn("⚠️ Could not find built library file in $buildDir")
        throw createException("Failed to find built library for $targetName")
    }
}