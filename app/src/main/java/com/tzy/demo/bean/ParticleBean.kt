package com.tzy.demo.bean

data class ParticleBean(
    var startX: Float,
    var startY: Float,
    var endX: Float,
    var endY: Float,
    var scale: Float = 1F,
    var frame: Int = 1
) {
    private val maxFrame = 100//最大可绘制帧数

    fun progress(): Float {
        return frame / maxFrame.toFloat()
    }

    fun isDone(): Boolean {
        return frame > maxFrame
    }
}
