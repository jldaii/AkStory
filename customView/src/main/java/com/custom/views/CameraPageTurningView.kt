package com.custom.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.custom.dp

class CameraPageTurningView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val BITMAP_SIZE = 200f.dp
    private val BITMAP_PADDING = 100f.dp

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = getAvatar(BITMAP_SIZE.toInt ())
    private val camera = Camera()

    init {
        camera.rotateX(30f)
        // 控制投影距离
        camera.setLocation(0f,0f,-3 * resources.displayMetrics.density)

    }

    private val clipped = Path().apply {
        addOval(
            BITMAP_PADDING,
            BITMAP_PADDING,
            BITMAP_PADDING + BITMAP_SIZE,
            BITMAP_PADDING + BITMAP_SIZE,
            Path.Direction.CCW
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 上半部分
        canvas.save()
        canvas.translate((BITMAP_PADDING + BITMAP_SIZE/2),(BITMAP_PADDING + BITMAP_SIZE/2))

        canvas.clipRect(-BITMAP_SIZE/2,-BITMAP_SIZE/2,BITMAP_SIZE/2,0f)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE/2),-(BITMAP_PADDING + BITMAP_SIZE/2))
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()

        // 下半部分
        canvas.save()
        canvas.translate((BITMAP_PADDING + BITMAP_SIZE/2),(BITMAP_PADDING + BITMAP_SIZE/2))
        camera.applyToCanvas(canvas)
        canvas.clipRect(-BITMAP_SIZE/2,0f,BITMAP_SIZE/2,BITMAP_SIZE/2)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE/2),-(BITMAP_PADDING + BITMAP_SIZE/2))
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()

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