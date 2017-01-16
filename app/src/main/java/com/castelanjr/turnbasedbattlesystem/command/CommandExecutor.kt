package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.presentation.Presenter

class CommandExecutor(val presenter: Presenter) {

    fun executeCommand(command: Command) {
        command.execute()
        presenter.renderCommand(command)
    }

}