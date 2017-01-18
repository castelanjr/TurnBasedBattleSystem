package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.entities.Character
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AttackCommandTest {

    val strong = Character("Strong", 0, 99, 99, 99, 99, 99, 99, 99, 99, true, emptyArray())
    val weak = Character("Weak", 0, 1, 1, 1, 1, 1, 1, 1, 1, false, emptyArray())

    @Test
    fun testAttack() {
        val attack = AttackCommand(strong, weak)
        attack.execute()
        assertTrue(attack.successful)
    }

    @Test
    fun testAttackMissed() {
        val attack = AttackCommand(weak, strong)
        attack.execute()
        assertFalse(attack.successful)
    }

}