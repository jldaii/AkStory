package com.custom.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.custom.dp
import com.custom.drawable.MeshDrawable

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    //    val  drawable = ColorDrawable(Color.RED)
    val drawable = MeshDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawable.setBounds(50.dp.toInt(), 80.dp.toInt(), width, height)
        drawable.draw(canvas)
    }

}