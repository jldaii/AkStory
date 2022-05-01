package com.custom.views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.custom.dp
import com.custom.getAvatar
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
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


//    private var scaleFraction = 0f
//        set(value) {
//            field = value
//            invalidate()
//        }

    /**
     *
     */
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)


    private var henFlingRunner = HenFlingRunner()
    private val scroller = OverScroller(context)
    private val henGestureListener = HenGestureListener()
    private val scaleGestureListener = HenScaleGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, henGestureListener)
    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)


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
        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 使用 gestureDetector 去截取 onTouchEvent  这就是钩子
//        return gestureDetector.onTouchEvent(event)

        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress){
            gestureDetector.onTouchEvent(event)
        }
        return true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 关闭长按
//        gestureDetector.setIsLongpressEnabled(false)
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }


    //    private fun refresh() {
//        if (scroller.computeScrollOffset()) {
//            offsetX = scroller.currX.toFloat()
//            offsetY = scroller.currY.toFloat()
//            invalidate()
//            postOnAnimation({ refresh() })
//        }
//    }


    private fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }


    inner class HenGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX

                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return false
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
//                postOnAnimation(this)
                ViewCompat.postOnAnimation(this@ScalableImageView, henFlingRunner)
            }
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        /**
         * 双击处理方法
         */
        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                offsetX = (e.x - width / 2f) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2f) * (1 - bigScale / smallScale)
                fixOffsets()

                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }

        /**
         * 显示是否被按下
         */
        override fun onShowPress(e: MotionEvent?) {

        }
    }

    inner class HenScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        // 过程
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val tempCurrentScale = currentScale * detector.scaleFactor
            if (tempCurrentScale < smallScale || tempCurrentScale > bigScale){
                return false
            }else{
                currentScale *= detector.scaleFactor
                return true
            }

//            currentScale = currentScale.coerceAtLeast(smallScale).coerceAtMost(bigScale)
//            return true
        }

        // 开始
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = ( detector.focusX - width / 2f) * (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2f) * (1 - bigScale / smallScale)
           return true
        }

        // 事件结束
        override fun onScaleEnd(detector: ScaleGestureDetector) {

        }


    }


    inner class HenFlingRunner : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }
}


