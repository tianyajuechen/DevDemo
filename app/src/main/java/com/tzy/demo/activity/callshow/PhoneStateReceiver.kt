package com.tzy.demo.activity.callshow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

/**
 * @Author tangzhaoyang
 * @Date 2023/8/15
 *
 */
class PhoneStateReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "PhoneStateReceiver"
    }
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Log.e(TAG, "state=$state, number=$number")
        }
    }
}