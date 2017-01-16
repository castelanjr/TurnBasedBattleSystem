package com.castelanjr.turnbasedbattlesystem.presentation

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.AttackCommand
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.RunCommand
import com.castelanjr.turnbasedbattlesystem.command.SkillCommand
import com.castelanjr.turnbasedbattlesystem.core.DataLoader
import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.core.Result
import org.jetbrains.anko.doAsync

class Presenter(val view: View) {

    val engine = Engine(this)

    fun initialize() {

        val entities = DataLoader.entities()
        engine.entities = entities

        setupView(entities)

        doAsync {
            engine.run()
        }

    }

    private fun setupView(entities: Array<Character>) {
        val heroes = entities.filter { it.isPlayer }.toList()
        for (hero in heroes) {
            val index = heroes.indexOf(hero)
            view.bindHero(index, hero)
            view.showHeroName(index, hero.name)
            view.showHeroHp(index, "HP: ${hero.hp}")
            view.showHeroMp(index, "MP: ${hero.mp}")
        }

        val enemies = entities.filter { !it.isPlayer }.toList()
        for (enemy in enemies) {
            val index = enemies.indexOf(enemy)
            view.showEnemy(index, enemy, enemy.sprite)
        }
    }

    fun renderCommand(command: Command) {
        when (command) {
            is AttackCommand -> {
                with(command) {
                    view.showMessage("${actor.name} attacked ${target.name}! It " +
                            if (successful()) "worked! Dealt ${damage()} points of damage" else "failed...")
                    view.renderAttack(actor, target, successful(), damage())
                }
            }
            is SkillCommand -> view.renderSkill(command.actor, command.target,
                    command.successful(), command.damage())
            is RunCommand -> checkIfRanAway(command.isSuccessful)
        }
    }

    private fun checkIfRanAway(success: Boolean) {
        if (success) {
            view.showMessage("Ran away from the enemies!", action = { view.finalize() })
        } else {
            view.showMessage("Couldn't run away...")
        }
    }

    fun requestActionFromPlayer(actor: Character) {
        view.setCurrentActor(actor)
        view.showCommands()
    }

    fun onAttackSelected() {
        view.showMessage("Pick a target")
        view.pickTarget()
    }

    fun onDefendSelected() {
        view.showMessage("Defending")
    }

    fun onSkillSelected(actor: Character) {
        view.showSkills(actor.skills)
    }

    fun onRunSelected() {
        view.showMessage("Attempting to run")
    }

    fun onTargetSelected(target: Character) {
        view.showMessage("Target: ${target.name}")
        engine.addNextCommand(view.command())
    }

    fun onBattleEnded(result: Result) {
        if (result == Result.VICTORY) {
            view.showMessage("You won!!!", action = { view.finalize() })
        } else {
            view.showMessage("You lost...", action = { view.finalize() })
        }
    }

}