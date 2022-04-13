package com.custom.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.custom.dp


class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // 半径
    val RADIUS = 150f.dp


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 角度
     */
    val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)

    val colors = listOf(
        Color.parseColor("#C2185B"),
        Color.parseColor("#00ACC1"),
        Color.parseColor("#558B2F"),
        Color.parseColor("#5D4097"),
    )

    val OFFSET_LENGTH = 20f


    private val path = Path()

    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.dp
        // 画笔样式 描边
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = colors[index]
            if (index == 2) {
                canvas.save()
                canvas.translate(
                    (OFFSET_LENGTH * Math.cos(Math.toRadians(startAngle + angle / 2f.toDouble()))).toFloat(),
                    (OFFSET_LENGTH * Math.sin(Math.toRadians(startAngle + angle / 2f.toDouble()))).toFloat(),
                )
            }

            canvas.drawArc(
                width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                angle,
                true,
                paint
            )
            startAngle += angle
            if (index == 2) {
                canvas.restore()
            }

        }
    }
}