package com.tzy.demo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Build

/**
 * @Author tangzhaoyang
 * @Date 2023/8/16
 *
 */


/**
 * 动态注册广播适配
 */
/*fun Context.registerReceiverCompat(receiver: BroadcastReceiver?, filter: IntentFilter) {
    registerReceiverCompat(receiver, filter, true)
}*/

/**
 * 动态注册广播适配
 * Android 34 要求动态注册广播需要明确指出该接收器是否对其他应用可见
 * @param export true：对其他应用可见 false：对其他应用不可见
 */
/*
fun Context.registerReceiverCompat(receiver: BroadcastReceiver?, filter: IntentFilter, export: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val flag = if (export) Context.RECEIVER_EXPORTED else Context.RECEIVER_NOT_EXPORTED
        registerReceiver(receiver, filter, flag)
    } else {
        registerReceiver(receiver, filter)
    }
}*/
