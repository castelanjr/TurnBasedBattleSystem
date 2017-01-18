package com.castelanjr.turnbasedbattlesystem.ui

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.char.Skill
import com.castelanjr.turnbasedbattlesystem.command.Command

interface View {

    fun showMessage(text: String, action: (() -> Unit?)? = null)

    fun setCurrentActor(actor: Character?)

    fun showCommands()

    fun pickTarget()

    fun renderAttack(actor: Character, target: Character, successful: Boolean, damage: Int)

    fun renderSkill(actor: Character, target: Character, successful: Boolean, damage: Int)

    fun finalize()

    fun command(): Command

    fun bindHero(index: Int, hero: Character)

    fun showHeroName(index: Int, name: String)

    fun showHeroHp(index: Int, hp: String)

    fun showHeroMp(index: Int, mp: String)

    fun showEnemy(index: Int, enemy: Character, sprite: Int)

    fun showSkills(skillList: Array<Skill>)

    fun dismissSkills()

}