package ca.moheektech.capstone.internal.platform

import ca.moheektech.capstone.internal.CapstoneModuleInstance
import ca.moheektech.capstone.internal.capstoneModule
import kotlin.js.Promise
import org.khronos.webgl.Int8Array
import org.khronos.webgl.set

internal actual suspend fun loadCapstoneModule(): Promise<CapstoneModuleInstance> {
    // If CapstoneModule is not defined globally, try to load it
    if (js("typeof CapstoneModule === 'undefined'") as Boolean) {
        if (js("typeof require !== 'undefined'") as Boolean) {
            // Node.js environment
            try {
                val module = js("require('./capstone.js')")
                if (module != null) {
                     js("if (typeof global !== 'undefined') global.CapstoneModule = module.CapstoneModule || module;")
                     js("if (typeof window !== 'undefined') window.CapstoneModule = module.CapstoneModule || module;")
                }
            } catch (e: Throwable) {
                println("Failed to load capstone.js via require: $e")
                println("Current dir: " + js("process.cwd()"))
            }
        } else {
            println("Warning: CapstoneModule is undefined and strictly browser dynamic loading is not yet implemented.")
        }
    }

    return capstoneModule()
}

internal actual fun ByteArray.asJsInt8Array(): JsAny {
    // Effective way for JS: use unsafeCast if it's backed by array, or create new.
    val res = Int8Array(this.size)
    for (i in indices) {
        res[i] = this[i]
    }
    return res
}

internal actual fun JsAny.toInt(): Int = this.unsafeCast<Int>()

internal actual fun JsAny.toLong(): Long = this.unsafeCast<Double>().toLong()

internal actual fun Int.asJsAny(): JsAny = this.unsafeCast<JsAny>()

internal actual fun <T : JsAny> JsAny.unsafeCast(): T = this.asDynamic()
