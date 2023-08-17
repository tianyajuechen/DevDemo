package com.tzy.demo.activity.callshow

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.util.Log
import com.tzy.demo.R

/**
 * @Author tangzhaoyang
 * @Date 2023/8/14
 * 来电秀服务
 */
class CallListenService : Service(), CallView.Listener {

    private val tag = javaClass.simpleName
    private lateinit var telManager: TelecomManager
    private lateinit var callView: CallView

    private val phoneStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE) ?: TelephonyManager.CALL_STATE_IDLE.toString()
                val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) ?: ""
                Log.e(tag, "state=$state, number=$number")
                onPhoneStateChanged(state, number)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        telManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        callView = CallView(this).apply { listener = this@CallListenService }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        buildNotification()?.let {
            startForeground(1, it)
        }
        registerPhoneStateReceiver()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun acceptCall() {
        PhoneUtil.acceptCall(this)
    }

    override fun endCall() {
        PhoneUtil.endCall(this)
    }

    private fun buildNotification(): Notification? {
        val name = getString(R.string.app_name)
        val content = "来电充电监听"
        val icon = R.mipmap.ic_launcher
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return null
            val channelId = packageName
            val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = content
                setSound(null, null)
            }
            notificationManager.createNotificationChannel(channel)
            Notification.Builder(this, channelId)
                .setContentTitle(name)
                .setContentText(content)
                .setSmallIcon(icon)
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle(name)
                .setContentText(content)
                .setSmallIcon(icon)
                .build()
        }
    }

    /**
     * 来电状态改变监听
     * @param state 来电状态
     * @param phone 电话号码
     */
    private fun onPhoneStateChanged(state: String, phone: String) {
        when (state) {
            TelephonyManager.EXTRA_STATE_IDLE -> {
                Log.e(tag, "无状态...")
                callView.hide()
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                Log.e(tag, "正在通话...")
                callView.hide()
            }
            TelephonyManager.EXTRA_STATE_RINGING -> {
                Log.e(tag, "电话响铃...")
                callView.show()
                callView.setInfo(phone)
            }
            else -> {
                callView.hide()
            }
        }
    }

    private fun registerPhoneStateReceiver() {
        val filter = IntentFilter().apply {
            addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        }
        registerReceiver(phoneStateReceiver, filter)
    }

    private fun unregisterPhoneStateReceiver() {
        unregisterReceiver(phoneStateReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        callView.destroy()
        unregisterPhoneStateReceiver()
    }
}