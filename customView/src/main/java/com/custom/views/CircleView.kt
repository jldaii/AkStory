package com.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.custom.dp

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val RADIUS = 100.dp
    val PADDING = 100.dp

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((PADDING + RADIUS) * 2).toInt()
        val width = resolveSize(size,widthMeasureSpec)
        val height = resolveSize(size,heightMeasureSpec)
        setMeasuredDimension(width,height)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(PADDING + RADIUS,PADDING + RADIUS,RADIUS, paint)
    }
}