package ir.alisalimik.kotstone.internal.platform

import ir.alisalimik.kotstone.internal.CapstoneModuleInstance
import ir.alisalimik.kotstone.internal.capstoneModule
import kotlin.js.JsAny
import kotlin.js.JsNumber
import kotlin.js.Promise

internal actual suspend fun loadCapstoneModule(): Promise<CapstoneModuleInstance> {
  ensureCapstoneLoaded()
  return capstoneModule()
}

private fun ensureCapstoneLoaded(): Unit =
    js(
        """{
    if (typeof CapstoneModule === 'undefined') {
        if (typeof require !== 'undefined') {
            try {
                const module = require('./capstone.js');
                if (module) {
                     if (typeof global !== 'undefined') global.CapstoneModule = module.CapstoneModule || module;
                     if (typeof window !== 'undefined') window.CapstoneModule = module.CapstoneModule || module;
                }
            } catch (e) {
                // Ignore
            }
        }
    }
}""")

internal actual fun ByteArray.asJsInt8Array(): JsAny {
  val size = this.size
  val array = createInt8Array(size)
  for (i in indices) {
    setArrayValue(array, i, this[i])
  }
  return array
}

// Correct usage: js(...) as the single expression of the function
private fun createInt8Array(size: Int): JsAny = js("new Int8Array(size)")

private fun setArrayValue(array: JsAny, index: Int, value: Byte): Unit = js("array[index] = value")

internal actual fun JsAny.toInt(): Int = toIntHelper(this)

private fun toIntHelper(x: JsAny): Int = js("x | 0")

internal actual fun JsAny.toLong(): Long = toLongHelper(this).toDouble().toLong()

private fun toLongHelper(val_: JsAny): JsNumber = js("Number(val_)")

internal actual fun Int.asJsAny(): JsAny = this.toJsNumber()

internal actual fun <T : JsAny> JsAny.unsafeCast(): T = unsafeCastHelper(this)

private fun <T : JsAny> unsafeCastHelper(x: JsAny): T = js("x")
