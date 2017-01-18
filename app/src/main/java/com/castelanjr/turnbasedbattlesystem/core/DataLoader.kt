package com.castelanjr.turnbasedbattlesystem.core

import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.entities.skills.Skill

object DataLoader {

    fun entities(): Array<Character> {
        return arrayOf(
            Character("Beatrice", 0, 35, 5, 12, 8, 10, 13, 4, 7, true, emptyArray()), // Knight

            Character("Vahn", 0, 30, 15, 8, 15, 12, 8, 8, 10, true, emptyArray()), // Thief

            Character("Lana", 0, 28, 24, 7, 10, 9, 7, 15, 13, true, arrayOf(Skill.CURE, Skill.FIRE)), // Mage

            Character("Dracky 1", R.drawable.dracky, 30, 5, 7, 10, 8, 7, 4, 4, false, arrayOf(Skill.BITE)),

            Character("Dracky 2", R.drawable.dracky, 30, 5, 10, 6, 11, 12, 1, 1, false, arrayOf(Skill.BITE)),

            Character("Dracky 3", R.drawable.dracky, 30, 5, 9, 8, 5, 8, 1, 1, false, arrayOf(Skill.BITE))
        )
    }

}