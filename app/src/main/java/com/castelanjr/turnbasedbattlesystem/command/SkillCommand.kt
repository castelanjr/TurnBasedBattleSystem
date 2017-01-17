package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.char.Skill

class SkillCommand(actor: Character, val skill: Skill, target: Character): Command(actor, target) {

    override fun execute() {
        skill.power
    }
}