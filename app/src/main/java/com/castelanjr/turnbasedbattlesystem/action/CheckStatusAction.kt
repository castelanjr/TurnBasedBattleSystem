package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.core.Result
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class CheckStatusAction(engine: Engine, interactor: UiInteractor, var entities: Array<Character>)
    : Action(engine, interactor) {

    override fun onStart() {
        isCurrent = true

        var heroesAlive = 0
        var enemiesAlive = 0

        entities.filter { it.isAlive() }
                .forEach {
                    if (it.isPlayer) {
                        heroesAlive++
                    } else {
                        enemiesAlive++
                    }
                }

        val finished = heroesAlive == 0 || enemiesAlive == 0
        if (finished) {
            engine.onBattleEnded(if (enemiesAlive == 0) Result.VICTORY else Result.DEFEAT)
        } else {
            engine.newTurn()
        }

        onFinish()

    }

    override fun onFinish() {
        isCurrent = false
    }
}