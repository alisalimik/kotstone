package ir.alisalimik.kotstone.api

import ir.alisalimik.kotstone.Instruction

/** Extension to easily iterate with forEach. */
inline fun DisassemblyIterator.forEach(action: (Instruction) -> Unit) {
  use {
    for (instruction in this) {
      action(instruction)
    }
  }
}

expect suspend fun initializeCapstone()
