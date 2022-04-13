package com.custom.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.custom.dp

private val IMAGE_WIDTH = 200f.dp
private val IMAGE_PADDING = 20f.dp
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bounds = RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH)



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = canvas.saveLayer(bounds, null)
        canvas.drawOval(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)

    }


    fun getAvatar(winth: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth
        options.inTargetDensity = winth
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }

}