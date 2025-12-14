package platform

object Toolchains {

    fun commandExists(cmd: String, vararg args: String): Boolean {
        val versionArgs = if (args.isNotEmpty()) args else arrayOf("--version")
        return runCatching {
            ProcessBuilder(cmd, *versionArgs)
                .redirectErrorStream(true)
                .start()
                .waitFor() == 0
        }.getOrDefault(false)
    }

    val hasZig: Boolean = commandExists("zig", "version")

    val nativeLinux: Boolean = Host.isLinux

    val linuxX64OnMac: Boolean = Host.isMac && (
            commandExists("x86_64-linux-gnu-gcc") || 
            commandExists("x86_64-linux-musl-gcc") || 
            hasZig || 
            commandExists("clang") ||
            commandExists("aarch64-unknown-linux-gnu-gcc")
    )

    val mingwX64: Boolean =
        Host.isWindows || commandExists("x86_64-w64-mingw32-gcc") || hasZig

    val mingwX86: Boolean =
        Host.isWindows || commandExists("i686-w64-mingw32-gcc") || hasZig

    val linuxArm32: Boolean =
        Host.isLinux || commandExists("arm-linux-gnueabihf-gcc") || hasZig

    val hasEmscripten: Boolean = commandExists("emcc", "--version")

    fun getEmscriptenRoot(): String? {
        if (!hasEmscripten) return null
        return runCatching {
            val process = ProcessBuilder("which", "emcc")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            if (output.isNotEmpty()) {
                // emcc is at EMSCRIPTEN_ROOT/emcc, so get parent directory
                java.io.File(output).parentFile?.absolutePath
            } else null
        }.getOrNull()
    }

    fun findEmscriptenToolchain(): String? {
        val emRoot = getEmscriptenRoot() ?: return null
        val toolchainFile = java.io.File(emRoot, "cmake/Modules/Platform/Emscripten.cmake")

        if (toolchainFile.exists()) {
            return toolchainFile.absolutePath
        }

        // Fallback search
        return runCatching {
            val process = ProcessBuilder("find", "$emRoot/../..", "-name", "Emscripten.cmake")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readLines().firstOrNull()
            process.waitFor()
            output
        }.getOrNull()
    }

    fun getLlvmNm(): String? {
        val emRoot = getEmscriptenRoot() ?: return null
        val llvmNm = java.io.File(emRoot, "llvm-nm")

        if (llvmNm.exists()) {
            return llvmNm.absolutePath
        }

        // Try system llvm-nm
        return if (commandExists("llvm-nm", "--version")) "llvm-nm" else null
    }
}