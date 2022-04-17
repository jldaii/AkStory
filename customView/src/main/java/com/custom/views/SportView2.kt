package com.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.custom.dp


class SportView2(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
    private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
    private val RING_WIDTH = 20.dp
    private val RADIUS = 150.dp

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp
        typeface = ResourcesCompat.getFont(context, R.font.font)
        textAlign = Paint.Align.CENTER
    }

    val bounds = Rect()
    val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 绘制进度条
        paint.color = HIGHLIGHT_COLOR
        // 设置线条两端的形状
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )

        /**
         * 绘制文字
         */
        paint.textSize = 100.dp
        paint.style = Paint.Style.FILL
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(
            "aaaa",
            width / 2f,
            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f,
            paint
        )
        /**
         * 动态文字
         * 绘制文字2
         */
        paint.textSize = 150.dp
        paint.textAlign = Paint.Align.LEFT
        paint.getFontMetrics(fontMetrics)
        paint.getTextBounds("abcd", 0, "abcd".length, bounds)
        canvas.drawText("abcd", -bounds.left.toFloat(), -bounds.top.toFloat(), paint)

        /**
         * 绘制文字3
         */
        paint.textSize = 15.dp
        paint.getTextBounds("abcd", 0, "abcd".length, bounds)
        canvas.drawText("abcd", -bounds.left.toFloat(), -bounds.top.toFloat(), paint)


    }

}
