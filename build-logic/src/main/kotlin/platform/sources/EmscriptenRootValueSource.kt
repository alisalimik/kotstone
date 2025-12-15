package platform.sources

import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import java.io.File

/**
 * Value source for finding Emscripten root directory.
 * Returns empty string if not found (ValueSource cannot return null).
 */
abstract class EmscriptenRootValueSource :
    ValueSource<String, EmscriptenRootValueSource.Parameters> {
    interface Parameters : ValueSourceParameters {
        val hasEmscripten: Property<Boolean>
    }

    override fun obtain(): String {
        if (!parameters.hasEmscripten.get()) return ""

        return runCatching {
            val process = ProcessBuilder("which", "emcc")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            if (output.isNotEmpty()) {
                // emcc is at EMSCRIPTEN_ROOT/emcc, so get parent directory
                File(output).parentFile?.absolutePath ?: ""
            } else ""
        }.getOrDefault("")
    }
}