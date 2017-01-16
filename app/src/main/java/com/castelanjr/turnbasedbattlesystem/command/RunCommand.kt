package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character

class RunCommand(actor: Character) : Command(actor, actor) {

    var isSuccessful = false

    override fun execute() {

    }

    override fun successful(): Boolean {
        return isSuccessful
    }

    override fun damage(): Int {
        return 0
    }
}