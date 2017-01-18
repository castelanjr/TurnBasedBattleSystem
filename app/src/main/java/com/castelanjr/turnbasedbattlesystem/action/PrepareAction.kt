package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class PrepareAction(engine: Engine, interactor: UiInteractor)
    : Action(engine, interactor) {

    override fun onStart() {
        isCurrent = true
        interactor.setupView(engine.entities)
        onFinish()
    }

    override fun onFinish() {
        isCurrent = false
        engine.newTurn()
    }
}