package ir.alisalimik.kotstone

import ir.alisalimik.kotstone.internal.platform.initializeCapstoneModule

suspend fun initCapstone() {
  initializeCapstoneModule()
}
