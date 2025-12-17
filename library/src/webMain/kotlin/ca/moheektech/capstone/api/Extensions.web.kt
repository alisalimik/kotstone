package ca.moheektech.capstone.api

import ca.moheektech.capstone.internal.platform.initializeCapstoneModule

actual suspend fun initializeCapstone() {
  initializeCapstoneModule()
}
