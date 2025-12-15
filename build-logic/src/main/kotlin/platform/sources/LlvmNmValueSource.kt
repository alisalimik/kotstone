package platform.sources

import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import java.io.File

/**
 * Value source for finding llvm-nm.
 * Returns empty string if not found (ValueSource cannot return null).
 */
abstract class LlvmNmValueSource : ValueSource<String, LlvmNmValueSource.Parameters> {
    interface Parameters : ValueSourceParameters {
        val emscriptenRoot: Property<String>
        val hasLlvmNm: Property<Boolean>
    }

    override fun obtain(): String {
        val emRoot = parameters.emscriptenRoot.orNull
        if (!emRoot.isNullOrEmpty()) {
            var llvmNm = File(emRoot, "llvm-nm")
            if (llvmNm.exists()) {
                return llvmNm.absolutePath
            }

            // Check upstream/bin location (sibling "bin" folder relative to emscripten root)
            // emcc is often in upstream/emscripten, while llvm tools are in upstream/bin
            llvmNm = File(File(emRoot).parentFile, "bin/llvm-nm")
            if (llvmNm.exists()) {
                return llvmNm.absolutePath
            }
        }

        // Try system llvm-nm
        return if (parameters.hasLlvmNm.get()) "llvm-nm" else ""
    }
}