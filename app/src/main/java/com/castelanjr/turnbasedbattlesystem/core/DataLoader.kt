package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.char.Character

object DataLoader {

    fun entities(): Array<Character> {
        return arrayOf(
                Character("Vahn", 0, 99, 99, 10, 10, 10, 10, 10, true, emptyArray()),

                Character("Slime", R.drawable.slime, 99, 50, 8, 8, 8, 8, 8, false, emptyArray())
        )
    }

}