package com.tzy.demo.activity.media

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.tzy.demo.BuildConfig
import com.tzy.demo.R

class PlayerService : Service() {

    private val tag = this.javaClass.simpleName
    private val notificationID = 132
    var playerChangeListener: (() -> Unit)? = null
    private val playerBinder = PlayerBinder()
    private val exoPlayers = mutableMapOf<Sound, SimpleExoPlayer>()

    override fun onCreate() {
        super.onCreate()
        Sound.values().forEach {
            exoPlayers[it] = initExoPlayer(it.file)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return playerBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        playerChangeListener = null
        if (!isPlaying()) {
            stopSelf()
            Log.d(tag, "stopping service")
        }
        return super.onUnbind(intent)
    }

    private fun initExoPlayer(soundFile: String): SimpleExoPlayer {
        val exoPlayer = SimpleExoPlayer.Builder(this).build()
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, BuildConfig.APPLICATION_ID))
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse("asset:///sounds/$soundFile")))
        Log.d(tag, "initExoPlayer: loading $soundFile")
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer.prepare()
        return exoPlayer
    }

    fun startForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isPlaying()) {
            val notificationIntent = Intent(this, MediaPlayerActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            val notification = NotificationCompat.Builder(this, MediaPlayerActivity.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getText(R.string.app_name))
                .setContentText("正在播放音乐...")
                .setSmallIcon(R.drawable.ic_volume)
                .setContentIntent(pendingIntent)
                .build()
            Log.d(tag, "starting foreground service...")
            startForeground(notificationID, notification)
        }
    }

    fun stopForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d(tag, "stopping foreground service...")
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
    }

    fun isPlaying(): Boolean {
        var playing = false
        exoPlayers.values.forEach {
            if (it.playWhenReady) playing = true
        }
        return playing
    }

    fun stopPlaying() {
        exoPlayers.values.forEach {
            it.playWhenReady = false
        }
    }

    fun setVolume(sound: Sound, volume: Float) {
        exoPlayers[sound]?.volume = volume
    }

    fun toggleSound(sound: Sound) {
        exoPlayers[sound]?.playWhenReady = !(exoPlayers[sound]?.playWhenReady ?: false)
        playerChangeListener?.invoke()
    }

    enum class Sound(val file: String) {
        RAIN("rain.ogg"),
        STORM("storm.ogg"),
        WATER("water.ogg"),
        FIRE("fire.ogg"),
        WIND("wind.ogg"),
        NIGHT("night.ogg"),
        PURR("purr.ogg")
    }

    inner class PlayerBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }
}