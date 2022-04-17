package com.custom.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.custom.dp


class DashboardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // 半径
    val RADIUS = 150f.dp

    // 线条长度
    val LENGTH = 120f.dp

    // 开角
    val OPEN_ANGLE = 120

    // 刻度的宽
    val DASH_WIDTH = 2f.dp

    // 刻度矩形的长
    val DASH_LENGTH = 10f.dp


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val dash = Path()

    private val path = Path()

    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.dp
        // 画笔样式 描边
        paint.style = Paint.Style.STROKE
        /**
         * 这里每个矩形的起始坐标都为 (0,0)
         * 是因为这里在绘制的时候，是会设置给 paint PathDashPathEffect
         * 配合 path （画圆）使用
         * 所以 起始坐标为(0,0) 是绘制的时候 每次绘制相对于圆弧的点的位置 方向是朝向圆心
         * 所以这里参考的X和Y轴，是以圆弧path上的任意一点，作为原点，以圆心作为Y轴的正方向，
         * 以绘制方向并与Y轴90°垂直的方向， 作为X轴正方向
         *
         * 所以 添加刻度的方法是
         * 在绘制完圆弧以后，依旧按照圆弧的路线
         * 设置一个矩形的样式给画笔 paint，然后绘制路径就会以修饰后的画笔样式进行绘制
         * 需要初始化一个 PathDashPathEffect 设置给 paint
         */
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        /**
         * left 和 top 确定起始点 坐标（x,y）
         * right 和 bottom 确定终点坐标 x,y）
         * startAngle 起始角度 从哪个角度开始
         * sweepAngle 最终角度
         * 起始角度和最终角度 确定了覆盖的范围
         */
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360f - OPEN_ANGLE
        )
        /**
         * 路径的长度
         * path 路径
         * forceClosed 是否闭合
         */
        val pathMeasure = PathMeasure(path, false)
        /**
         * 虚线
         * shape 指的填充图形 也是路径
         * advance 是每个图形间的间隔 如果不设置 每次都会在同一个位置绘制
         * phase  则为绘制时的偏移量
         * style则是该类自由的枚举值，有三种情况：ROTATE、MORPH和TRANSLATE
         * ROTATE情况下：线段连接处的图形转换以旋转到与下一段移动方向相一致的角度进行连接
         * MORPH情况下：图形会以发生拉伸或压缩等变形的情况与下一段相连接
         * TRANSLATE情况下：图形会以位置平移的方式与下一段相连接
         * https://www.jianshu.com/p/4ef24f2c15b5
         */
        pathEffect = PathDashPathEffect(
            dash, (pathMeasure.length - DASH_WIDTH) / 20f, 0f,
            PathDashPathEffect.Style.ROTATE
        )


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画弧
        canvas.drawPath(path, paint)
        /**
         * 这里画刻度
         * 通过指定画笔的主题
         */
        paint.pathEffect = pathEffect

        /**
         * 使用一个持有主题效果的画笔 再画一个圆
         */
        canvas.drawPath(path, paint)
        paint.pathEffect = null
        /**
         * startX startY 起始的坐标 这里取的屏幕中心 也就是圆心
         *
         * stopX  屏幕X轴中心 + LENGTH * cos(270π/180)
         * stopY  屏幕Y轴中心 + LENGTH * sin（270π/180）
         */
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + LENGTH * Math.cos(markToRadians(10)).toFloat(),
            height / 2f + LENGTH * Math.sin(markToRadians(10)).toFloat(),
            paint
        )
    }

    private fun markToRadians(mark: Int): Double {
        /**
         * Math.toRadians() 将角度转换为弧度 需要传入角度值
         * 90 + 120/2 + (360 -120)/20 * 10
         * 90 + 60 + 120 = 270
         * 这里角度转弧度
         * 1弧度=180/π 度 1度=π/180 弧度
         * 最后得到的弧度 是 270π/180 ≈ 4.7123...
         *
         * 对当前图形的实际值做一次说明
         * OPEN_ANGLE = 120
         * 90 + OPEN_ANGLE / 2f 得到的是起始角度 也就是 150
         * (360 - OPEN_ANGLE) / 20f 得到的是 每个刻度的平均的锐(360 - OPEN_ANGLE) / 20f角度数
         * (360 - OPEN_ANGLE) / 20f * mark 就是当前mark所谓的角度
         */
        return Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * mark).toDouble())
    }

}