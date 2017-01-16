package com.castelanjr.turnbasedbattlesystem.action

class FinalizingAction : Action {

    override fun onStart() = Unit

    override fun isFinished(): Boolean {
        return true
    }
}