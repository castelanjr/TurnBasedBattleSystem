package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.entities.Character
import java.util.*

class AttackCommand(actor: Character, target: Character): Command(actor, target) {

    override fun execute() {
        val chance = 95 + (actor.accuracy - target.speed)
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

    override fun message(): String {
        var message = "${actor.name} attacked ${target.name}! It " + if (successful) "worked! Dealt $damage points of damage" else "failed..."
        if (target.isDead()) {
            message += ". ${target.name} fainted!"
        }
        return message
    }
}