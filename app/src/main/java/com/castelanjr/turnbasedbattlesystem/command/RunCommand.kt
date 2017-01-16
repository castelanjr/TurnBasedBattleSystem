package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character
import java.util.*

class RunCommand(actor: Character) : Command(actor, actor) {

    var isSuccessful = false

    override fun execute() {
        isSuccessful = Random().nextBoolean()
    }

    override fun successful(): Boolean {
        return isSuccessful
    }

    override fun damage(): Int {
        return 0
    }
}