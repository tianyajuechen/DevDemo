package com.tzy.demo.activity.wallpaper

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * @Author tangzhaoyang
 * @Date 2023/2/1
 * 控制box2d 重力感应逻辑
 */
class Box2dSensorLogic {
    private val manager: SensorManager
    private val sensor: Sensor
    private val listener: SensorEventListener
    var isPortrait = true

    constructor(world: World, context: Context) {
        //获得重力感应硬件控制器
        manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[SensorManager.DATA_X]
                val y = event.values[SensorManager.DATA_Y]
                val gravityX: Float
                val gravityY: Float
                if (isPortrait) {
                    gravityX = -5 * x
                    gravityY = -5 * y
                } else {
                    gravityX = 5F * y
                    gravityY = if (x >= -1) -30F else -x * 0.5F
                }
//                Log.e("sensor", "x1=$x x2=$gravityX y1=$y y2=$gravityY")
                world.gravity = Vector2(gravityX, gravityY)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
    }

    fun release() {
        manager.unregisterListener(listener)
    }

    fun startListener() {
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListener() {
        manager.unregisterListener(listener)
    }
}