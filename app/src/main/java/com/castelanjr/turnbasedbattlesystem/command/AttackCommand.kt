package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character
import java.util.*

class AttackCommand(actor: Character, target: Character): Command(actor, target) {

    override fun execute() {
        val chance = 100 + (actor.accuracy - target.speed)
        val hit = Random().nextInt(100)
        successful = chance > hit

        if (successful) {
            val dmg = Math.round((Math.random() + 1) * actor.attack).toInt()
            damage = Math.max(dmg - target.defense, 1)

            if (target.isDefending) {
                damage /= 2
            }

            target.hp = target.hp - damage
        }
    }
}