package com.castelanjr.turnbasedbattlesystem.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.char.Skill
import com.castelanjr.turnbasedbattlesystem.command.AttackCommand
import com.castelanjr.turnbasedbattlesystem.command.Command
import com.castelanjr.turnbasedbattlesystem.command.DefendCommand
import com.castelanjr.turnbasedbattlesystem.command.RunCommand
import com.castelanjr.turnbasedbattlesystem.presentation.Presenter
import com.castelanjr.turnbasedbattlesystem.presentation.View
import kotlinx.android.synthetic.main.activity_battle.*
import org.jetbrains.anko.onClick

class BattleActivity : AppCompatActivity(), View {

    var heroesMap = mutableMapOf<Character, android.view.View>()
    val presenter = Presenter(this)

    var currentCommandActor: Character? = null
    var currentCommandTarget: Character? = null
    var commandType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        attack.onClick {
            commandType = "attack"
            presenter.onAttackSelected()
        }

        defend.onClick {
            commandType = "defend"
            presenter.onDefendSelected()
        }

        skill.onClick {
            commandType = "skill"
            currentCommandActor?.let { it1 -> presenter.onSkillSelected(it1) }
        }

        run.onClick {
            commandType = "run"
            presenter.onRunSelected()
        }

        presenter.initialize()
    }

    override fun showMessage(text: String, action: (() -> Unit?)?) {
        message.visibility = VISIBLE
        message.text = text
        message.onClick {
            if (action == null) {
                message.visibility = GONE
            } else {
                action.invoke()
                message.visibility = GONE
            }
        }
    }

    override fun setCurrentActor(actor: Character?) {
        currentCommandActor = actor
    }

    override fun showCommands() {
        message.visibility = GONE
    }

    override fun pickTarget() {
        listOf(enemy1, enemy2, enemy3)
                .forEach { it.onClick {
                    currentCommandTarget = it?.tag as Character
                    currentCommandTarget?.let { presenter.onTargetSelected(it) }
                } }
    }

    override fun renderAttack(actor: Character, target: Character, successful: Boolean, damage: Int) {

    }

    override fun renderSkill(actor: Character, target: Character, successful: Boolean, damage: Int) {

    }

    override fun finalize() {
        finish()
    }

    override fun command(): Command {
        return when (commandType) {
            "attack" -> AttackCommand(actor(currentCommandActor), actor(currentCommandTarget))
            "defend" -> DefendCommand(actor(currentCommandActor))
            "skill" -> throw NotImplementedError("Feature not implemented yet...")
            "run" -> RunCommand(actor(currentCommandActor))
            else -> throw IllegalStateException("Command type can't be null here")
        }
    }

    private fun actor(actor: Character?) = actor ?: throw IllegalStateException("Actor can't be null here")

    override fun showSkills(skills: Array<Skill>) {

    }

    override fun bindHero(index: Int, hero: Character) {
        val view = heroViewAt(index)
        view.visibility = VISIBLE
        heroesMap.put(hero, view)
    }

    override fun showHeroName(index: Int, name: String) {
        when (index) {
            0 -> name1.text = name
            1 -> name2.text = name
            2 -> name3.text = name
        }
    }

    override fun showHeroHp(index: Int, hp: String) {
        when (index) {
            0 -> hp1.text = hp
            1 -> hp2.text = hp
            2 -> hp3.text = hp
        }
    }

    override fun showHeroMp(index: Int, mp: String) {
        when (index) {
            0 -> mp1.text = mp
            1 -> mp2.text = mp
            2 -> mp3.text = mp
        }
    }

    override fun showEnemy(index: Int, enemy: Character, sprite: Int) {
        when (index) {
            0 -> bindEnemy(enemy1, enemy, sprite)
            1 -> bindEnemy(enemy2, enemy, sprite)
            2 -> bindEnemy(enemy3, enemy, sprite)
        }
    }

    private fun bindEnemy(imageView: ImageView, enemy: Character, sprite: Int) {
        imageView.visibility = VISIBLE
        imageView.setImageResource(sprite)
        imageView.tag = enemy
    }

    private fun heroViewAt(index: Int): android.view.View {
        return when(index) {
            0 -> hero1
            1 -> hero2
            2 -> hero3
            else -> throw IllegalArgumentException("Invalid index")
        }
    }
}
