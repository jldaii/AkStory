package com.custom.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.graphics.toColorInt
import com.custom.dp

class MeshDrawable : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = "#F9A825".toColorInt()
            strokeWidth = 5.dp
        }

    private val INTERVAL = 50.dp

    override fun draw(canvas: Canvas) {
        var x = bounds.left.toFloat()
        while (x <= bounds.right) {
            canvas.drawLine(
                x,
                bounds.top.toFloat(),
                x,
                bounds.bottom.toFloat(),
                paint
            )
            x += INTERVAL
        }
        var y = bounds.top.toFloat()
        while (y <= bounds.bottom) {
            canvas.drawLine(
                bounds.left.toFloat(),
                y,
                bounds.right.toFloat(),
                y,
                paint
            )
            y += INTERVAL
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0 -> PixelFormat.TRANSPARENT
            0xff -> PixelFormat.OPAQUE
            else -> PixelFormat.TRANSLUCENT
        }
    }
}