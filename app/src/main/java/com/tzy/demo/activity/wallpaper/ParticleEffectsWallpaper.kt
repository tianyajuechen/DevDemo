package com.tzy.demo.activity.wallpaper

import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.WindowInsets
import com.tzy.demo.R
import com.tzy.demo.bean.ParticleBean
import kotlin.concurrent.thread

class ParticleEffectsWallpaper : WallpaperService() {

    companion object {
        const val TAG = "Wallpaper"
    }

    override fun onCreateEngine(): Engine {
        return MyEngin()
    }

    inner class MyEngin : Engine() {

        var bitmap: Bitmap? = null
        var bg: Bitmap? = null
        var saveCount = 1
        var canCreate = false
        var canDraw = false
        var width = 1080
        var height = 1920
        var touchX = 0F
        var touchY = 0F
        val particleList = mutableListOf<ParticleBean>()
        val handler = Handler(Looper.getMainLooper())
        var maxDistance = 700
        var createThread: Thread? = null
        var drawThread: Thread? = null

        override fun getSurfaceHolder(): SurfaceHolder {
            return super.getSurfaceHolder()
        }

        override fun getDesiredMinimumWidth(): Int {
            return super.getDesiredMinimumWidth()
        }

        override fun getDesiredMinimumHeight(): Int {
            return super.getDesiredMinimumHeight()
        }

        override fun isVisible(): Boolean {
            return super.isVisible()
        }

        override fun isPreview(): Boolean {
            return super.isPreview()
        }

        override fun setTouchEventsEnabled(enabled: Boolean) {
            super.setTouchEventsEnabled(enabled)
        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
        }

        override fun onDestroy() {
            super.onDestroy()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
        }

        override fun onApplyWindowInsets(insets: WindowInsets?) {
            super.onApplyWindowInsets(insets)
        }

        override fun onTouchEvent(event: MotionEvent) {
            super.onTouchEvent(event)
            Log.e(TAG, "action:${event.action} x:${event.rawX} y:${event.rawY}")
            dealTouchEvent(event)
        }

        override fun onOffsetsChanged(
            xOffset: Float,
            yOffset: Float,
            xOffsetStep: Float,
            yOffsetStep: Float,
            xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset)
        }

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder?) {
            super.onSurfaceRedrawNeeded(holder)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            width = holder.surfaceFrame.width()
            height = holder.surfaceFrame.height()
            maxDistance = if (width <= height) {
                width * 7 / 10
            } else {
                height * 7 / 10
            }
            bitmap = BitmapFactory.decodeStream(resources.assets.open("gifts/1.png"))
            bg = BitmapFactory.decodeResource(resources, R.drawable.ic_girl_6)
            val canvas = holder.lockCanvas()
            drawBg(canvas)
            holder.unlockCanvasAndPost(canvas)
//            saveCount = canvas.save()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
        }

        private fun drawBg(canvas: Canvas) {
//            canvas.drawColor(Color.RED)
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            bg?.let {
                val src = Rect(0, 0, it.width, it.height)
                val dst = Rect(0, 0, width, height)
                canvas.drawBitmap(it, src, dst, null)
            }
        }

        private fun dealTouchEvent(event: MotionEvent) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    canCreate = true
                    canDraw = true
                    touchX = event.rawX
                    touchY = event.rawY
                    if (createThread == null || createThread!!.state == Thread.State.TERMINATED) {
                        createThread = thread {
                            createParticle()
                        }
                    }
                    if (drawThread == null || drawThread!!.state == Thread.State.TERMINATED) {
                        drawThread = thread {
                            drawParticle()
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    touchX = event.rawX
                    touchY = event.rawY

                }
                else -> {
                    canCreate = false
                }
            }

        }

        private fun getIconBitmap(): Bitmap {
            if (bitmap == null) {
                bitmap = try {
                    BitmapFactory.decodeStream(resources.assets.open("gifts/1.png"))
                } catch (e: Exception) {
                    Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)
                }
            }
            return bitmap!!
        }

        private fun createParticle() {
            while (canCreate) {
                val endX = touchX + (-maxDistance .. maxDistance).random()
                val endY = touchY + (-maxDistance .. maxDistance).random()
                val scale = (4..10).random() / 10F
                val particle = ParticleBean(touchX, touchY, endX, endY, scale)
                particleList.add(particle)
                Thread.sleep(20)
            }
        }

        private fun drawParticle() {
            while (canDraw) {
                val canvas = surfaceHolder.lockCanvas()
                drawBg(canvas)
                if (particleList.isEmpty()) {
                    //列表为空，且也不再创建，那代表绘制结束，标记一下不需要再画了
                    if (!canCreate) {
                        canDraw = false
                    }
                } else {
                    val tempList = mutableListOf<ParticleBean>()
                    tempList.addAll(particleList)
                    for (particle in tempList) {
                        if (particle.isDone()) {
                            continue
                        }
                        val iconBitmap = getIconBitmap()
                        val progress = particle.progress()
                        val width = iconBitmap.width * particle.scale
                        val height = iconBitmap.height * particle.scale
                        val x = particle.startX  + progress * (particle.endX - particle.startX) - width / 2
                        val y = particle.startY + progress * (particle.endY - particle.startY) - height / 2
                        val matrix = Matrix().apply { postScale(particle.scale, particle.scale) }
                        val newBitmap = Bitmap.createBitmap(iconBitmap, 0, 0, iconBitmap.width, iconBitmap.height, matrix, true)
                        canvas.drawBitmap(newBitmap, x, y, null)
                        particle.frame += 1
                        if (particle.isDone()) {
                            particleList.remove(particle)
                            Log.e(TAG, "size：${particleList.size}")
                        }
                    }
                    tempList.clear()
                }
                surfaceHolder.unlockCanvasAndPost(canvas)
                Thread.sleep(5)
            }
        }
    }
}