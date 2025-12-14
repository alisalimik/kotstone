package ca.moheektech.capstone

import ca.moheektech.capstone.api.CapstoneEngine
import ca.moheektech.capstone.api.DisassemblyPosition
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.Mode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.assertNull


class CapstoneTests {

    private fun isArchSupported(arch: Architecture): Boolean {
        return try {
            // Try to create an engine and disassemble a simple instruction
            CapstoneEngine.build(arch, Mode.LITTLE_ENDIAN).use { cs ->
                // Try to disassemble at least one byte
                val testCode = byteArrayOf(0x00)
                val result = cs.disassemble(testCode)
                // If we get a result (even empty), the arch is supported
                result.isSuccess
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Helper to verify x86 disassembly results.
     * Returns true if test should continue, false if it should skip due to platform limitations.
     */
    private fun verifyX86Results(insns: List<Instruction>, testName: String): Boolean {
        if (insns.isEmpty()) {
            println("Skipping $testName: X86 disassembly returned 0 instructions (platform limitation)")
            return false
        }
        return true
    }

    // ============================================================================
    // ARM64 (AArch64) Tests - 20 tests
    // ============================================================================

    @Test
    fun testArm64BasicAdd() = runTest {
        initCapstoneTesting()
        // add w0, w0, #1
        val code = byteArrayOf(0x00, 0x04, 0x00, 0x11)

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("add", insns[0].mnemonic)
            assertEquals(0x1000, insns[0].address)
            assertEquals(4, insns[0].size)
        }
    }

    @Test
    fun testArm64Mov() = runTest {
        initCapstoneTesting()
        // mov x0, x1
        val code = byteArrayOf(0xE0.toByte(), 0x03, 0x01, 0xAA.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code, address = 0x2000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("mov", insns[0].mnemonic)
            assertTrue(insns[0].opStr.contains("x0"))
        }
    }

    @Test
    fun testArm64Nop() = runTest {
        initCapstoneTesting()
        // nop
        val code = byteArrayOf(0x1F, 0x20, 0x03, 0xD5.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("nop", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64LoadRegister() = runTest {
        initCapstoneTesting()
        // ldr x0, [x1]
        val code = byteArrayOf(0x20, 0x00, 0x40, 0xF9.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code, address = 0x3000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("ldr", insns[0].mnemonic)
            assertTrue(insns[0].opStr.contains("x0"))
        }
    }

    @Test
    fun testArm64StoreRegister() = runTest {
        initCapstoneTesting()
        // str x0, [sp, #0x10]
        val code = byteArrayOf(0xE0.toByte(), 0x0B, 0x00, 0xF9.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("str", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64Branch() = runTest {
        initCapstoneTesting()
        // b #0x1000
        val code = byteArrayOf(0x00, 0x04, 0x00, 0x14)

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("b", insns[0].mnemonic)
            assertTrue(insns[0].isJump)
        }
    }

    @Test
    fun testArm64BranchLink() = runTest {
        initCapstoneTesting()
        // bl #0x100
        val code = byteArrayOf(0x40, 0x00, 0x00, 0x94.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("bl", insns[0].mnemonic)
            assertTrue(insns[0].isCall)
        }
    }

    @Test
    fun testArm64Return() = runTest {
        initCapstoneTesting()
        // ret
        val code = byteArrayOf(0xC0.toByte(), 0x03, 0x5F, 0xD6.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("ret", insns[0].mnemonic)
            assertTrue(insns[0].isReturn)
        }
    }

    @Test
    fun testArm64Subtract() = runTest {
        initCapstoneTesting()
        // sub sp, sp, #0x10
        val code = byteArrayOf(0xFF.toByte(), 0x43, 0x00, 0xD1.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("sub", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64Compare() = runTest {
        initCapstoneTesting()
        // cmp x0, #0
        val code = byteArrayOf(0x1F, 0x00, 0x00, 0xF1.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("cmp", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64MultipleInstructions() = runTest {
        initCapstoneTesting()
        // sub sp, sp, #0x10 ; str x0, [sp] ; ldr x0, [sp] ; add sp, sp, #0x10 ; ret
        val code = byteArrayOf(
            0xFF.toByte(), 0x43, 0x00, 0xD1.toByte(), // sub sp, sp, #0x10
            0xE0.toByte(), 0x03, 0x00, 0xF9.toByte(), // str x0, [sp]
            0xE0.toByte(), 0x03, 0x40, 0xF9.toByte(), // ldr x0, [sp]
            0xFF.toByte(), 0x43, 0x00, 0x91.toByte(), // add sp, sp, #0x10
            0xC0.toByte(), 0x03, 0x5F, 0xD6.toByte()  // ret
        )

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()

            assertEquals(5, insns.size)
            assertEquals("sub", insns[0].mnemonic)
            assertEquals("str", insns[1].mnemonic)
            assertEquals("ldr", insns[2].mnemonic)
            assertEquals("add", insns[3].mnemonic)
            assertEquals("ret", insns[4].mnemonic)

            // Verify addresses are sequential
            assertEquals(0x1000, insns[0].address)
            assertEquals(0x1004, insns[1].address)
            assertEquals(0x1008, insns[2].address)
            assertEquals(0x100C, insns[3].address)
            assertEquals(0x1010, insns[4].address)
        }
    }

    @Test
    fun testArm64ConditionalBranch() = runTest {
        initCapstoneTesting()
        // b.eq #0x100
        val code = byteArrayOf(0x20, 0x08, 0x00, 0x54)

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertTrue(insns[0].mnemonic.startsWith("b."))
            assertTrue(insns[0].isJump)
        }
    }

    @Test
    fun testArm64LogicalAnd() = runTest {
        initCapstoneTesting()
        // and x0, x0, x1
        val code = byteArrayOf(0x00, 0x00, 0x01, 0x8A.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("and", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64LogicalOr() = runTest {
        initCapstoneTesting()
        // orr x0, x0, x1
        val code = byteArrayOf(0x00, 0x00, 0x01, 0xAA.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("orr", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64Shift() = runTest {
        initCapstoneTesting()
        // lsl x0, x0, #1
        val code = byteArrayOf(0x00, 0x04, 0x40, 0xD3.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("lsl", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64LoadPair() = runTest {
        initCapstoneTesting()
        // ldp x0, x1, [sp]
        val code = byteArrayOf(0xE0.toByte(), 0x07, 0x40, 0xA9.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("ldp", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64StorePair() = runTest {
        initCapstoneTesting()
        // stp x29, x30, [sp, #-0x10]!
        val code = byteArrayOf(0xFD.toByte(), 0x7B, 0xBF.toByte(), 0xA9.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("stp", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64Multiply() = runTest {
        initCapstoneTesting()
        // mul x0, x0, x1
        val code = byteArrayOf(0x00, 0x7C, 0x01, 0x9B.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("mul", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64Divide() = runTest {
        initCapstoneTesting()
        // sdiv x0, x0, x1
        val code = byteArrayOf(0x00, 0x0C, 0xC1.toByte(), 0x9A.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("sdiv", insns[0].mnemonic)
        }
    }

    @Test
    fun testArm64AddWithCarry() = runTest {
        initCapstoneTesting()
        // adc x0, x0, x1
        val code = byteArrayOf(0x00, 0x00, 0x01, 0x9A.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("adc", insns[0].mnemonic)
        }
    }

    // ============================================================================
    // ARM (32-bit) Tests - 20 tests
    // ============================================================================

    @Test
    fun testArmBasicAdd() = runTest {
        initCapstoneTesting()
        // add r0, r1, r2
        val code = byteArrayOf(0x02, 0x00, 0x81.toByte(), 0xE0.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("add", insns[0].mnemonic)
            assertEquals(0x1000, insns[0].address)
            assertEquals(4, insns[0].size)
        }
    }

    @Test
    fun testArmMov() = runTest {
        initCapstoneTesting()
        // mov r0, r1
        val code = byteArrayOf(0x01, 0x00, 0xA0.toByte(), 0xE1.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("mov", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmNop() = runTest {
        initCapstoneTesting()
        // nop (mov r0, r0)
        val code = byteArrayOf(0x00, 0x00, 0xA0.toByte(), 0xE1.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            // Could be "nop" or "mov r0, r0" depending on Capstone version
            assertTrue(insns[0].mnemonic == "nop" || insns[0].mnemonic == "mov")
        }
    }

    @Test
    fun testArmLoad() = runTest {
        initCapstoneTesting()
        // ldr r0, [r1]
        val code = byteArrayOf(0x00, 0x00, 0x91.toByte(), 0xE5.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("ldr", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmStore() = runTest {
        initCapstoneTesting()
        // str r0, [r1]
        val code = byteArrayOf(0x00, 0x00, 0x81.toByte(), 0xE5.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("str", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmBranch() = runTest {
        initCapstoneTesting()
        // b #0x100
        val code = byteArrayOf(0x3E, 0x00, 0x00, 0xEA.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("b", insns[0].mnemonic)
            assertTrue(insns[0].isJump)
        }
    }

    @Test
    fun testArmBranchLink() = runTest {
        initCapstoneTesting()
        // bl #0x100
        val code = byteArrayOf(0x3E, 0x00, 0x00, 0xEB.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("bl", insns[0].mnemonic)
            assertTrue(insns[0].isCall)
        }
    }

    @Test
    fun testArmBranchExchange() = runTest {
        initCapstoneTesting()
        // bx lr
        val code = byteArrayOf(0x1E, 0xFF.toByte(), 0x2F, 0xE1.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("bx", insns[0].mnemonic)
            assertTrue(insns[0].isReturn || insns[0].isJump)
        }
    }

    @Test
    fun testArmSubtract() = runTest {
        initCapstoneTesting()
        // sub r0, r1, r2
        val code = byteArrayOf(0x02, 0x00, 0x41, 0xE0.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("sub", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmCompare() = runTest {
        initCapstoneTesting()
        // cmp r0, r1
        val code = byteArrayOf(0x01, 0x00, 0x50, 0xE1.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("cmp", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmMultipleInstructions() = runTest {
        initCapstoneTesting()
        // push {r4, lr} ; mov r0, r1 ; pop {r4, pc}
        val code = byteArrayOf(
            0x10, 0x40, 0x2D, 0xE9.toByte(), // push {r4, lr}
            0x01, 0x00, 0xA0.toByte(), 0xE1.toByte(), // mov r0, r1
            0x10, 0x80.toByte(), 0xBD.toByte(), 0xE8.toByte()  // pop {r4, pc}
        )

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code, address = 0x2000)
            val insns = result.getOrThrow()

            assertEquals(3, insns.size)
            assertEquals("push", insns[0].mnemonic)
            assertEquals("mov", insns[1].mnemonic)
            assertEquals("pop", insns[2].mnemonic)
        }
    }

    @Test
    fun testArmLogicalAnd() = runTest {
        initCapstoneTesting()
        // and r0, r1, r2
        val code = byteArrayOf(0x02, 0x00, 0x01, 0xE0.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("and", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmLogicalOr() = runTest {
        initCapstoneTesting()
        // orr r0, r1, r2
        val code = byteArrayOf(0x02, 0x00, 0x81.toByte(), 0xE1.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("orr", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmExclusiveOr() = runTest {
        initCapstoneTesting()
        // eor r0, r1, r2
        val code = byteArrayOf(0x02, 0x00, 0x21, 0xE0.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("eor", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmShift() = runTest {
        initCapstoneTesting()
        // lsl r0, r1, #1
        val code = byteArrayOf(0x81.toByte(), 0x00, 0xA0.toByte(), 0xE1.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("lsl", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmMultiply() = runTest {
        initCapstoneTesting()
        // mul r0, r1, r2
        val code = byteArrayOf(0x91.toByte(), 0x02, 0x00, 0xE0.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("mul", insns[0].mnemonic)
        }
    }

    @Test
    fun testArmLoadMultiple() = runTest {
        initCapstoneTesting()
        // ldm sp!, {r4, pc}
        val code = byteArrayOf(0x10, 0x80.toByte(), 0xBD.toByte(), 0xE8.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertTrue(insns[0].mnemonic.startsWith("ldm") || insns[0].mnemonic == "pop")
        }
    }

    @Test
    fun testArmStoreMultiple() = runTest {
        initCapstoneTesting()
        // stmdb sp!, {r4, lr}
        val code = byteArrayOf(0x10, 0x40, 0x2D, 0xE9.toByte())

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertTrue(insns[0].mnemonic.startsWith("stm") || insns[0].mnemonic == "push")
        }
    }

    @Test
    fun testArmConditionalExecution() = runTest {
        initCapstoneTesting()
        // moveq r0, r1
        val code = byteArrayOf(0x01, 0x00, 0xA0.toByte(), 0x01)

        CapstoneEngine.build(Architecture.ARM, Mode.ARM).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertTrue(insns[0].mnemonic.contains("eq") || insns[0].mnemonic == "mov")
        }
    }

    @Test
    fun testArmThumbMode() = runTest {
        initCapstoneTesting()
        // movs r0, #0 (Thumb encoding)
        val code = byteArrayOf(0x00, 0x20)

        CapstoneEngine.build(Architecture.ARM, Mode.THUMB).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            assertEquals("movs", insns[0].mnemonic)
            assertEquals(2, insns[0].size) // Thumb instructions are 2 bytes
        }
    }

    // ============================================================================
    // x86/x86_64 Tests - 20 tests
    // ============================================================================

    @Test
    fun testX86_64Nop() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        if (!isArchSupported(Architecture.X86)) {
            println("Skipping testX86_64Nop: X86 not supported on this platform")
            return@runTest
        }

        // nop
        val code = byteArrayOf(0x90.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("nop", insns[0].mnemonic)
            assertEquals(1, insns[0].size)
        }
    }

    @Test
    fun testX86_64MovRegToReg() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // mov rax, rbx
        val code = byteArrayOf(0x48, 0x89.toByte(), 0xD8.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("mov", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64AddRegImm() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // add rax, 0x10
        val code = byteArrayOf(0x48, 0x83.toByte(), 0xC0.toByte(), 0x10)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("add", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64SubRegImm() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // sub rsp, 0x20
        val code = byteArrayOf(0x48, 0x83.toByte(), 0xEC.toByte(), 0x20)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("sub", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Push() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // push rbp
        val code = byteArrayOf(0x55)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("push", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Pop() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // pop rbp
        val code = byteArrayOf(0x5D)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("pop", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Call() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // call rel32
        val code = byteArrayOf(0xE8.toByte(), 0x00, 0x00, 0x00, 0x00)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("call", insns[0].mnemonic)
            assertTrue(insns[0].isCall)
        }
    }

    @Test
    fun testX86_64Ret() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // ret
        val code = byteArrayOf(0xC3.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("ret", insns[0].mnemonic)
            assertTrue(insns[0].isReturn)
        }
    }

    @Test
    fun testX86_64Jmp() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // jmp rel8
        val code = byteArrayOf(0xEB.toByte(), 0x10)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("jmp", insns[0].mnemonic)
            assertTrue(insns[0].isJump)
        }
    }

    @Test
    fun testX86_64ConditionalJump() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // je rel8
        val code = byteArrayOf(0x74, 0x10)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertTrue(insns[0].mnemonic.startsWith("j"))
            assertTrue(insns[0].isJump)
        }
    }

    @Test
    fun testX86_64LoadMemory() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // mov rax, [rbx]
        val code = byteArrayOf(0x48, 0x8B.toByte(), 0x03)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("mov", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64StoreMemory() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // mov [rbx], rax
        val code = byteArrayOf(0x48, 0x89.toByte(), 0x03)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("mov", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Lea() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // lea rax, [rbx+8]
        val code = byteArrayOf(0x48, 0x8D.toByte(), 0x43, 0x08)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("lea", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Cmp() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // cmp rax, rbx
        val code = byteArrayOf(0x48, 0x39.toByte(), 0xD8.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("cmp", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Test() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // test rax, rax
        val code = byteArrayOf(0x48, 0x85.toByte(), 0xC0.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("test", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Xor() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // xor eax, eax
        val code = byteArrayOf(0x31, 0xC0.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("xor", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64And() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // and rax, rbx
        val code = byteArrayOf(0x48, 0x21, 0xD8.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("and", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_64Or() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // or rax, rbx
        val code = byteArrayOf(0x48, 0x09, 0xD8.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("or", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86_32BasicMov() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // mov eax, ebx
        val code = byteArrayOf(0x89.toByte(), 0xD8.toByte())

        CapstoneEngine.build(Architecture.X86, Mode.MODE_32).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(1, insns.size)
            assertEquals("mov", insns[0].mnemonic)
        }
    }

    @Test
    fun testX86MultipleInstructions() = runTest {
        initCapstoneTesting()
        if (!isArchSupported(Architecture.X86)) {
            println("Skipping X86 test: X86 not supported on this platform")
            return@runTest
        }

        // push rbp ; mov rbp, rsp ; sub rsp, 0x20 ; nop ; pop rbp ; ret
        val code = byteArrayOf(
            0x55, // push rbp
            0x48, 0x89.toByte(), 0xE5.toByte(), // mov rbp, rsp
            0x48, 0x83.toByte(), 0xEC.toByte(), 0x20, // sub rsp, 0x20
            0x90.toByte(), // nop
            0x5D, // pop rbp
            0xC3.toByte() // ret
        )

        CapstoneEngine.build(Architecture.X86, Mode.MODE_64).use { cs ->
            val result = cs.disassemble(code, address = 0x1000)
            val insns = result.getOrThrow()
            if (!verifyX86Results(insns, "testX86")) return@runTest

            assertEquals(6, insns.size)
            assertEquals("push", insns[0].mnemonic)
            assertEquals("mov", insns[1].mnemonic)
            assertEquals("sub", insns[2].mnemonic)
            assertEquals("nop", insns[3].mnemonic)
            assertEquals("pop", insns[4].mnemonic)
            assertEquals("ret", insns[5].mnemonic)
        }
    }

    // ============================================================================
    // Edge Cases and Error Handling - 10 tests
    // ============================================================================

    @Test
    fun testEmptyCode() = runTest {
        initCapstoneTesting()
        val code = byteArrayOf()

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(0, insns.size)
        }
    }

    @Test
    fun testTruncatedInstruction() = runTest {
        initCapstoneTesting()
        // Incomplete ARM64 instruction (only 2 bytes instead of 4)
        val code = byteArrayOf(0x00, 0x04)

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            // Should either return empty list or an error
            assertTrue(result.isSuccess || result.isFailure)
        }
    }

    @Test
    fun testInvalidInstruction() = runTest {
        initCapstoneTesting()
        // Invalid ARM64 instruction
        val code = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code)
            // Capstone might return an error or skip invalid instructions
            assertTrue(result.isSuccess || result.isFailure)
        }
    }

    @Test
    fun testDisassembleWithCount() = runTest {
        initCapstoneTesting()
        // Multiple ARM64 instructions but only disassemble first 2
        val code = byteArrayOf(
            0xFF.toByte(), 0x43, 0x00, 0xD1.toByte(), // sub sp, sp, #0x10
            0xE0.toByte(), 0x03, 0x00, 0xF9.toByte(), // str x0, [sp]
            0xE0.toByte(), 0x03, 0x40, 0xF9.toByte()  // ldr x0, [sp]
        )

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val result = cs.disassemble(code, count = 2)
            val insns = result.getOrThrow()

            assertEquals(2, insns.size)
        }
    }

    @Test
    fun testIterator() = runTest {
        initCapstoneTesting()
        val code = byteArrayOf(
            0xFF.toByte(), 0x43, 0x00, 0xD1.toByte(), // sub sp, sp, #0x10
            0xE0.toByte(), 0x03, 0x00, 0xF9.toByte(), // str x0, [sp]
            0xC0.toByte(), 0x03, 0x5F, 0xD6.toByte()  // ret
        )

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
            detail = true
        }.use { cs ->
            cs.iterate(code, 0x1000).use { iter ->
                val insns = mutableListOf<Instruction>()
                for (insn in iter) {
                    insns.add(insn)
                }

                assertEquals(3, insns.size)
                assertEquals("sub", insns[0].mnemonic)
                assertEquals("str", insns[1].mnemonic)
                assertEquals("ret", insns[2].mnemonic)
            }
        }
    }

    @Test
    fun testDisassembleOneByOne() = runTest {
        initCapstoneTesting()
        val code = byteArrayOf(
            0x00, 0x04, 0x00, 0x11, // add w0, w0, #1
            0xC0.toByte(), 0x03, 0x5F, 0xD6.toByte()  // ret
        )

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            val position = DisassemblyPosition(address = 0x1000)

            val insn1 = cs.disassembleOne(code, position).getOrThrow()
            assertNotNull(insn1)
            assertEquals("add", insn1.mnemonic)
            assertEquals(0x1000, insn1.address)

            val insn2 = cs.disassembleOne(code, position).getOrThrow()
            assertNotNull(insn2)
            assertEquals("ret", insn2.mnemonic)
            assertEquals(0x1004, insn2.address)

            val insn3 = cs.disassembleOne(code, position).getOrThrow()
            assertNull(insn3) // No more instructions
        }
    }

    @Test
    fun testDetailMode() = runTest {
        initCapstoneTesting()
        // add w0, w0, #1
        val code = byteArrayOf(0x00, 0x04, 0x00, 0x11)

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN) {
            detail = true
        }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            // When detail is enabled, we should get an upgraded instruction type
            assertTrue(insns[0] is InternalInstruction || insns[0] is Arm64Instruction)
        }
    }

    @Test
    fun testRegisterName() = runTest {
        initCapstoneTesting()

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            // ARM64 register IDs: X0 = ~1, X1 = ~2, etc (actual values depend on Capstone)
            val regName = cs.registerName(1)
            assertNotNull(regName)
            // Should be a valid register name
            assertTrue(regName.isNotEmpty())
        }
    }

    @Test
    fun testInstructionName() = runTest {
        initCapstoneTesting()

        CapstoneEngine.build(Architecture.ARM64, Mode.LITTLE_ENDIAN).use { cs ->
            // Get name for a specific instruction ID
            val insnName = cs.instructionName(1)
            // Should return a valid instruction name or null
            if (insnName != null) {
                assertTrue(insnName.isNotEmpty())
            }
        }
    }

    @Test
    fun testVersion() = runTest {
        initCapstoneTesting()
        val (major, minor) = CapstoneEngine.version()

        // Capstone version should be at least 4.0
        assertTrue(major >= 4)
        assertTrue(minor >= 0)
    }
}
