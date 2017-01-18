package com.castelanjr.turnbasedbattlesystem.ui

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.castelanjr.turnbasedbattlesystem.R
import com.castelanjr.turnbasedbattlesystem.entities.Character
import com.castelanjr.turnbasedbattlesystem.entities.skills.Skill
import com.castelanjr.turnbasedbattlesystem.command.*
import kotlinx.android.synthetic.main.activity_battle.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.onClick

class BattleActivity : AppCompatActivity(), View {

    var charactersMap = mutableMapOf<Character, android.view.View>()
    val presenter = UiInteractor(this)
    var sp: SoundPool? = null

    var currentCommandActor: Character? = null
    var currentCommandTarget: Character? = null
    var commandType: String? = null
    var currentCommandSkill: Skill? = null

    var clickSound = 0
    var attackSound = 0
    var skillSound = 0

    var bound = false
    var service: MusicService? = null
    var musicConnection = Connection()


    lateinit var adapter: SkillsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        attack.onClick {
            commandType = "attack"
            presenter.onAttackSelected()
            playClick()
        }

        defend.onClick {
            commandType = "defend"
            presenter.onDefendSelected()
            playClick()
        }

        skill.onClick {
            commandType = "skill"
            currentCommandActor?.let { it1 -> presenter.onSkillSelected(it1) }
            playClick()
        }

        run.onClick {
            commandType = "run"
            presenter.onRunSelected()
            playClick()
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

        attackSound = sp?.load(this, R.raw.hit23, 1) as Int
        clickSound = sp?.load(this, R.raw.click, 1) as Int
        skillSound = sp?.load(this, R.raw.zap2, 1) as Int

        doBindService()

        adapter = SkillsAdapter {
            playClick()
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
        doUnbindService()
    }

    override fun showMessage(text: String, action: (() -> Unit?)?) {
        message.visibility = VISIBLE
        message.text = text
        message.onClick {
            playClick()
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
                    playClick()
                    currentCommandTarget = it?.tag as Character
                    currentCommandTarget?.let { presenter.onTargetSelected(it) }
                } }
    }

    override fun renderAttack(attack: AttackCommand) {
        playAttack()
        ObjectAnimator
                .ofFloat(charactersMap[attack.target], "translationX", 0f, 25f, -25f, 25f, -25f,15f, -15f, 6f, -6f, 0f)
                .setDuration(500L)
                .start()
    }

    override fun renderSkill(skill: SkillCommand) {
        playSkill()
        val view = charactersMap[skill.target]
        val bg = view?.backgroundDrawable
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, skill.skill.colorAnimation)
        animator.duration = 250
        animator.addUpdateListener {
            if (view is ImageView) {
                view.setColorFilter(animator.animatedValue as Int)
            } else {
                view?.backgroundColor = animator.animatedValue as Int
            }
        }
        animator.start()
        view?.postDelayed({
            if (view is ImageView) {
                view.clearColorFilter()
            } else {
                view.backgroundDrawable = bg
            }
        }, 300)
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
            imageView.visibility = INVISIBLE
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

    private fun playClick() {
        sp?.play(clickSound, 1f, 1f, 1, 0, 1f)
    }

    private fun playAttack() {
        sp?.play(attackSound, 1f, 1f, 1, 0, 1f)
    }

    private fun playSkill() {
        sp?.play(skillSound, 1f, 1f, 1, 0, 1f)
    }

    class SkillsAdapter(val listener: (skill: Skill) -> Unit): RecyclerView.Adapter<Holder>() {

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
            return Holder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.adapter_skill, parent, false),
                    listener)
        }
    }

    class Holder(itemView: android.view.View?, click: (skill: Skill) -> Unit)
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

    inner class Connection: ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            if (binder != null) {
                service = (binder as MusicService.ServiceBinder).service()
            }
        }
    }

    fun doBindService() {
        bound = bindService(Intent(this, MusicService::class.java),
                musicConnection, Service.BIND_AUTO_CREATE)
    }

    fun doUnbindService() {
        if (bound) {
            unbindService(musicConnection)
            bound = false
        }
    }
}
