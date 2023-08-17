package com.tzy.demo.activity.callshow

import android.Manifest
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.RingtoneManager
import android.media.session.MediaSessionManager
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.ContactsContract
import android.provider.MediaStore
import android.telecom.TelecomManager
import android.view.InputDevice
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.loader.content.CursorLoader
import java.io.File

/**
 * @Author tangzhaoyang
 * @Date 2023/8/15
 *
 */
object PhoneUtil {
    fun getContactName(context: Context, phone: String?): String {
        var result = "未知来电"
        if (phone.isNullOrEmpty()) return result
        val resolver = context.contentResolver
        var lookupUri: Uri
        val projection =
            arrayOf(ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME)
        var cursor: Cursor? = null
        try {
            lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phone)
            )
            cursor = resolver.query(lookupUri, projection, null, null, null)
            if (cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
                result = cursor.getString(1)
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun acceptCall(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            acceptCallAboveO(context)
        } else {
            sendHeadsetAction(context, true)
        }
        showInCallScreen(context, false)
    }

    fun endCall(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (!endCallAboveP(context)) {
                showInCallScreen(context, false)
            }
        } else {
            if (!sendHeadsetAction(context, false)) {
                showInCallScreen(context, false)
            }
        }
    }

    /**
     * 8.0以上接电话
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun acceptCallAboveO(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ANSWER_PHONE_CALLS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return try {
            val telManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            telManager.acceptRingingCall()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 8.0以上接电话
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun endCallAboveP(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ANSWER_PHONE_CALLS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return try {
            val telManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            telManager.endCall()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 通过模拟耳机接听/挂断电话
     * @param isAnswer: true, 接听; false: 挂断
     */
    fun sendHeadsetAction(context: Context, isAnswer: Boolean): Boolean {
        val manager =
            context.getSystemService(Context.MEDIA_SESSION_SERVICE) as? MediaSessionManager
                ?: return false
        val controllers = manager.getActiveSessions(
            ComponentName(
                context,
                EmptyNotificationListenService::class.java
            )
        )
        if (controllers.isEmpty()) return false
        try {
            for (controller in controllers) {
                if ("com.android.server.telecom" == controller.packageName) {
                    val downEvent = if (isAnswer) {
                        KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK)
                    } else {
                        val now = SystemClock.uptimeMillis()
                        KeyEvent(
                            now,
                            now,
                            KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_HEADSETHOOK,
                            1,
                            0,
                            KeyCharacterMap.VIRTUAL_KEYBOARD,
                            0,
                            KeyEvent.FLAG_LONG_PRESS,
                            InputDevice.SOURCE_KEYBOARD
                        )
                    }
                    val upEvent = KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK)
                    controller.dispatchMediaButtonEvent(downEvent)
                    controller.dispatchMediaButtonEvent(upEvent)
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 显示系统拨号界面
     * @param showDialpad 是否显示拨号盘
     */
    fun showInCallScreen(context: Context, showDialpad: Boolean) {
        val telManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        telManager.showInCallScreen(showDialpad)
    }

    fun setRing(context: Context, path: String) {
        val file = File(path)
        if (!file.exists()) return
        val oldUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE)
        val oldFilePath = getPathFromUri(context, oldUri)
        if (file.absolutePath == oldFilePath) {
            Toast.makeText(context, "重复设置", Toast.LENGTH_SHORT).show()
            return
        }
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DATA, file.absolutePath)
            put(MediaStore.MediaColumns.TITLE, file.nameWithoutExtension)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/*")
            put(MediaStore.Audio.Media.IS_RINGTONE, true)
            put(MediaStore.Audio.Media.IS_NOTIFICATION, true)
            put(MediaStore.Audio.Media.IS_ALARM, true)
            put(MediaStore.Audio.Media.IS_MUSIC, false)
        }
        // 获取文件是external还是internal的uri路径
        val uri = MediaStore.Audio.Media.getContentUriForPath(file.absolutePath)
        if (uri == null) {
            Toast.makeText(context, "设置铃声失败", Toast.LENGTH_SHORT).show()
            return
        }
        val newUri = context.contentResolver.insert(uri, values)
        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri)
        Toast.makeText(context, "设置铃声成功", Toast.LENGTH_SHORT).show()
    }

    fun getPathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Audio.Media.DATA)
        val loader = CursorLoader(context, uri, projection, null, null, null)
        val cursor = loader.loadInBackground() ?: return null
        try {
            val index = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(index)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
        }
        return null
    }
}