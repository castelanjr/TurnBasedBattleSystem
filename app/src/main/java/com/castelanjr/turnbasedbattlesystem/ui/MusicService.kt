package com.castelanjr.turnbasedbattlesystem.ui

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.castelanjr.turnbasedbattlesystem.R
import java.io.IOException

class MusicService: Service(), MediaPlayer.OnErrorListener {
    private val binder : IBinder = ServiceBinder()
    private var player: MediaPlayer? = null
    private var position = 0

    override fun onBind(intent: Intent?) = binder

    override fun onCreate() {
        super.onCreate()
        val file: AssetFileDescriptor = resources.openRawResourceFd(R.raw.battle)
        player = MediaPlayer()
        try {
            player?.let {
                it.setDataSource(file.fileDescriptor, file.startOffset, file.declaredLength)
                it.prepare()
                it.setOnPreparedListener {
                    it.seekTo(0)
                    it.start()
                }
                it.setOnErrorListener(this)
                it.isLooping = true
                it.setVolume(0.7f, 0.7f)
            }
            file.close()
        } catch (exception: IOException) {
            Log.d("MusicService", "Error initializing media player", exception)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_STICKY

    fun pause() {
        position = player?.currentPosition ?: 0
        player?.pause()
    }

    fun resume() {
        player?.let {
            if (!it.isPlaying) {
                it.seekTo(position)
                it.start()
            }
        }
    }

    fun stop() {
        try {
            player?.let {
                it.stop()
                it.release()
            }
        } finally {
            player = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        stop()
        return false
    }

    inner class ServiceBinder : Binder() {
        fun service() = this@MusicService
    }
}