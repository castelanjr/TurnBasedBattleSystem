package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.CommandExecutor
import com.castelanjr.turnbasedbattlesystem.presentation.Presenter

class ExecutingCommandsAction(val actions: MutableList<Command>, presenter: Presenter) : Action {

    val commandExecutor = CommandExecutor(presenter)
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