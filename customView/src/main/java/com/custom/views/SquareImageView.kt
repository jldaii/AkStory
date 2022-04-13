package com.custom.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.custom.dp
import kotlin.math.min
import kotlin.math.roundToInt

class SquareImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = min(measuredWidth,measuredHeight)


    }
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, (l+100.dp).toInt(), (t+100.dp).toInt())
    }

}