package ca.moheektech.capstone.internal.platform

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.NativeLong
import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.LongByReference
import com.sun.jna.ptr.NativeLongByReference
import com.sun.jna.ptr.PointerByReference
import java.io.File
import java.io.FileOutputStream

/**
 * JNA interface for Capstone library
 */
@Suppress("FunctionName")
internal interface CapstoneLibrary : Library {
    companion object {
        private fun extractLibraryFromResources(): String {
            val osName = System.getProperty("os.name")?.lowercase() ?: ""
            val osArch = System.getProperty("os.arch")?.lowercase() ?: ""

            val platformDir = when {
                osName.contains("mac") && osArch.contains("aarch64") -> "capstone-macos-arm64"
                osName.contains("mac") -> "capstone-macos-x64"
                osName.contains("linux") && osArch.contains("amd64") -> "capstone-linux-x64"
                osName.contains("linux") -> "capstone-linux-x86"
                osName.contains("win") && osArch.contains("amd64") -> "capstone-win-x64"
                osName.contains("win") -> "capstone-win-x86"
                else -> throw UnsupportedOperationException("Unsupported platform: $osName-$osArch")
            }

            val libName = when {
                osName.contains("mac") -> "libcapstone.dylib"
                osName.contains("linux") -> "libcapstone.so"
                osName.contains("win") -> "capstone.dll"
                else -> "libcapstone.so"
            }

            val resourcePath = "/libs/$platformDir/$libName"
            val inputStream = CapstoneLibrary::class.java.getResourceAsStream(resourcePath)
                ?: throw RuntimeException("Capstone library not found in resources: $resourcePath")

            // Create temp file
            val tempFile = File.createTempFile("libcapstone", when {
                osName.contains("mac") -> ".dylib"
                osName.contains("win") -> ".dll"
                else -> ".so"
            })
            tempFile.deleteOnExit()

            // Extract library to temp file
            inputStream.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            return tempFile.absolutePath
        }

        val INSTANCE: CapstoneLibrary = try {
            if (Platform.isAndroid()) {
                // On Android, libraries are loaded from the APK's native library path.
                // We shouldn't try to extract them to a temp file.
                Native.load("capstone", CapstoneLibrary::class.java)
            } else {
                // JVM (Desktop) logic: Try load, then fallback to extraction
                try {
                    Native.load("capstone", CapstoneLibrary::class.java)
                } catch (e: UnsatisfiedLinkError) {
                    // If not found, extract from resources and load
                    try {
                        val libPath = extractLibraryFromResources()
                        Native.load(libPath, CapstoneLibrary::class.java)
                    } catch (ex: Exception) {
                        throw RuntimeException("Failed to extract and load Capstone library from resources", ex)
                    }
                }
            }
        } catch (ex: Exception) {
            throw RuntimeException("Failed to load Capstone library", ex)
        }
    }

    fun cs_open(arch: Int, mode: Int, handle: PointerByReference): Int
    fun cs_close(handle: PointerByReference): Int
    fun cs_disasm(
        handle: Pointer,
        code: ByteArray,
        codeSize: NativeLong,
        address: Long,
        count: NativeLong,
        insn: PointerByReference
    ): NativeLong
    fun cs_free(insn: Pointer, count: NativeLong)
    fun cs_malloc(handle: Pointer): Pointer?
    fun cs_disasm_iter(
        handle: Pointer,
        code: PointerByReference,
        size: NativeLongByReference,
        address: LongByReference,
        insn: Pointer
    ): Boolean
    fun cs_option(handle: Pointer, type: Int, value: NativeLong): Int
    fun cs_errno(handle: Pointer): Int
    fun cs_strerror(code: Int): String?
    fun cs_reg_name(handle: Pointer, regId: Int): String?
    fun cs_insn_name(handle: Pointer, insnId: Int): String?
    fun cs_group_name(handle: Pointer, groupId: Int): String?
    fun cs_version(major: IntByReference?, minor: IntByReference?): Int
    fun cs_support(query: Int): Boolean
}