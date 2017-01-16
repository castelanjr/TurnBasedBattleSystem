package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character

class DefendCommand(actor: Character): Command(actor, actor) {

    override fun execute() {
        actor.isDefending = true
    }

    override fun successful(): Boolean {
        return true
    }

    override fun damage(): Int {
        return 0
    }
}