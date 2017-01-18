package com.castelanjr.turnbasedbattlesystem.entities.skills

import com.castelanjr.turnbasedbattlesystem.entities.Character

abstract class Skill(val name: String, val cost: Int, val power: Int, val colorAnimation: Int) {

    companion object{
        val CURE = Cure()
        val FIRE = Fire()
    }

    var result: Int = 0
    var success = true

    fun execute(actor: Character, target: Character) {
        success = checkMp(actor)
        if (success) {
            internalExecute(actor, target)
            consumeMp(actor)
        }
    }

    abstract fun internalExecute(actor: Character, target: Character)

    open fun message() = ""

    fun checkMp(actor: Character): Boolean {
        success = actor.mp >= cost
        return success
    }

    fun consumeMp(actor: Character) {
        actor.mp -= cost
    }

}