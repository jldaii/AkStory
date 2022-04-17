package com.custom.views.rwx

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.custom.dp
import com.custom.views.R


class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    /**
     * 文字大小
     */
    val TEXT_SIZE = 12.dp

    /**
     * 间距
     */
    val TEXT_MARGIN = 8.dp

    val HORIZONTAL_OFFSET = 5.dp

    val VERTICAL_OFFSET = 18.dp

    val EXTRA_VERTICAL_OFFSET = 16.dp

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var floatingLabelShown = false

    /**
     * 动画完成度
     */
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    val animatorReverse by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f)
    }

    val animator by lazy {
//        ObjectAnimator.ofFloat(this,"floatingLabelFraction",1f)
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
    }


    var useFloatingLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }

        }


    init {
        paint.textSize = TEXT_SIZE


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel =
            typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true)
        typedArray.recycle()
        if (useFloatingLabel) {
            setPadding(
                paddingLeft,
                (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                paddingRight,
                paddingBottom
            )
        }
    }


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (floatingLabelShown && text.isNullOrEmpty()) {
            // 原本没有文本 并且文本为空
            floatingLabelShown = false
//            animatorReverse.start()
            // 反向执行动画
            animator.reverse()
        } else if (!floatingLabelShown && !text.isNullOrEmpty()) {
            // 原本有文本 并且文本不为空
            floatingLabelShown = true
            animator.start()
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatingLabelFraction * 0xff).toInt()
        val currentVertivalValue =
            VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, currentVertivalValue, paint)
//        if (!text.isNullOrEmpty()) {
//
//        }


    }


}