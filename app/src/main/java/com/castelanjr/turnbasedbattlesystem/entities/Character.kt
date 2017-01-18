package com.castelanjr.turnbasedbattlesystem.entities

import com.castelanjr.turnbasedbattlesystem.entities.skills.Skill

class Character(val name: String, val sprite: Int,
                val maxHP: Int, val maxMP: Int,
                val attack: Int, val speed: Int, val accuracy: Int, val defense: Int,
                val magicAttack: Int, val magicDefense: Int,
                val isPlayer: Boolean, val skills: Array<Skill>) {

    var hp = maxHP
    var mp = maxMP
    var isDefending = false

    fun isAlive() = hp > 0

    fun isDead() = hp <= 0

    fun hpPercentage() = Math.round((hp.toDouble() / maxHP.toDouble()) * 100)

    fun hasSkills() = !skills.isEmpty()
}
