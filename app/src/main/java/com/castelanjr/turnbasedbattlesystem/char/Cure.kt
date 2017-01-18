package com.castelanjr.turnbasedbattlesystem.char

import android.graphics.Color

class Cure: Skill("Cure", 6, 20, Color.GREEN) {

    override fun internalExecute(actor: Character, target: Character) {
        result = ((Math.random() * actor.magicAttack) + power).toInt()
        result = Math.min(result, target.maxHP - target.hp)
        target.hp += result
    }

    override fun message(): String {
        return if (success) {
            " It restored $result hp"
        } else {
            " Not enough mp..."
        }
    }
}

