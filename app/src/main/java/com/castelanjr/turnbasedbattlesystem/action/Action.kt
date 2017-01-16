package com.castelanjr.turnbasedbattlesystem.action

interface Action {

    fun onStart()

    fun isFinished(): Boolean

}