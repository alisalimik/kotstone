package ca.moheektech.capstone.api

import ca.moheektech.capstone.internal.platform.CapstoneLibrary

actual suspend fun initializeCapstone() {
  CapstoneLibrary.INSTANCE
}
