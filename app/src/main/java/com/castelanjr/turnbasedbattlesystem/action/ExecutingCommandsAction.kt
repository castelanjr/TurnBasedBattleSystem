package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.CommandExecutor
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class ExecutingCommandsAction(val actions: MutableList<Command>, interactor: UiInteractor) : Action {

    val commandExecutor = CommandExecutor(interactor)
    var finished = false

    override fun onStart() {
        actions.sortByDescending { it.actor.speed }

        actions.forEach( {
            commandExecutor.executeCommand(it)
        })

        actions.clear()
        finished = true
    }

    override fun isFinished() = finished
}