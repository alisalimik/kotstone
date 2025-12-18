package ir.alisalimik.kotstone.internal.platform

import kotlin.js.JsBigInt
import kotlin.js.Promise
import kotlinx.coroutines.await as coroutineAwait

internal actual fun Long.toPlatformBigInt(): JsBigInt =
    js("BigInt")(this.toString()).unsafeCast<JsBigInt>()

internal actual suspend fun <T : JsAny?> Promise<T>.await(): T {
  return coroutineAwait()
}
