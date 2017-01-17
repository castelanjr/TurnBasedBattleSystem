package com.castelanjr.turnbasedbattlesystem.ai

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.AttackCommand
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.DefendCommand
import com.castelanjr.turnbasedbattlesystem.command.SkillCommand
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
            if (actor.hasSkills() && random.nextBoolean()) {
                return SkillCommand(actor, actor.skills[random.nextInt(actor.skills.size)],
                        entities[random.nextInt(entities.size)])
            } else {
                return AttackCommand(actor, entities[random.nextInt(entities.size)])
            }
        }
    }

}