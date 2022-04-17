package com.custom.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.custom.views.DrawableView
import com.custom.views.R

class ShowDrawableAndBitmapActivity : AppCompatActivity() {

    val vDraw by lazy {
        findViewById<DrawableView>(R.id.v_draw)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_drawable_and_bitmap)
    }
}