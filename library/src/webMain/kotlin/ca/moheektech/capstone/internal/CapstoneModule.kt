package ca.moheektech.capstone.internal

import kotlin.js.JsAny
import kotlin.js.JsName
import kotlin.js.Promise
import kotlin.js.definedExternally

/** 1. The Factory Function Corresponds to: var CapstoneModule = (() => { ... })(); */
@JsName("CapstoneModule")
external fun capstoneModule(config: JsAny? = definedExternally): Promise<CapstoneModuleInstance>
