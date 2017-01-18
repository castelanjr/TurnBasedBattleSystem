package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.entities.Character

class DefendCommand(actor: Character): Command(actor, actor) {

    override fun execute() {
        actor.isDefending = true
    }

    override fun message(): String {
        return "${actor.name} defended"
    }
}