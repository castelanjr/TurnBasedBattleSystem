package com.castelanjr.turnbasedbattlesystem.action

import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

abstract class Action(val engine: Engine, val interactor: UiInteractor) {

    var isCurrent = false

    abstract fun onStart()

    abstract fun onFinish()

}