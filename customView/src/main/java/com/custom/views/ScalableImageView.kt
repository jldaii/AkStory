package com.custom.views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.custom.dp
import com.custom.getAvatar
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private val IMAGE_SIZE = 300.dp.toInt()

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE)

    // 初始偏移
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    // 额外偏移
    private var offsetX = 0f
    private var offsetY = 0f

    private var smallScale = 0f
    private var bigScale = 0f
    var big = false

    // 额外放缩技术
    val EXTRA_SCALE_FRACTOR = 1.5f


    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    private val scroller = OverScroller(context)

    private val gestureDetector = GestureDetectorCompat(context, this).apply {
        setOnDoubleTapListener(this@ScalableImageView)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - IMAGE_SIZE) / 2f
        originalOffsetY = (height - IMAGE_SIZE) / 2f

        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALE_FRACTOR
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALE_FRACTOR

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 使用 gestureDetector 去截取 onTouchEvent  这就是钩子
        return gestureDetector.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 关闭长按
//        gestureDetector.setIsLongpressEnabled(false)

        canvas.translate(offsetX, offsetY)
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    /**
     * 显示是否被按下
     */
    override fun onShowPress(e: MotionEvent?) {

    }

    /**
     *
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (big) {
            offsetX -= distanceX
            offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
            offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
            offsetY -= distanceY
            offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
            offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
            invalidate()
        }
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (big) {
            scroller.fling(
                offsetX.toInt(),
                offsetY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                (-(bitmap.width * bigScale - width) / 2).toInt(),
                ((bitmap.width * bigScale - width) / 2).toInt(),
                (-(bitmap.height * bigScale - height) / 2).toInt(),
                ((bitmap.height * bigScale - height) / 2).toInt(),
//                40.dp.toInt(), 40.dp.toInt()
            )
//            for (i in 10..100 step 10) {
//                // 每10ms 刷新一次
//                postDelayed({ refresh() }, i.toLong())
//            }
            postOnAnimation(this)
        }
        return false
    }

    //    private fun refresh() {
//        if (scroller.computeScrollOffset()) {
//            offsetX = scroller.currX.toFloat()
//            offsetY = scroller.currY.toFloat()
//            invalidate()
//            postOnAnimation({ refresh() })
//        }
//    }
    override fun run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            postOnAnimation(this)
        }
    }

    /**
     * 双击处理方法
     */
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        big = !big
        if (big) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * 不是双击才会触发
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }


}