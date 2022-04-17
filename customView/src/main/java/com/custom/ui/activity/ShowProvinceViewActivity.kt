package com.custom.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.custom.dp
import com.custom.views.ProvinceEvaluator
import com.custom.views.R
import kotlinx.android.synthetic.main.activity_show_province_view.*

class ShowProvinceViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_province_view)

        val animator = ObjectAnimator.ofObject(view, "province", ProvinceEvaluator(), "澳门特别行政区")
        animator.startDelay = 1000
        animator.duration = 20000
        animator.start()

        view.animate()
            .translationY(200.dp)
            .withLayer()
    }
}