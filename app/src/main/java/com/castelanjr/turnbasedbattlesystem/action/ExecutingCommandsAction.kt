package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.CommandExecutor
import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class ExecutingCommandsAction(engine: Engine, interactor: UiInteractor, var commands: List<Command>)
    : Action(engine, interactor) {

    var index = 0
    val executor = CommandExecutor(interactor)

    override fun onStart() {
        isCurrent = true
        index = 0
        next()
    }

    fun next() {
        if (index >= commands.size) {
            onFinish()
            return
        }
        val action = commands[index]
        // For now let's just skip if the target is dead
        // TODO: pick next random target if the current one is dead
        if (action.actor.isAlive() && action.target.isAlive()) {
            executor.execute(action)
        } else {
            onCommandExecuted()
        }
    }

    fun onCommandExecuted() {
        index++
        next()
    }

    override fun onFinish() {
        isCurrent = false
        engine.checkStatus()
    }
}