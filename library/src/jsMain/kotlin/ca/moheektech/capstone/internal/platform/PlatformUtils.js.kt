package ca.moheektech.capstone.internal.platform

import kotlin.js.JsBigInt

internal actual fun Long.toPlatformBigInt(): JsBigInt =
    js("BigInt")(this.toString()).unsafeCast<JsBigInt>()
