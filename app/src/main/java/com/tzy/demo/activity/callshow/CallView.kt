package com.tzy.demo.activity.callshow

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.tzy.demo.R

/**
 * @Author tangzhaoyang
 * @Date 2023/8/14
 * 来电秀UI
 */
class CallView : FrameLayout {

    private lateinit var ivAnswer: ImageView
    private lateinit var ivHangUp: ImageView
    private lateinit var tvPhone: TextView
    private lateinit var tvName: TextView
    private lateinit var floatManager: FloatManager
    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer
    var listener: Listener? = null
    private var isShowing = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_call_show, this, true)
        ivAnswer = findViewById(R.id.iv_answer)
        ivHangUp = findViewById(R.id.iv_hang_up)
        tvPhone = findViewById(R.id.tv_phone)
        tvName = findViewById(R.id.tv_name)
        playerView = findViewById(R.id.player_view)

        initPlayer()

        ivAnswer.setOnClickListener {
            listener?.acceptCall()
        }

        ivHangUp.setOnClickListener {
            listener?.endCall()
        }

        floatManager = FloatManager.instance
    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(context).build()
        player.volume = 0F
        playerView.player = player
        val mediaItem = MediaItem.fromUri(Uri.parse("asset:///call/yuechan.mp4"))
        player.setMediaItem(mediaItem)
        player.repeatMode = ExoPlayer.REPEAT_MODE_ONE
        player.prepare()
    }

    fun setInfo(phone: String) {
        tvPhone.text = phone
        tvName.text = PhoneUtil.getContactName(context, phone)
    }

    fun show() {
        if (isShowing) return
        isShowing = true
        val layoutParams = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            //高版本适配 全面/刘海屏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
        floatManager.addView(this, layoutParams)
        playerView.player = player
        player.play()
    }

    fun hide() {
        if (isShowing) {
            isShowing = false
            floatManager.removeView(this)
            player.pause()
            player.seekToDefaultPosition()
        }
    }

    fun destroy() {
        player.release()
    }

    interface Listener {
        fun acceptCall()
        fun endCall()
    }
}