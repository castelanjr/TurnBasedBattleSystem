package com.castelanjr.turnbasedbattlesystem.ui

import android.animation.ObjectAnimator
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.char.Character
import com.castelanjr.turnbasedbattlesystem.char.Skill
import com.castelanjr.turnbasedbattlesystem.command.*
import kotlinx.android.synthetic.main.activity_battle.*
import org.jetbrains.anko.onClick

class BattleActivity : AppCompatActivity(), View {

    var charactersMap = mutableMapOf<Character, android.view.View>()
    val presenter = UiInteractor(this)
    var sp: SoundPool? = null

    var currentCommandActor: Character? = null
    var currentCommandTarget: Character? = null
    var commandType: String? = null
    var currentCommandSkill: Skill? = null

    val soundIds = mutableListOf<Int>()

    lateinit var adapter: SkillsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        attack.onClick {
            commandType = "attack"
            presenter.onAttackSelected()
            sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
        }

        defend.onClick {
            commandType = "defend"
            presenter.onDefendSelected()
            sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
        }

        skill.onClick {
            commandType = "skill"
            currentCommandActor?.let { it1 -> presenter.onSkillSelected(it1) }
            sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
        }

        run.onClick {
            commandType = "run"
            presenter.onRunSelected()
            sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
        }

        val attrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

        sp = SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(attrs)
                .build()

        volumeControlStream = AudioManager.STREAM_MUSIC

        val sound = sp?.load(this, R.raw.hit23, 1)
        if (sound != null) {
            soundIds.add(sound)
        }
        val click = sp?.load(this, R.raw.click, 1)
        if (click != null) {
            soundIds.add(click)
        }

        adapter = SkillsAdapter {
            sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
            currentCommandSkill = it
            presenter.onSkillSelected(it)
        }
        skills.layoutManager = LinearLayoutManager(this)
        skills.itemAnimator = DefaultItemAnimator()
        skills.adapter = adapter

        presenter.initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        sp?.release()
    }

    override fun showMessage(text: String, action: (() -> Unit?)?) {
        message.visibility = VISIBLE
        message.text = text
        message.onClick {
            sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
            if (action == null) {
                message.visibility = GONE
            } else {
                message.visibility = GONE
                action.invoke()
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
        listOf(enemy1, enemy2, enemy3, hero1, hero2, hero3)
                .forEach { it.onClick {
                    sp?.play(soundIds[1], 1f, 1f, 1, 0, 1f)
                    currentCommandTarget = it?.tag as Character
                    currentCommandTarget?.let { presenter.onTargetSelected(it) }
                } }
    }

    override fun renderAttack(actor: Character, target: Character, successful: Boolean, damage: Int) {
        sp?.play(soundIds[0], 1f, 1f, 1, 0, 1f)
        ObjectAnimator
                .ofFloat(charactersMap[target], "translationX", 0f, 25f, -25f, 25f, -25f,15f, -15f, 6f, -6f, 0f)
                .setDuration(500L)
                .start()
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
            "skill" -> SkillCommand(actor(currentCommandActor), skill(currentCommandSkill), actor(currentCommandTarget))
            "run" -> RunCommand(actor(currentCommandActor))
            else -> throw IllegalStateException("Command type can't be null here")
        }
    }

    private fun actor(actor: Character?) = actor ?: throw IllegalStateException("Actor can't be null here")

    private fun skill(skill: Skill?) = skill ?: throw IllegalStateException("Skill shouldn't be null here")

    override fun showSkills(skillList: Array<Skill>) {
        adapter.data = skillList
        skills.visibility = VISIBLE
    }

    override fun bindHero(index: Int, hero: Character) {
        val view = heroViewAt(index)
        view.visibility = VISIBLE
        if (hero.isDead()) {
            view.alpha = 0.7f
        }
        view.tag = hero
        charactersMap.put(hero, view)
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

    override fun dismissSkills() {
        skills.visibility = GONE
    }

    private fun bindEnemy(imageView: ImageView, enemy: Character, sprite: Int) {
        imageView.visibility = VISIBLE
        imageView.setImageResource(sprite)
        imageView.tag = enemy
        if (enemy.isDead()) {
            imageView.visibility = GONE
        }
        charactersMap.put(enemy, imageView)
    }

    private fun heroViewAt(index: Int): android.view.View {
        return when(index) {
            0 -> hero1
            1 -> hero2
            2 -> hero3
            else -> throw IllegalArgumentException("Invalid index")
        }
    }

    inner class SkillsAdapter(val listener: (skill: Skill) -> Unit): RecyclerView.Adapter<Holder>() {

        var data = emptyArray<Skill>()
        set(value) {
            field = value
            this@SkillsAdapter.notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: Holder?, position: Int) {
            holder?.bind(data[position])
        }

        override fun getItemCount() = data.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
            return Holder(layoutInflater.inflate(R.layout.adapter_skill, parent, false),
                    listener)
        }
    }

    inner class Holder(itemView: android.view.View?, click: (skill: Skill) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        lateinit var skill: Skill

        init {
            itemView?.onClick { click.invoke(skill) }
        }

        fun bind(skill: Skill) {
            this.skill = skill
            if (itemView != null) {
                (itemView as TextView).text = skill.name
            }
        }
    }
}
