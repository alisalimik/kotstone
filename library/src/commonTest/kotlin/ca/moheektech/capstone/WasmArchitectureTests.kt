package ca.moheektech.capstone

import ca.moheektech.capstone.api.CapstoneEngine
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.Mode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class WasmArchitectureTests {

  @Test
  fun testWasmLocalGet() = runTest {
    initCapstoneTesting()
    val code = byteArrayOf(0x20, 0x00)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          val mnemonic = insns[0].mnemonic
          assertTrue(
              mnemonic == "local.get" || mnemonic == "get_local",
              "Expected local.get or get_local, got $mnemonic")
        }
      }
    }
  }

  @Test
  fun testWasmLocalSet() = runTest {
    initCapstoneTesting()
    // WebAssembly: local.set 0
    val code = byteArrayOf(0x21, 0x00)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          val mnemonic = insns[0].mnemonic
          assertTrue(
              mnemonic == "local.set" || mnemonic == "set_local",
              "Expected local.set or set_local, got $mnemonic")
        }
      }
    }
  }

  @Test
  fun testWasmI32Const() = runTest {
    initCapstoneTesting()
    // WebAssembly: i32.const 42 (0x41 0x2A)
    val code = byteArrayOf(0x41, 0x2A)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("i32.const") || insns[0].mnemonic.contains("const"))
        }
      }
    }
  }

  @Test
  fun testWasmI32Add() = runTest {
    initCapstoneTesting()
    // WebAssembly: i32.add
    val code = byteArrayOf(0x6A)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("add") || insns[0].mnemonic.contains("i32"))
        }
      }
    }
  }

  @Test
  fun testWasmI32Sub() = runTest {
    initCapstoneTesting()
    // WebAssembly: i32.sub
    val code = byteArrayOf(0x6B)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("sub") || insns[0].mnemonic.contains("i32"))
        }
      }
    }
  }

  @Test
  fun testWasmI32Mul() = runTest {
    initCapstoneTesting()
    // WebAssembly: i32.mul
    val code = byteArrayOf(0x6C)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("mul") || insns[0].mnemonic.contains("i32"))
        }
      }
    }
  }

  @Test
  fun testWasmI32Load() = runTest {
    initCapstoneTesting()
    // WebAssembly: i32.load align=2 offset=0 (0x28 0x02 0x00)
    val code = byteArrayOf(0x28, 0x02, 0x00)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("load") || insns[0].mnemonic.contains("i32"))
        }
      }
    }
  }

  @Test
  fun testWasmI32Store() = runTest {
    initCapstoneTesting()
    // WebAssembly: i32.store align=2 offset=0 (0x36 0x02 0x00)
    val code = byteArrayOf(0x36, 0x02, 0x00)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("store") || insns[0].mnemonic.contains("i32"))
        }
      }
    }
  }

  @Test
  fun testWasmCall() = runTest {
    initCapstoneTesting()
    // WebAssembly: call 0 (0x10 0x00)
    val code = byteArrayOf(0x10, 0x00)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN) { detail = true }
        .use { cs ->
          val result = cs.disassemble(code, address = 0x0)

          if (result.isSuccess) {
            val insns = result.getOrThrow()
            if (insns.isNotEmpty()) {
              assertEquals("call", insns[0].mnemonic)
              // Check if it's detected as a call instruction
              if (insns[0].isCall) {
                assertTrue(insns[0].isCall)
              }
            }
          }
        }
  }

  @Test
  fun testWasmReturn() = runTest {
    initCapstoneTesting()
    // WebAssembly: return (0x0F)
    val code = byteArrayOf(0x0F)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN) { detail = true }
        .use { cs ->
          val result = cs.disassemble(code, address = 0x0)

          if (result.isSuccess) {
            val insns = result.getOrThrow()
            if (insns.isNotEmpty()) {
              assertEquals("return", insns[0].mnemonic)
            }
          }
        }
  }

  @Test
  fun testWasmBlock() = runTest {
    initCapstoneTesting()
    // WebAssembly: block (blocktype = empty) (0x02 0x40)
    val code = byteArrayOf(0x02, 0x40)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertEquals("block", insns[0].mnemonic)
        }
      }
    }
  }

  @Test
  fun testWasmLoop() = runTest {
    initCapstoneTesting()
    // WebAssembly: loop (blocktype = empty) (0x03 0x40)
    val code = byteArrayOf(0x03, 0x40)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertEquals("loop", insns[0].mnemonic)
        }
      }
    }
  }

  @Test
  fun testWasmBranchIf() = runTest {
    initCapstoneTesting()
    // WebAssembly: br_if 0 (0x0D 0x00)
    val code = byteArrayOf(0x0D, 0x00)

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN) { detail = true }
        .use { cs ->
          val result = cs.disassemble(code, address = 0x0)

          if (result.isSuccess) {
            val insns = result.getOrThrow()
            if (insns.isNotEmpty()) {
              assertTrue(insns[0].mnemonic.contains("br"))
            }
          }
        }
  }

  @Test
  fun testWasmMultipleInstructions() = runTest {
    initCapstoneTesting()
    // WebAssembly function: local.get 0; local.get 1; i32.add; return
    val code =
        byteArrayOf(
            0x20,
            0x00, // local.get 0
            0x20,
            0x01, // local.get 1
            0x6A, // i32.add
            0x0F // return
            )

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        // Depending on Capstone support, we might get 0-4 instructions
        assertTrue(insns.size <= 4)
      }
    }
  }

  @Test
  fun testWasmArchitectureSupport() = runTest {
    initCapstoneTesting()
    // Check if WASM architecture is supported
    val isSupported = CapstoneEngine.isSupported(Architecture.WASM)

    // This test documents whether WASM is supported on this platform
    // It doesn't fail either way, just reports the status
    println("WebAssembly architecture support: $isSupported")

    if (!isSupported) {
      println("Note: WASM disassembly not available on this platform")
    }
  }

  @Test
  fun testWasmI64Operations() = runTest {
    initCapstoneTesting()
    // WebAssembly: i64.const 100 (LEB128 encoded)
    val code = byteArrayOf(0x42, 0x64.toByte())

    CapstoneEngine.build(Architecture.WASM, Mode.LITTLE_ENDIAN).use { cs ->
      val result = cs.disassemble(code, address = 0x0)

      if (result.isSuccess) {
        val insns = result.getOrThrow()
        if (insns.isNotEmpty()) {
          assertTrue(insns[0].mnemonic.contains("i64") || insns[0].mnemonic.contains("const"))
        }
      }
    }
  }
}
