package ca.moheektech.capstone

import ca.moheektech.capstone.internal.*
import ca.moheektech.capstone.internal.platform.initializeCapstoneModule
import kotlin.js.Promise
import kotlinx.coroutines.await


suspend fun initCapstone() {
    initializeCapstoneModule()
}