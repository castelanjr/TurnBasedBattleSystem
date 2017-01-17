package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character
import java.util.*

class RunCommand(actor: Character) : Command(actor, actor) {

    override fun execute() {
        successful = Random().nextBoolean()
    }
}