package com.tzy.demo.activity.callshow

import android.service.notification.NotificationListenerService

/**
 * @Author tangzhaoyang
 * @Date 2023/8/16
 * 通过MediaController模拟耳机线控操作，可以在大多数设备上实现接听/挂断电话操作
 * 这种方式需要引导用户开启通知监听权限
 */
class EmptyNotificationListenService : NotificationListenerService()