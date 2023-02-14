package com.tzy.demo.activity.wallpaper

/**
 * @Author tangzhaoyang
 * @Date 2023/2/9
 * 平滑滤波器
 */
class SmoothFilter {
    private val size = 5
    private var data = FloatArray(size)
    private var index = 0
    private var sum = 0F

    private var lastData = 0F
    private val alpha = 0.5F //滤波系数

    /**
     * 滑动平均算法
     */
    fun averageData(value: Float): Float {
        sum -= data[index]
        sum += value
        data[index] = value
        index = (index + 1) % size
        return sum / size
    }

    fun lowPassData(value: Float): Float {
        lastData = alpha * value + (1 - alpha) * lastData
        return lastData
    }

    fun reset() {
        index = 0
        sum = 0F
        data = FloatArray(10)

        lastData = 0F
    }
}