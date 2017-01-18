package com.castelanjr.turnbasedbattlesystem.ai

import com.castelanjr.turnbasedbattlesystem.command.AttackCommand
import com.castelanjr.turnbasedbattlesystem.command.DefendCommand
import com.castelanjr.turnbasedbattlesystem.command.SkillCommand
import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.entities.skills.Skill
import org.junit.Assert.assertTrue
import org.junit.Test

class AiDecideTest {

    val char = Character("AI", 0, 100, 100, 0, 0, 0, 0, 0, 0, false, arrayOf(Skill.BITE))

    @Test
    fun shouldAttackWhenHighHp() {
        char.hp = 100
        val command = AiDecide.newAiCommand(char, listOf(char))
        assertTrue(command is AttackCommand)
    }

    @Test
    fun shouldAttackOrDefendWhenMediumHp() {
        char.hp = 49
        val command = AiDecide.newAiCommand(char, listOf(char))
        assertTrue(command is AttackCommand || command is DefendCommand)
    }

    @Test
    fun shouldUseSkillWhenLowHpAndHighMp() {
        char.hp = 1
        char.mp = 100
        val command = AiDecide.newAiCommand(char, listOf(char))
        assertTrue(command is SkillCommand)
        assertTrue((command as SkillCommand).skill == Skill.BITE)
    }

    @Test
    fun shouldAttackWhenLowHpAndLowMp() {
        char.hp = 1
        char.mp = 1
        val command = AiDecide.newAiCommand(char, listOf(char))
        assertTrue(command is AttackCommand)
    }

}