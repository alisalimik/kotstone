package ca.moheektech.capstone

import ca.moheektech.capstone.api.CapstoneEngine
import ca.moheektech.capstone.arch.ArchDetail
import ca.moheektech.capstone.bit.BitField
import ca.moheektech.capstone.enums.Architecture
import ca.moheektech.capstone.enums.Mode
import ca.moheektech.capstone.exp.x86.X86EFlags
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

import ca.moheektech.capstone.X86Instruction

class BitFieldVerificationTest {

    @Test
    fun testX86EFlagsBitField() = runTest {
        initCapstoneTesting()
        
        // ADD EAX, 1 (0x83, 0xC0, 0x01)
        // Modifies: OF, SF, ZF, AF, PF, CF
        val code = byteArrayOf(0x83.toByte(), 0xC0.toByte(), 0x01)

        CapstoneEngine.build(Architecture.X86, Mode.MODE_32) { detail = true }.use { cs ->
            val result = cs.disassemble(code)
            val insns = result.getOrThrow()

            assertEquals(1, insns.size)
            val insn = insns[0]
            
            assertTrue(insn is X86Instruction, "Instruction should be X86Instruction")
            
            val eflags = insn.x86.eflags
            
            // Verify types
            println("EFlags: ${eflags.toHexString()}")
        }
    }
}
