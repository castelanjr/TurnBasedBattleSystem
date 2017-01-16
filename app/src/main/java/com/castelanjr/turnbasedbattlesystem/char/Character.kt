package com.castelanjr.turnbasedbattlesystem.char

class Character(val name: String, val sprite: Int, val maxHP: Int, val maxMP: Int,
                val attack: Int, val speed: Int, val defense: Int,
                val magicAttack: Int, val magicDefense: Int, val isPlayer: Boolean,
                val skills: Array<Skill>) {

    var hp = maxHP
    var mp = maxMP
    var isDefending = false

    fun isAlive() = hp > 0

    fun hpPercentage() = (hp / maxHP) * 100

    fun hasSkills() = !skills.isEmpty()
}
