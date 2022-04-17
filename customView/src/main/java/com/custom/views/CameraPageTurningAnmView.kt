package com.custom.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.custom.dp

class CameraPageTurningAnmView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val BITMAP_SIZE = 200f.dp
    private val BITMAP_PADDING = 100f.dp

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = getAvatar(BITMAP_SIZE.toInt())
    private val camera = Camera()

    var topFlip = 0f
        set(value) {
            field = value
            // 重绘
            invalidate()
        }
    var bottomFlip = 30f
        set(value) {
            field = value
            // 重绘
            invalidate()
        }
    var flipRotation = 0f
        set(value) {
            field = value
            // 重绘
            invalidate()
        }


    init {
        // 控制投影距离
        camera.setLocation(0f, 0f, -6 * resources.displayMetrics.density)

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
        canvas.withSave {
            canvas.translate((BITMAP_PADDING + BITMAP_SIZE / 2), (BITMAP_PADDING + BITMAP_SIZE / 2))
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-BITMAP_SIZE, -BITMAP_SIZE, BITMAP_SIZE, 0f)
            canvas.rotate(flipRotation)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_SIZE / 2),
                -(BITMAP_PADDING + BITMAP_SIZE / 2)
            )
            canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        }

        // 下半部分
        canvas.withSave {
            canvas.translate((BITMAP_PADDING + BITMAP_SIZE / 2), (BITMAP_PADDING + BITMAP_SIZE / 2))
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(bottomFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas.clipRect(-BITMAP_SIZE, 0f, BITMAP_SIZE, BITMAP_SIZE)
            canvas.rotate(flipRotation)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_SIZE / 2),
                -(BITMAP_PADDING + BITMAP_SIZE / 2)
            )
            canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
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