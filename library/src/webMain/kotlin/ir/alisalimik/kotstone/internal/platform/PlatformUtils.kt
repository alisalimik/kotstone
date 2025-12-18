package ir.alisalimik.kotstone.internal.platform

import kotlin.js.JsAny
import kotlin.js.JsBigInt
import kotlin.js.Promise

internal expect fun Long.toPlatformBigInt(): JsBigInt

internal expect suspend fun <T : JsAny?> Promise<T>.await(): T
