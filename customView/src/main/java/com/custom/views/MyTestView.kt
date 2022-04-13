package com.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.custom.dp

// 半径


class MyTestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val RADIUS = 100f.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.fillType = Path.FillType.EVEN_ODD
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        /**
         * 左上角 X坐标  = 宽度的一半 减去 圆的半径 就是圆中间
         * 左上角 Y坐标 = 高度的一半
         * 宽度
         * 高度 高度的一半 + 2*半径
         * 绘制方向
         * 矩形api就是左上角的点和右下角的点 来确定一个矩形
         */
        path.addRect(
            width / 2f - RADIUS,
            height / 2f,
            width / 2f + RADIUS,
            height / 2f + 2 * RADIUS,
            Path.Direction.CW
        )

        path.addCircle(width / 2f, height / 2f, RADIUS * 1.5f, Path.Direction.CCW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        paint.color = context.getColor(R.color.black)
//        canvas.drawLine(100f, 100f, 200f, 200f, paint)
//        paint.color = context.getColor(R.color.purple_200)
//        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)
        canvas.drawPath(path, paint)

    }

}