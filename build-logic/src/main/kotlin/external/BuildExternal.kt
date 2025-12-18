package external

import java.io.File

/**
 * Defines the specific Capstone architectures to build.
 * Filters: x86, ARM, AARCH64, and WASM.
 * All others are disabled to reduce binary size and compilation time.
 */
private fun getTargetArchFlags(): List<String> = listOf(
    "-DCAPSTONE_ARCHITECTURE_DEFAULT=0",
    "-DCAPSTONE_X86_SUPPORT=1",
    "-DCAPSTONE_ARM_SUPPORT=1",
    "-DCAPSTONE_AARCH64_SUPPORT=1",
    "-DCAPSTONE_WASM_SUPPORT=1",
)

/**
 * Builds the Capstone library for WebAssembly targets (wasmJs, wasmWasi).
 * Uses Emscripten toolchain via CMake.
 */
fun buildCapstoneWasmFromContext(buildContext: BuildContext, targetName: String) {
    val projectDir = buildContext.rootProjectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")
    val workDir = File(buildContext.buildDirectory, "capstone_wasm_build/$targetName")
    val sourceDir = File(workDir, "source")
    val buildDir = File(workDir, "build")
    val distDir = File(workDir, "dist")

    // Determine output location based on target
    val isWasi = targetName == "wasmWasi"
    val outputDir = File(projectDir, if (isWasi) "library/src/wasmWasiMain/resources" else "library/src/webMain/resources")
    val outputFile = File(outputDir, if (isWasi) "capstone-wasi.wasm" else "capstone.wasm")

    buildContext.logger.lifecycle("Building Capstone WASM for $targetName...")

    // Validate Toolchain
    val toolchainFile = buildContext.emscriptenToolchainFile
    val emRoot = buildContext.emscriptenRoot
    if (toolchainFile.isEmpty() || emRoot == null || !emRoot.exists()) {
        throw createException("Emscripten toolchain or root not found")
    }

    val emcc = File(emRoot, "emcc").absolutePath
    val emcpp = File(emRoot, "em++").absolutePath

    // 1. Configure CMake
    buildContext.logger.info("Configuring CMake for WebAssembly static library...")
    sourceDir.mkdirs()
    buildDir.mkdirs()

    // Wrapper to prevent top-level project pollution
    File(sourceDir, "CMakeLists.txt").writeText("""
        cmake_minimum_required(VERSION 3.15)
        project(CapstoneWasmWrapper)
        add_subdirectory("${capstoneSource.absolutePath}" capstone)
    """.trimIndent())

    val cmakeConfigArgs = mutableListOf(
        "cmake",
        "--no-warn-unused-cli",
        "-S", sourceDir.absolutePath,
        "-B", buildDir.absolutePath,
        "-DCMAKE_TOOLCHAIN_FILE=$toolchainFile",
        "-DCMAKE_C_COMPILER=$emcc",
        "-DCMAKE_CXX_COMPILER=$emcpp",
        "-DCMAKE_BUILD_TYPE=Release",
        "-DCAPSTONE_BUILD_SHARED_LIBS=OFF",
        "-DCAPSTONE_BUILD_STATIC_LIBS=ON",
        "-DCAPSTONE_BUILD_CSTOOL=OFF",
        "-DCAPSTONE_INSTALL=OFF"
    )
    cmakeConfigArgs.addAll(getTargetArchFlags())

    buildContext.execOperations.exec {
        commandLine(cmakeConfigArgs)
        isIgnoreExitValue = false
    }

    // 2. Build Static Library
    buildContext.logger.info("Building libcapstone.a...")
    val makeJobs = Runtime.getRuntime().availableProcessors()

    buildContext.execOperations.exec {
        commandLine("cmake", "--build", buildDir.absolutePath, "-j$makeJobs")
        isIgnoreExitValue = false
    }

    val staticLib = File(buildDir, "capstone/libcapstone.a")
    if (!staticLib.exists()) {
        throw createException("Static library build failed. Not found: ${staticLib.absolutePath}")
    }

    // 3. Generate Export List (Symbols)
    buildContext.logger.info("Extracting exported symbols...")
    val llvmNm = buildContext.llvmNmPath
    if (llvmNm.isEmpty()) throw createException("llvm-nm not found.")

    val symbolsOutput = java.io.ByteArrayOutputStream()
    buildContext.execOperations.exec {
        commandLine(llvmNm, staticLib.absolutePath)
        standardOutput = symbolsOutput
        isIgnoreExitValue = false
    }

    val capstoneFuncs = symbolsOutput.toString()
        .lines()
        .filter { it.contains(" T ") }
        .mapNotNull { "\\bcs_[a-zA-Z0-9_]*".toRegex().find(it)?.value }
        .distinct()
        .sorted()
        .joinToString(",") { "'_$it'" }

    if (capstoneFuncs.isEmpty()) throw createException("No functions found to export!")

    val finalExports = "['_malloc','_free',$capstoneFuncs]"

    // 4. Link Final Module
    distDir.mkdirs()
    buildContext.logger.info("Linking ${if (isWasi) "WASI" else "Web"} module...")

    val linkArgs = mutableListOf(
        emcc, staticLib.absolutePath,
        "-O3",
        "-s", "WASM=1",
        "-s", "EXPORTED_FUNCTIONS=$finalExports"
    )

    if (isWasi) {
        linkArgs.addAll(listOf(
            "-o", File(distDir, "capstone-wasi.wasm").absolutePath,
            "-s", "STANDALONE_WASM=1",
            "--no-entry",
            "-s", "EXPORTED_RUNTIME_METHODS=[]",
            "-D_WASI_EMULATED_MMAN"
        ))
    } else {
        val exportedRuntime = "['ccall','cwrap','getValue','setValue','UTF8ToString','writeArrayToMemory','addFunction','removeFunction']"
        linkArgs.addAll(listOf(
            "-o", File(distDir, "capstone.js").absolutePath,
            "-s", "MODULARIZE=1",
            "-s", "EXPORTED_RUNTIME_METHODS=$exportedRuntime",
            "-s", "ALLOW_MEMORY_GROWTH=1",
            "-s", "EXPORT_NAME='CapstoneModule'",
            "-s", "SINGLE_FILE=0",
            "-s", "NO_EXIT_RUNTIME=1"
        ))
    }

    buildContext.execOperations.exec {
        commandLine(linkArgs)
        isIgnoreExitValue = false
    }

    // 5. Copy Outputs
    buildContext.logger.info("Copying outputs...")
    outputDir.mkdirs()

    val filesToCopy = if (isWasi) {
        listOf(File(distDir, "capstone-wasi.wasm"))
    } else {
        listOf(File(distDir, "capstone.js"), File(distDir, "capstone.wasm"))
    }

    if (filesToCopy.all { it.exists() }) {
        buildContext.fileSystemOperations.copy {
            from(filesToCopy)
            into(outputDir)
        }
        buildContext.logger.lifecycle("✓ Build Complete for $targetName!")
    } else {
        throw createException("Build failed. Missing output files in ${distDir.absolutePath}")
    }
}

/**
 * Builds the Capstone library for Native targets (Android, Linux, Windows, macOS).
 * Configuration cache compatible.
 */
fun buildCapstoneForTarget(buildContext: BuildContext, targetName: String) {
    val enabledConfigs = CapstoneBuildConfigs.getConfigsFromContext(buildContext)
    val config = enabledConfigs[targetName] ?: return

    if (!config.enabled) {
        buildContext.logger.warn("Skipping $targetName: build disabled.")
        return
    }

    // Redirect WASM targets
    if (targetName == "wasmJs" || targetName == "wasmWasi") {
        buildCapstoneWasmFromContext(buildContext, targetName)
        return
    }

    val projectDir = buildContext.rootProjectDir
    val capstoneSource = File(projectDir, "library/interop/capstone")

    // Check Reuse Logic (e.g., Android reusing libs)
    if (config.reuseOutputFrom != null) {
        buildContext.logger.lifecycle("Target $targetName reusing output from ${config.reuseOutputFrom}")
        val sourceConfig = enabledConfigs[config.reuseOutputFrom]
        if (sourceConfig != null) {
            // Recursively ensure source is built
            buildCapstoneForTarget(buildContext, config.reuseOutputFrom)
        }
    }

    val sourceTargetName = config.reuseOutputFrom ?: targetName
    val buildDir = File(projectDir, "build/capstone/$sourceTargetName")

    // Determine Output Directory
    val outputDir = when {
        config.buildShared && CapstoneBuildConfigs.getAndroidAbi(targetName) != null ->
            File(projectDir, "library/interop/linked-android/${CapstoneBuildConfigs.getAndroidAbi(targetName)}")

        config.buildShared && CapstoneBuildConfigs.getJvmPlatformClassifier(targetName) != null ->
            File(projectDir, "library/src/jvmMain/resources/libs/${CapstoneBuildConfigs.getJvmPlatformClassifier(targetName)}")

        else -> File(projectDir, "library/interop/lib/$targetName")
    }

    // CMake Configuration
    buildContext.logger.info("Configuring Capstone for $targetName...")

    if (config.reuseOutputFrom == null) {
        val cmakeConfigArgs = mutableListOf(
            "cmake",
            "--no-warn-unused-cli",
            "-S", capstoneSource.absolutePath, "-B", buildDir.absolutePath,
            "-DCMAKE_BUILD_TYPE=Release",
            "-DCAPSTONE_BUILD_CSTOOL=OFF",
            "-DCAPSTONE_BUILD_TESTS=OFF",
            "-DCMAKE_POSITION_INDEPENDENT_CODE=ON",
//            "-DCMAKE_C_LINK_DEPENDS_USE_COMPILER=FALSE",
//            "-DCMAKE_CXX_LINK_DEPENDS_USE_COMPILER=FALSE",
            "-DCMAKE_LINK_DEPENDS_NO_SHARED=ON"
        )

        if (buildContext.hasNinja) cmakeConfigArgs.add("-GNinja")

        // Library Type
        if (config.buildShared) {
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_STATIC_LIBS=OFF")
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_SHARED_LIBS=ON")
        } else {
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_STATIC_LIBS=ON")
            cmakeConfigArgs.add("-DCAPSTONE_BUILD_SHARED_LIBS=OFF")
        }

        // Apply Architecture Limits
        cmakeConfigArgs.addAll(getTargetArchFlags())

        // Toolchain & Compiler
        if (config.cmakeToolchainFile != null) {
            cmakeConfigArgs.add("-DCMAKE_TOOLCHAIN_FILE=${config.cmakeToolchainFile}")
        } else if (config.cCompiler == "zig") {
            val toolchainFile = getZigToolchainFile(buildContext.buildDirectory)
            cmakeConfigArgs.add("-DCMAKE_TOOLCHAIN_FILE=${toolchainFile.absolutePath}")
            if (config.cmakeSystemName == "Windows") {
                cmakeConfigArgs.add("-DCMAKE_RC_COMPILER_INIT=windres")
            }
        }

        if (config.cmakeSystemName != null) cmakeConfigArgs.add("-DCMAKE_SYSTEM_NAME=${config.cmakeSystemName}")
        if (config.cmakeSystemProcessor != null) cmakeConfigArgs.add("-DCMAKE_SYSTEM_PROCESSOR=${config.cmakeSystemProcessor}")
        if (config.cCompiler != null) cmakeConfigArgs.add("-DCMAKE_C_COMPILER=${config.cCompiler}")
        if (config.cxxCompiler != null) cmakeConfigArgs.add("-DCMAKE_CXX_COMPILER=${config.cxxCompiler}")

        // Target flags
        if (config.archFlags.isNotEmpty()) {
            val flags = config.archFlags.joinToString(" ")
            cmakeConfigArgs.add("-DCMAKE_C_FLAGS=$flags")
            cmakeConfigArgs.add("-DCMAKE_CXX_FLAGS=$flags")
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
        buildContext.logger.lifecycle("Skipping build for $targetName (reusing output)")
    }

    // Copying Outputs
    outputDir.mkdirs()

    val libExtension = when {
        config.cmakeSystemName == "Windows" && config.buildShared -> "dll"
        config.cmakeSystemName == "Windows" -> "a"
        config.buildShared && config.cmakeSystemName == "Darwin" -> "dylib"
        config.buildShared -> "so"
        else -> "a"
    }

    val expectedLibName = if (config.cmakeSystemName == "Windows" && config.buildShared) "capstone.dll" else "libcapstone.$libExtension"

    val builtLibFile = buildDir.walkTopDown()
        .filter { it.isFile && (it.name == expectedLibName || it.name == "capstone.lib" || it.name.endsWith(".$libExtension")) }
        .filter { !it.path.contains("CMakeFiles") }
        .firstOrNull()

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