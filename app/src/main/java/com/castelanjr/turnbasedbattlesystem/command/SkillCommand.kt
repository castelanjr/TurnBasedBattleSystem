package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.char.Skill

class SkillCommand(actor: Character, val skill: Skill, target: Character): Command(actor, target) {

    override fun execute() {
        skill.execute(actor, target)
        successful = skill.success
    }

    override fun message(): String {
        var message = "${actor.name} cast ${skill.name} on ${target.name}! ${skill.message()}"

        if (target.isDead()) {
            message += ". ${target.name} fainted!"
        }
        return message
    }
}