package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.action.CheckingStatusAction
import com.castelanjr.turnbasedbattlesystem.action.ExecutingCommandsAction
import com.castelanjr.turnbasedbattlesystem.action.PreparingAction
import com.castelanjr.turnbasedbattlesystem.action.SelectingCommandAction
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class Engine(val interactor: UiInteractor): Runnable {

    var entities: Array<Character> = emptyArray()
    val preparingAction = PreparingAction(this, interactor)
    val selectingCommandAction = SelectingCommandAction(this, interactor, aliveEntities())
    val executingCommandAction = ExecutingCommandsAction(this, interactor, emptyList())
    val checkingStatusAction = CheckingStatusAction(this, interactor, entities)

    override fun run() {
        preparingAction.onStart()
    }

    fun newTurn() {
        selectingCommandAction.entities = aliveEntities()
        selectingCommandAction.onStart()
    }

    fun executeCommands(actions: List<Command>) {
        executingCommandAction.commands = actions
        executingCommandAction.onStart()
    }

    fun checkStatus() {
        checkingStatusAction.entities = entities
        checkingStatusAction.onStart()
    }

    fun onBattleEnded(result: Result) {
        interactor.onBattleEnded(result)
    }

    fun addNextCommand(command: Command) {
        if (selectingCommandAction.isCurrent) {
            selectingCommandAction.addCommand(command)
        }
    }

    fun commandExecuted() {
        if (executingCommandAction.isCurrent) {
            executingCommandAction.onCommandExecuted()
        }
    }

    fun aliveEntities() = entities.filter { it.isAlive() }

}

