package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.char.Character

class AttackCommand(actor: Character, target: Character): Command(actor, target) {

    override fun execute() {

    }

    override fun successful(): Boolean {
        return true
    }

    override fun damage(): Int {
        return 10
    }
}