package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.action.*
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class Engine(val uiInteractor: UiInteractor): Runnable {

    var entities: Array<Character> = emptyArray()
    var actions = mutableListOf<Command>()
    var currentState: Action = PreparingAction()

    override fun run() {
        next()
    }

    fun next() {
        var result: Pair<Boolean, Result?> = Pair(false, null)

        when(currentState) {
            is PreparingAction -> {
                currentState = SelectingCommandsAction(uiInteractor, entities)
                currentState.onStart()
            }
            is SelectingCommandsAction -> {
                if (currentState.isFinished()) {
                    actions = (currentState as SelectingCommandsAction).result()
                    currentState = ExecutingCommandsAction(actions, uiInteractor)
                    currentState.onStart()
                }
            }
            is ExecutingCommandsAction -> {
                if (currentState.isFinished()) {
                    currentState = CheckingStatusAction(entities)
                    currentState.onStart()
                }
            }
            is CheckingStatusAction -> {
                if (currentState.isFinished()) {
                    result = (currentState as CheckingStatusAction).result()
                    if (result.first) {
                        currentState = PreparingAction()
                    } else {
                        currentState = FinalizingAction()
                    }
                    currentState.onStart()
                }
            }
            is FinalizingAction -> {
                if (currentState.isFinished()) {
                    result.second?.let { uiInteractor.onBattleEnded(it) }
                }
            }
        }
    }

    fun addNextCommand(command: Command) {
        if (currentState is SelectingCommandsAction) {
            (currentState as SelectingCommandsAction).addAction(command)
        }
    }

}

