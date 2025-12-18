package ir.alisalimik.kotstone.internal.platform

import kotlin.js.JsBigInt
import kotlin.js.Promise
import kotlin.js.toJsBigInt
import kotlinx.coroutines.await as coroutineAwait

internal actual fun Long.toPlatformBigInt(): JsBigInt = this.toJsBigInt()

internal actual suspend fun <T : JsAny?> Promise<T>.await(): T {
  return coroutineAwait()
}
