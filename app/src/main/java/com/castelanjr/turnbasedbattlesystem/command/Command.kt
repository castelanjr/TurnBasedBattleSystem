package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character

abstract class Command(val actor: Character, val target: Character) {

    var successful = true
    var damage = 0

    abstract fun execute()

}