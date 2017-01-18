package com.castelanjr.turnbasedbattlesystem.entities.skills

import android.graphics.Color
import com.castelanjr.turnbasedbattlesystem.entities.Character

class Bite: Skill("Bite", 5, 12, Color.MAGENTA) {

    override fun internalExecute(actor: Character, target: Character) {
        result = ((Math.random() * actor.attack) + power).toInt() - target.defense
        target.hp -= result
    }

    override fun message() = " It dealt $result damage"
}