package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character

abstract class Command(val actor: Character, val target: Character) {

    abstract fun execute()

    abstract fun successful(): Boolean

    abstract fun damage(): Int

}