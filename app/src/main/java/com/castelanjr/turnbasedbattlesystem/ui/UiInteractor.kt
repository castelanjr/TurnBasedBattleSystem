package com.castelanjr.turnbasedbattlesystem.ui

import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.command.*
import com.castelanjr.turnbasedbattlesystem.core.DataLoader
import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.core.Result

class UiInteractor(val view: View) {

    val engine = Engine(this)

    fun initialize() {

        val entities = DataLoader.entities()
        engine.entities = entities

        setupView(entities)

        engine.run()

    }

    fun setupView(entities: Array<Character>) {
        val heroes = entities.filter { it.isPlayer }.toList()
        for (hero in heroes) {
            val index = heroes.indexOf(hero)
            view.bindHero(index, hero)
            view.showHeroName(index, hero.name)
            view.showHeroHp(index, "HP: ${Math.max(hero.hp, 0)}")
            view.showHeroMp(index, "MP: ${Math.max(hero.mp, 0)}")
        }

        val enemies = entities.filter { !it.isPlayer }.toList()
        for (enemy in enemies) {
            val index = enemies.indexOf(enemy)
            view.showEnemy(index, enemy, enemy.sprite)
        }
    }

    fun renderCommand(command: Command) {
        when (command) {
            is AttackCommand -> renderAttack(command)

            is SkillCommand -> renderSkill(command)

            is RunCommand -> checkIfRanAway(command.successful)

            is DefendCommand -> view.showMessage("${command.actor.name} defended",
                    { engine.commandExecuted() })
        }
    }

    private fun renderSkill(command: SkillCommand) {
        with(command) {
            view.renderSkill(actor, target, successful, damage)
            setupView(engine.entities)
            var message = "${actor.name} cast ${skill.name} on ${target.name}!"

            if (target.isDead()) {
                message += ". ${target.name} fainted!"
            }

            view.showMessage(message, { engine.commandExecuted() })
        }
    }

    private fun renderAttack(command: AttackCommand) {
        with(command) {
            view.renderAttack(actor, target, successful, damage)
            setupView(engine.entities)
            var message = "${actor.name} attacked ${target.name}! It " + if (successful) "worked! Dealt ${damage} points of damage" else "failed..."

            if (target.isDead()) {
                message += ". ${target.name} fainted!"
            }

            view.showMessage(message, { engine.commandExecuted() })
        }
    }

    private fun checkIfRanAway(success: Boolean) {
        if (success) {
            view.showMessage("Ran away from the enemies!", { view.finalize() })
        } else {
            view.showMessage("Couldn't run away...", { engine.commandExecuted() })
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
        engine.addNextCommand(view.command())
    }

    fun onSkillSelected(actor: Character) {
//        view.showSkills(actor.skills)

        view.showMessage("Pick a target")
        view.pickTarget()
    }

    fun onRunSelected() {
        view.showMessage("Attempting to run")
        engine.addNextCommand(view.command())
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