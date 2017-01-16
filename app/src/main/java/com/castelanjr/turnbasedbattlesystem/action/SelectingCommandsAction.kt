package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.ai.newAiCommand
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class SelectingCommandsAction(val uiInteractor: UiInteractor,
                              val entities: Array<Character>): Action {

    val actions = mutableListOf<Command>()

    override fun onStart() {
        entities
                .filter { it.isAlive() }
                .forEach {
                    if (it.isPlayer) {
                        uiInteractor.requestActionFromPlayer(it)
                    } else {
                        actions.add(newAiCommand(it, aliveEntities().filter { it.isPlayer }))
                    }
                }
    }

    fun result() = actions

    override fun isFinished() = actions.size == aliveEntities().size

    private fun aliveEntities(): List<Character> = entities.filter { it.isAlive() }

    fun addAction(action: Command) {
        actions.add(action)
    }

}

