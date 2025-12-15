package ca.moheektech.capstone

import ca.moheektech.capstone.internal.platform.initializeCapstoneModule

suspend fun initCapstone() {
  initializeCapstoneModule()
}
