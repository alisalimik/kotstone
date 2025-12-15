package platform.sources

import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import java.io.File

/**
 * Value source for finding Emscripten toolchain file.
 * Returns empty string if not found (ValueSource cannot return null).
 */
abstract class EmscriptenToolchainValueSource :
    ValueSource<String, EmscriptenToolchainValueSource.Parameters> {
    interface Parameters : ValueSourceParameters {
        val emscriptenRoot: Property<String>
    }

    override fun obtain(): String {
        val emRoot = parameters.emscriptenRoot.orNull
        if (emRoot.isNullOrEmpty()) return ""

        val toolchainFile = File(emRoot, "cmake/Modules/Platform/Emscripten.cmake")

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
            output ?: ""
        }.getOrDefault("")
    }
}