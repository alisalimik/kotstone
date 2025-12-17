package ir.alisalimik.kapstone.internal.platform

import ir.alisalimik.kapstone.internal.CapstoneModuleInstance
import ir.alisalimik.kapstone.internal.capstoneModule
import kotlin.js.Promise
import org.khronos.webgl.Int8Array
import org.khronos.webgl.set

internal actual suspend fun loadCapstoneModule(): Promise<CapstoneModuleInstance> {
  // 1. If CapstoneModule is already defined globally, proceed immediately
  if (js("typeof CapstoneModule !== 'undefined'") as Boolean) {
    return capstoneModule()
  }

  // 2. Try CommonJS (Node.js) - Synchronous loading
  if (js("typeof require !== 'undefined'") as Boolean) {
    try {
      val module = js("require('./capstone.js')")
      if (module != null) {
        js(
            "if (typeof global !== 'undefined') global.CapstoneModule = module.CapstoneModule || module;")
        js(
            "if (typeof window !== 'undefined') window.CapstoneModule = module.CapstoneModule || module;")
      }
      return capstoneModule()
    } catch (e: Throwable) {
      println("Failed to load capstone.js via require: $e")
      println("Current dir: " + js("process.cwd()"))
      // Fall through to try ESM or fail
    }
  }

  // 3. Try ES Modules - Asynchronous dynamic import
  // We return a new Promise that handles the import first, then the initialization
  return Promise { resolve, reject ->
    // Use standard dynamic import syntax
    val importPromise = js("import('./capstone.js')")

    importPromise
        .then { module: dynamic ->
          // Assign to globals to satisfy the capstoneModule() factory expectations
          if (module != null) {
            js(
                "if (typeof global !== 'undefined') global.CapstoneModule = module.CapstoneModule || module.default || module;")
            js(
                "if (typeof window !== 'undefined') window.CapstoneModule = module.CapstoneModule || module.default || module;")
          }
          // Now call the factory function and pipe the result to this Promise
          capstoneModule().then(resolve).catch(reject)
        }
        .catch { e: dynamic ->
          println("Failed to load capstone.js via dynamic import: $e")
          reject(e)
        }
  }
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
