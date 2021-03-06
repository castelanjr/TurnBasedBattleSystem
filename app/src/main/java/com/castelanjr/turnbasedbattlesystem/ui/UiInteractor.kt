package com.castelanjr.turnbasedbattlesystem.ui

import com.castelanjr.turnbasedbattlesystem.command.*
import com.castelanjr.turnbasedbattlesystem.core.DataLoader
import com.castelanjr.turnbasedbattlesystem.core.Engine
import com.castelanjr.turnbasedbattlesystem.core.Result
import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.entities.skills.Skill

class UiInteractor(val view: View) {

    val engine = Engine(this)

    fun initialize() {
        val entities = DataLoader.entities()
        engine.entities = entities
        setupView(entities)
        engine.run()
    }

    fun setupView(entities: Array<Character>) {
        entities.filter { it.isPlayer }.toList().forEachIndexed { index, hero ->
            view.bindHero(index, hero)
            view.showHeroName(index, hero.name)
            view.showHeroHp(index, "HP: ${Math.max(hero.hp, 0)}")
            view.showHeroMp(index, "MP: ${Math.max(hero.mp, 0)}")
        }

        entities.filter { !it.isPlayer }.toList().forEachIndexed { index, enemy ->
            view.showEnemy(index, enemy, enemy.sprite)
        }
    }

    fun renderCommand(command: Command) {
        when (command) {
            is AttackCommand -> renderAttack(command)
            is SkillCommand -> renderSkill(command)
            is RunCommand -> checkIfRanAway(command)
            is DefendCommand -> renderDefend(command)
        }
    }

    private fun renderDefend(command: DefendCommand) {
        view.showMessage(command.message(), { engine.commandExecuted() })
    }

    private fun renderSkill(command: SkillCommand) {
        if (command.successful) {
            view.renderSkill(command)
        }
        setupView(engine.entities)
        view.showMessage(command.message(), { engine.commandExecuted() })
    }

    private fun renderAttack(command: AttackCommand) {
        view.renderAttack(command)
        setupView(engine.entities)
        view.showMessage(command.message(), { engine.commandExecuted() })
    }

    private fun checkIfRanAway(command: RunCommand) {
        view.showMessage(command.message(),
                { if (command.successful) view.finalize() else engine.commandExecuted() })
    }

    fun requestActionFromPlayer(actor: Character) {
        view.setCurrentActor(actor)
        view.showCommands()
    }

    fun onAttackSelected() {
        view.showMessage("Pick a target", block = true)
        view.pickTarget()
    }

    fun onDefendSelected() {
        view.showMessage("Defending")
        engine.addNextCommand(view.command())
    }

    fun onSkillSelected(actor: Character) {
        if (!actor.skills.isEmpty()) {
            view.showMessage("Select a skill", block = true)
            view.showSkills(actor.skills)
        } else {
            view.showMessage("${actor.name} knows no skills")
        }
    }

    fun onRunSelected() {
        view.showMessage("Attempting to run")
        engine.addNextCommand(view.command())
    }

    fun onTargetSelected(target: Character) {
        view.disableTargets()
        view.showMessage("Target: ${target.name}")
        engine.addNextCommand(view.command())
    }

    fun onSkillSelected(skill: Skill) {
        view.showMessage("Pick a target to cast ${skill.name}")
        view.dismissSkills()
        view.pickTarget()
    }

    fun onBattleEnded(result: Result) {
        if (result == Result.VICTORY) {
            view.showMessage("You won!!!", action = { view.finalize() })
        } else {
            view.showMessage("You lost...", action = { view.finalize() })
        }
    }

}