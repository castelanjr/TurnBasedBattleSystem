package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.core.Result

class CheckingStatusAction(val entities: Array<Character>): Action {

    var finished = false
    var battleFinished = false
    var result: Result? = null

    override fun onStart() {
        var heroesAlive = 0
        var enemiesAlive = 0

        entities
                .filter { it.isAlive() }
                .forEach {
                    if (it.isPlayer) {
                        heroesAlive++
                    } else {
                        enemiesAlive++
                    }
                }

        battleFinished = heroesAlive == 0 || enemiesAlive == 0
        if (battleFinished) {
            result = if (enemiesAlive == 0) Result.VICTORY else Result.DEFEAT
        }
        finished = true
    }

    fun result() = Pair(battleFinished, result)

    override fun isFinished() = finished
}