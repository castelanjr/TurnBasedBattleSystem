package com.castelanjr.turnbasedbattlesystem.char

abstract class Skill(val name: String, val power: Int, val colorAnimation: Int) {

    companion object{
        val CURE = Cure()
    }

    var result: Int = 0

    abstract fun execute(actor: Character, target: Character)

    open fun message() = ""

}