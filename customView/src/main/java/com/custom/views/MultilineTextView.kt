package com.custom.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.custom.dp

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ut nisl congue, facilisis arcu a, malesuada dolor. Nullam semper ligula sit amet pharetra mattis. Suspendisse varius nec ipsum quis dignissim. Aliquam molestie dolor eu lacus rhoncus, a posuere erat vehicula. Etiam in libero non eros tincidunt molestie a a diam. Aliquam erat volutpat. Vivamus nec pretium augue. Maecenas id euismod ante, ut cursus odio. Maecenas aliquet rhoncus elit nec auctor. Phasellus dolor nibh, consectetur ac consectetur in, commodo quis neque. In semper erat in iaculis semper. Vestibulum convallis sapien at tincidunt blandit. Sed non blandit lectus. Suspendisse tincidunt porta leo sed ultricies. Phasellus rutrum, enim eu condimentum vulputate, sapien purus dignissim urna, nec varius diam enim non justo."

    val IMAGE_SIZE = 150.dp
    val IMAGE_PADDING = 50.dp
    val textpaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }

    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }
    private val bitmap = getAvatar(IMAGE_SIZE.toInt())
    private val fontMetrics = Paint.FontMetrics()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        val ststicLayout = StaticLayout(text,textpaint,width,Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
//        ststicLayout.draw(canvas)

        canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)
        paint.getFontMetrics(fontMetrics)
        val measyreWidth = floatArrayOf(0f)


        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float

        while (start < text.length) {
            if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING
                || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE
            ) {
                maxWidth = width.toFloat()
            } else {
                maxWidth = width.toFloat() - IMAGE_SIZE
            }
            count = paint.breakText(text, start, text.length, true, maxWidth, measyreWidth)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }

    }

    fun getAvatar(winth: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = winth
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }

}