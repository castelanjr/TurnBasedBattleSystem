package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class CommandExecutor(val uiInteractor: UiInteractor) {

    fun executeCommand(command: Command) {
        command.execute()
        uiInteractor.renderCommand(command)
    }

}