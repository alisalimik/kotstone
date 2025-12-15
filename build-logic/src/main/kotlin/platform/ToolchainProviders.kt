package platform

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ValueSourceSpec
import platform.sources.CommandExistsValueSource
import platform.sources.EmscriptenRootValueSource
import platform.sources.EmscriptenToolchainValueSource
import platform.sources.LlvmNmValueSource

/**
 * Configuration cache-compatible toolchain detection using Gradle Providers.
 * Use this class to check for toolchain availability during configuration.
 */
open class ToolchainProviders(private val project: Project) {

    /**
     * Check if a command exists on the system.
     */
    fun commandExists(cmd: String, vararg args: String): Provider<Boolean> {
        val versionArgs = if (args.isNotEmpty()) args.toList() else listOf("--version")
        return project.providers.of(
            CommandExistsValueSource::class.java,
            Action {
                parameters.command.set(cmd)
                parameters.args.set(versionArgs)
            })
    }

    val hasZig: Provider<Boolean> by lazy {
        commandExists("zig", "version")
    }

    val nativeLinux: Provider<Boolean> by lazy {
        project.providers.provider { Host.isLinux }
    }

    val nativeWindows: Provider<Boolean> by lazy {
        project.providers.provider { Host.isWindows }
    }

    val linuxX64OnMac: Provider<Boolean> by lazy {
        project.providers.provider {
            Host.isMac && (commandExists("x86_64-linux-gnu-gcc").get() || commandExists("x86_64-linux-musl-gcc").get() || hasZig.get() || commandExists(
                "clang"
            ).get() || commandExists("aarch64-unknown-linux-gnu-gcc").get())
        }
    }

    val mingwX64: Provider<Boolean> by lazy {
        project.providers.provider {
            Host.isWindows || commandExists("x86_64-w64-mingw32-gcc").get() || hasZig.get()
        }
    }

    val mingwX86: Provider<Boolean> by lazy {
        project.providers.provider {
            Host.isWindows || commandExists("i686-w64-mingw32-gcc").get() || hasZig.get()
        }
    }

    val linuxArm32: Provider<Boolean> by lazy {
        project.providers.provider {
            Host.isLinux || commandExists("arm-linux-gnueabihf-gcc").get() || hasZig.get()
        }
    }

    val hasEmscripten: Provider<Boolean> by lazy {
        commandExists("emcc", "--version")
    }

    fun getEmscriptenRoot(): Provider<String> {
        val hasEmscriptenProvider = hasEmscripten
        return project.providers.of(
            EmscriptenRootValueSource::class.java,
            Action {
                parameters.hasEmscripten.set(hasEmscriptenProvider)
            })
    }

    fun findEmscriptenToolchain(): Provider<String> {
        val emRootProvider = getEmscriptenRoot()
        return project.providers.of(
            EmscriptenToolchainValueSource::class.java,
            Action {
                parameters.emscriptenRoot.set(emRootProvider)
            })
    }

    fun getLlvmNm(): Provider<String> {
        val hasLlvmNm = commandExists("llvm-nm", "--version")
        val emRootProvider = getEmscriptenRoot()
        return project.providers.of(
            LlvmNmValueSource::class.java,
            Action {
                parameters.emscriptenRoot.set(emRootProvider)
                parameters.hasLlvmNm.set(hasLlvmNm)
            })
    }
}

