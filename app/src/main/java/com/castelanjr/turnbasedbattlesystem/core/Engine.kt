package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.action.CheckStatusAction
import com.castelanjr.turnbasedbattlesystem.action.ExecuteCommandsAction
import com.castelanjr.turnbasedbattlesystem.action.PrepareAction
import com.castelanjr.turnbasedbattlesystem.action.SelectCommandAction
import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class Engine(val interactor: UiInteractor): Runnable {

    var entities: Array<Character> = emptyArray()
    val preparingAction = PrepareAction(this, interactor)
    val selectingCommandAction = SelectCommandAction(this, interactor, aliveEntities())
    val executingCommandAction = ExecuteCommandsAction(this, interactor, emptyList())
    val checkingStatusAction = CheckStatusAction(this, interactor, entities)

    override fun run() {
        preparingAction.onStart()
    }

    fun newTurn() {
        cleanup()
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

    private fun cleanup() {
        entities.filter { it.isDefending }.forEach { it.isDefending = false }
    }

}

