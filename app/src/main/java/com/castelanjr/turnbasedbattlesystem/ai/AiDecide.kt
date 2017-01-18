package com.castelanjr.turnbasedbattlesystem.ai

import com.castelanjr.turnbasedbattlesystem.command.AttackCommand
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.DefendCommand
import com.castelanjr.turnbasedbattlesystem.command.SkillCommand
import com.castelanjr.turnbasedbattlesystem.entities.Character
import java.util.*

object AiDecide {

    fun newAiCommand(actor: Character, entities: List<Character>): Command {
        val random = Random()

        val hp = actor.hpPercentage()
        if (hp > 50) {
            return AttackCommand(actor, entities[random.nextInt(entities.size)])
        } else if (hp < 50 && hp > 25) {
            if (random.nextBoolean()) {
                return DefendCommand(actor)
            } else {
                return AttackCommand(actor, entities[random.nextInt(entities.size)])
            }
        } else {
            val randomSkill = if (actor.hasSkills()) {
                    actor.skills[random.nextInt(actor.skills.size)]
                } else {
                    null
                }
            if (randomSkill != null && actor.mp >= randomSkill.cost) {
                return SkillCommand(actor, randomSkill, entities[random.nextInt(entities.size)])
            } else {
                return AttackCommand(actor, entities[random.nextInt(entities.size)])
            }
        }
    }

}