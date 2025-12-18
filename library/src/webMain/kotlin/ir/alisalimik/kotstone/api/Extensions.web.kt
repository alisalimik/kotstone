package ir.alisalimik.kotstone.api

import ir.alisalimik.kotstone.internal.platform.initializeCapstoneModule

actual suspend fun initializeCapstone() {
  initializeCapstoneModule()
}
