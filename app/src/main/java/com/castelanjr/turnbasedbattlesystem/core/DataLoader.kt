package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.char.Character

object DataLoader {

    fun entities(): Array<Character> {
        return arrayOf(
                Character("Vahn", 0, 30, 10, 8, 14, 12, 8, 8, 10, true, emptyArray()),

                Character("Lana", 0, 35, 5, 12, 8, 10, 13, 4, 7, true, emptyArray()),

                Character("Slime 1", R.drawable.slime, 30, 0, 8, 10, 8, 7, 1, 1, false, emptyArray()),

                Character("Slime 2", R.drawable.slime, 30, 0, 10, 6, 12, 14, 1, 1, false, emptyArray()),

                Character("Slime 3", R.drawable.slime, 30, 0, 9, 8, 5, 8, 1, 1, false, emptyArray())
        )
    }

}