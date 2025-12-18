package ir.alisalimik.kotstone.api

import ir.alisalimik.kotstone.internal.platform.CapstoneLibrary

actual suspend fun initializeCapstone() {
  CapstoneLibrary.INSTANCE
}
