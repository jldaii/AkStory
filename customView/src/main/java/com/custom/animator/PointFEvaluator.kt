package com.custom.animator
import android.animation.TypeEvaluator
import android.graphics.PointF

class PointFEvaluator : TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val startX = startValue.x
        val endX = endValue.x
        val currentX = startX + (endX - startX) * fraction
        val startY = startValue.y
        val endY = endValue.y
        val currentY = startY + (endY - startY) * fraction
        return PointF(currentX, currentY)
    }
}