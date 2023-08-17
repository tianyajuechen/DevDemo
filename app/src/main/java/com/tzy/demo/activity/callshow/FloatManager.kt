package com.tzy.demo.activity.callshow

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tzy.demo.application.MyApp

/**
 * @Author tangzhaoyang
 * @Date 2023/8/15
 *
 */
class FloatManager private constructor() {

    private val windowManager = MyApp.getInstance().getSystemService(Context.WINDOW_SERVICE) as WindowManager

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { FloatManager() }
    }

    fun show() {

    }

    fun addView(view: View, params: WindowManager.LayoutParams): Boolean {
        return try {
            windowManager.addView(view, params)
            ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.navigationBars())
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateView(view: View, params: WindowManager.LayoutParams): Boolean {
        return try {
            windowManager.updateViewLayout(view, params)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun removeView(view: View): Boolean {
        return try {
            windowManager.removeView(view)
            true
        } catch (e: Exception) {
            false
        }
    }
}