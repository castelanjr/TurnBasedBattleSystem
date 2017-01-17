package com.castelanjr.turnbasedbattlesystem.char

import android.graphics.Color

class Cure : Skill("Cure", 20, Color.GREEN) {

    override fun execute(actor: Character, target: Character) {
        result = ((Math.random() * actor.magicAttack) + power).toInt()
        result = Math.min(result, target.maxHP - target.hp)
        target.hp += result
    }
}

