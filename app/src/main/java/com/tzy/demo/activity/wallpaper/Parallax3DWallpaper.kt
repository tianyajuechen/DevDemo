package com.tzy.demo.activity.wallpaper

import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory.Options
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import android.view.WindowManager

class Parallax3DWallpaper : WallpaperService() {

    companion object {
        const val TAG = "Wallpaper"
    }

    override fun onCreateEngine(): Engine {
        return MyEngin(this)
    }

    inner class MyEngin(private val context: Context) : Engine(), SensorEventListener, Runnable {

        /**
         * 图片处理方式
         * 0：原图中心区域取高度80%，宽高比为屏幕宽高比，上下左右最大偏移量为原图高度的10%
         * 1：原图高度放大为屏幕高度1.2倍，宽度等比放大，从图片中心区域取屏幕宽高尺寸的图片，上下左右最大偏移量为屏幕高度10%
         */
        private val drawMode = 0
        private var bitmap1: Bitmap? = null //底部图片
        private var bitmap2: Bitmap? = null //中间图片
        private var bitmap3: Bitmap? = null //顶部图片
        private var gravityX = 0F //重力感应x值
        private var gravityY = 0F //重力感应y值
        private val xFilter = SmoothFilter()
        private val yFilter = SmoothFilter()
        private var width = 1
        private var height = 1
        private var maxOffsetPx1 = 0 //底层图片最大可偏移像素
        private var maxOffsetPx2 = 0 //中间图片最大可偏移像素
        private var maxOffsetPx3 = 0 //最上面图片最大可偏移像素
        private var canDraw = false
        private var deltaTime = 16 //刷新时间，单位毫秒
        val handler = Handler(Looper.getMainLooper())

        private lateinit var manager: SensorManager
        private lateinit var sensor: Sensor
        private lateinit var drawThread: Thread

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = manager.getDefaultSensor(Sensor.TYPE_GRAVITY)
            drawThread = Thread(this)
        }

        override fun onDestroy() {
            super.onDestroy()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                startListener()
            } else {
                stopListener()
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            Log.e("RefreshRate", windowManager.defaultDisplay.supportedRefreshRates.contentToString())
            width = holder.surfaceFrame.width()
            height = holder.surfaceFrame.height()
            if (drawMode == 0) {
                bitmap1 = createBitmap("parallax3d/sikadi_1.png")
                bitmap2 = createBitmap("parallax3d/sikadi_2.png")
                bitmap3 = createBitmap("parallax3d/sikadi_3.png")
                maxOffsetPx2 = (bitmap1!!.height * 0.06F).toInt()
            } else {
                bitmap1 = createBitmap2("parallax3d/sikadi_1.png")
                bitmap2 = createBitmap2("parallax3d/sikadi_2.png")
                bitmap3 = createBitmap2("parallax3d/sikadi_3.png")
                maxOffsetPx2 = (bitmap1!!.height * 0.05F).toInt()
            }
            maxOffsetPx1 = (maxOffsetPx2 * 1.4F).toInt()
            maxOffsetPx3 = (maxOffsetPx2 * 0.8F).toInt()
            canDraw = true
            drawThread.start()
//            draw()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            canDraw = false
            xFilter.reset()
            yFilter.reset()
            bitmap1?.recycle()
            bitmap2?.recycle()
            bitmap3?.recycle()

        }

        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[SensorManager.DATA_X]
            val y = event.values[SensorManager.DATA_Y]
            gravityX = xFilter.averageData(x)
            gravityY = yFilter.averageData(y)
//            gravityX = xFilter.lowPassData(x)
//            gravityY = yFilter.lowPassData(y)
//            Log.e("sensor", "x1:$x x2:$gravityX y1:$y y2:$gravityY")
//            if (canDraw) {
//                draw()
//            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun run() {
            while (true) {
                draw()
                Thread.sleep(deltaTime.toLong())
            }
        }

        /**
         * 创建bitmap，缩放ratio倍
         */
        private fun createBitmap(path: String): Bitmap {
            val options = Options().apply { inPreferredConfig = Bitmap.Config.RGB_565 }
            return BitmapFactory.decodeStream(resources.assets.open(path))
        }

        private fun createBitmap2(path: String): Bitmap {
            val bitmap = BitmapFactory.decodeStream(resources.assets.open(path))
            //最终的图片高度应该为屏幕高度1.2倍，宽度等比放大
            val scale = height * 1.2F / bitmap.height
            val matrix = Matrix()
            matrix.setScale(scale, scale)
            val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            return newBitmap
        }

        private fun draw() {
            if (!isVisible) return
            val canvas = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                surfaceHolder.lockHardwareCanvas()
            } else {
                surfaceHolder.lockCanvas()
            }
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            bitmap1?.let {
                drawBitmap(it, maxOffsetPx1, canvas)
            }
            bitmap2?.let {
                drawBitmap(it, maxOffsetPx2, canvas)
            }
            bitmap3?.let {
                drawBitmap(it, maxOffsetPx3, canvas)
            }
            surfaceHolder.unlockCanvasAndPost(canvas)
        }

        private fun drawBitmap(source: Bitmap, maxOffset: Int, canvas: Canvas) {
            if (drawMode == 0) {
                drawBitmap1(source, maxOffset, canvas)
            } else {
                drawBitmap2(source, maxOffset, canvas)
            }
        }

        /**
         * 从bitmap中心区域选取高度80%，宽高比和view一致的尺寸
         * 然后根据传感器数值偏移
         * @param source 要绘制的bitmap
         * @param maxOffset 最大可偏移的尺寸px
         * @param canvas
         */
        private fun drawBitmap1(source: Bitmap, maxOffset: Int, canvas: Canvas) {
            val scale = width.toFloat() / height
            val newHeight = (source.height * 0.83F).toInt() //定义绘制区域为bitmap高度的80%
            val newWidth = (newHeight * scale).toInt() //根据view宽高比算出宽度
            val xOffset = gravityX / 10 * maxOffset
            val yOffset = gravityY / 10 * maxOffset
            val top = (source.height * 0.085F + yOffset).toInt()
            val left = ((source.width - newWidth) / 2 - xOffset).toInt()
//            Log.e("offset", "x:$xOffset y:$yOffset")
            val rect = Rect(left, top, left + newWidth, top + newHeight)
            val rect2 = Rect(0, 0, width, height)
            canvas.drawBitmap(source, rect, rect2, null)
        }

        /**
         * 从bitmap中心区域选取view的尺寸
         * 然后根据传感器数值偏移
         * @param source 要绘制的bitmap
         * @param maxOffset 最大可偏移的尺寸px
         * @param canvas
         */
        private fun drawBitmap2(source: Bitmap, maxOffset: Int, canvas: Canvas) {
            val xOffset = gravityX / 10 * maxOffset
            val yOffset = gravityY / 10 * maxOffset
            val left = ((source.width - width) / 2F - xOffset).toInt()
//            val top = (source.height * 0.045 + yOffset).toInt()
            val top = ((source.height - height) / 2F + yOffset).toInt()
            Log.e("offset", "x:$xOffset y:$yOffset")
            val rect = Rect(left, top, left + width, top + height)
            val rect2 = Rect(0, 0, width, height)
            canvas.drawBitmap(source, rect, rect2, null)
        }

        private fun startListener() {
            manager.registerListener(this, sensor, deltaTime * 1000)
//            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        private fun stopListener() {
            manager.unregisterListener(this)
        }
    }
}