package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.entities.Character

abstract class Command(val actor: Character, val target: Character) {

    var successful = true
    var damage = 0

    abstract fun execute()

    open fun message() = ""

}