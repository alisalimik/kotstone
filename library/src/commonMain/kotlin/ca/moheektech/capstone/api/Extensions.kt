package ca.moheektech.capstone.api

import ca.moheektech.capstone.Instruction

/** Extension to easily iterate with forEach. */
inline fun DisassemblyIterator.forEach(action: (Instruction) -> Unit) {
  use {
    for (instruction in this) {
      action(instruction)
    }
  }
}

expect suspend fun initializeCapstone()
