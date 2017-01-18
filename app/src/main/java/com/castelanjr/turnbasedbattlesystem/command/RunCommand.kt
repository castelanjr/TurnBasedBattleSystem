package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.entities.Character
import java.util.*

class RunCommand(actor: Character) : Command(actor, actor) {

    override fun execute() {
        successful = Random().nextBoolean()
    }

    override fun message(): String {
        return if (successful) {
            "Ran away from the enemies!"
        } else {
            "Couldn't run away..."
        }
    }
}