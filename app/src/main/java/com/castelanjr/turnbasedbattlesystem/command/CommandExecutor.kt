package com.castelanjr.turnbasedbattlesystem.command

import com.castelanjr.turnbasedbattlesystem.ui.UiInteractor

class CommandExecutor(val uiInteractor: UiInteractor) {

    fun execute(command: Command) {
        command.execute()
        uiInteractor.renderCommand(command)
    }

}