package com.castelanjr.turnbasedbattlesystem.ui

import com.castelanjr.turnbasedbattlesystem.command.AttackCommand
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.SkillCommand
import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.entities.skills.Skill

interface View {
    fun showMessage(text: String, action: (() -> Unit?)? = null, block: Boolean = false)
    fun setCurrentActor(actor: Character?)
    fun showCommands()
    fun pickTarget()
    fun renderAttack(attack: AttackCommand)
    fun renderSkill(skill: SkillCommand)
    fun finalize()
    fun command(): Command
    fun bindHero(index: Int, hero: Character)
    fun showHeroName(index: Int, name: String)
    fun showHeroHp(index: Int, hp: String)
    fun showHeroMp(index: Int, mp: String)
    fun showEnemy(index: Int, enemy: Character, sprite: Int)
    fun showSkills(skillList: Array<Skill>)
    fun dismissSkills()
    fun disableTargets()
}