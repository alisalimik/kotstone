package ca.moheektech.capstone.internal.platform

import kotlin.js.JsBigInt
import kotlin.js.toJsBigInt

internal actual fun Long.toPlatformBigInt(): JsBigInt = this.toJsBigInt()
