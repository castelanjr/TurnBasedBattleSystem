package com.castelanjr.turnbasedbattlesystem.entities.skills

import android.graphics.Color
import com.castelanjr.turnbasedbattlesystem.entities.Character

class Fire: Skill("Fire", 8, 20, Color.RED) {

    override fun internalExecute(actor: Character, target: Character) {
        result = ((Math.random() * actor.magicAttack) + power).toInt()
        target.hp -= result
    }

    override fun message(): String {
        return if (success) {
            " It dealt $result damage"
        } else {
            " Not enough mp..."
        }
    }
}