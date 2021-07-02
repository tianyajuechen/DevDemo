package com.tzy.demo.activity.media

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.View
import android.widget.ProgressBar
import android.widget.SeekBar
import com.tzy.demo.BuildConfig
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityMediaPlayerBinding

/**
 * @Author tangzhaoyang
 * @Date 2021/6/29
 *
 */
open class MediaPlayerActivity : BaseActivity<ActivityMediaPlayerBinding>() {

    companion object {
        val NOTIFICATION_CHANNEL_NAME = "音乐播放"
        val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + "." + NOTIFICATION_CHANNEL_NAME
    }

    private var playerService: PlayerService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            playerService = (service as PlayerService.PlayerBinder).getService()
            if (playerService?.isPlaying() == true) mBinding.fab.show() else mBinding.fab.hide()
            playerService?.playerChangeListener = playerChangeListener
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    private val playerChangeListener = {
        if (playerService?.isPlaying() == true) mBinding.fab.show() else mBinding.fab.hide()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_media_player
    }

    override fun initView() {
        createNotificationChannel()
    }

    override fun initEvent() {
        mBinding.playRain.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.RAIN)
            toggleProgressBar(mBinding.rainVolume)
        }
        mBinding.playStorm.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.STORM)
            toggleProgressBar(mBinding.stormVolume)
        }
        mBinding.playWater.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.WATER)
            toggleProgressBar(mBinding.waterVolume)
        }
        mBinding.playFire.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.FIRE)
            toggleProgressBar(mBinding.fireVolume)
        }
        mBinding.playWind.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.WIND)
            toggleProgressBar(mBinding.windVolume)
        }
        mBinding.playNight.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.NIGHT)
            toggleProgressBar(mBinding.nightVolume)
        }
        mBinding.playCat.setOnClickListener {
            playerService?.toggleSound(PlayerService.Sound.PURR)
            toggleProgressBar(mBinding.catVolume)
        }

        mBinding.rainVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.RAIN))
        mBinding.stormVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.STORM))
        mBinding.waterVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.WATER))
        mBinding.fireVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.FIRE))
        mBinding.windVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.WIND))
        mBinding.nightVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.NIGHT))
        mBinding.catVolume.setOnSeekBarChangeListener(VolumeChangeListener(PlayerService.Sound.PURR))

        mBinding.fab.setOnClickListener {
            playerService?.stopPlaying()
            mBinding.fab.hide()
            arrayOf(mBinding.rainVolume, mBinding.stormVolume, mBinding.waterVolume, mBinding.fireVolume, mBinding.windVolume, mBinding.nightVolume, mBinding.catVolume).forEach { bar ->
                bar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val playerIntent = Intent(this, PlayerService::class.java)
        startService(playerIntent)
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        playerService?.stopForeground()
    }

    override fun onPause() {
        playerService?.startForeground()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun toggleProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = if (progressBar.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
    }

    inner class VolumeChangeListener(private val sound: PlayerService.Sound) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            playerService?.setVolume(sound, (progress + 1) / 20f)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    }
}