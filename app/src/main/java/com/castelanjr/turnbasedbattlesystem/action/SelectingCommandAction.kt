package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.ai.AiDecide
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class SelectingCommandAction(engine: Engine, interactor: UiInteractor, var entities: List<Character>)
    : Action(engine, interactor) {

    val actions = mutableListOf<Command>()
    var index = 0

    override fun onStart() {
        isCurrent = true
        index = 0
        actions.clear()
        next()
    }

    fun next() {
        if (index >= entities.size) {
            onFinish()
            return
        }
        val actor = entities[index]
        if (actor.isPlayer) {
            interactor.requestActionFromPlayer(actor)
        } else {
            addCommand(AiDecide.newAiCommand(actor, entities.filter { it.isPlayer }))
        }
    }

    fun addCommand(command: Command) {
        actions.add(command)
        index++
        next()
    }

    override fun onFinish() {
        isCurrent = false
        engine.executeCommands(actions)
    }
}