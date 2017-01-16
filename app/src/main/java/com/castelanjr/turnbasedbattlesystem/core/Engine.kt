package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.presentation.Presenter
import com.castelanjr.turnbasedbattlesystem.action.*

class Engine(val presenter: Presenter): Runnable {

    var entities: Array<Character> = emptyArray()
    var actions = mutableListOf<Command>()
    var currentState: Action = PreparingAction()

    override fun run() {

        var result: Pair<Boolean, Result?> = Pair(false, null)

        while (currentState !is FinalizingAction) {

            when(currentState) {
                is PreparingAction -> {
                    currentState = SelectingCommandsAction(presenter, entities)
                    currentState.onStart()
                }
                is SelectingCommandsAction -> {
                    if (currentState.isFinished()) {
                        actions = (currentState as SelectingCommandsAction).result()
                        currentState = ExecutingCommandsAction(actions, presenter)
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
            }

        }
        result.second?.let { presenter.onBattleEnded(it) }

    }

    fun addNextCommand(command: Command) {
        if (currentState is SelectingCommandsAction) {
            (currentState as SelectingCommandsAction).addAction(command)
        }
    }

}

