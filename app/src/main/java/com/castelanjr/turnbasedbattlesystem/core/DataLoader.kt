package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.char.Skill

object DataLoader {

    fun entities(): Array<Character> {
        return arrayOf(
                Character("Vahn", 0, 30, 15, 8, 14, 12, 8, 8, 10, true, emptyArray()), // Thief

                Character("Lana", 0, 35, 5, 12, 8, 10, 13, 4, 7, true, emptyArray()), // Knight

                Character("Regina", 0, 28, 24, 7, 10, 9, 7, 15, 13, true, arrayOf(Skill.CURE)), // Mage

                Character("Dracky 1", R.drawable.dracky, 30, 0, 8, 10, 8, 7, 1, 1, false, emptyArray()),

                Character("Dracky 2", R.drawable.dracky, 30, 0, 10, 6, 11, 12, 1, 1, false, emptyArray()),

                Character("Dracky 3", R.drawable.dracky, 30, 0, 9, 8, 5, 8, 1, 1, false, emptyArray())
        )
    }

}