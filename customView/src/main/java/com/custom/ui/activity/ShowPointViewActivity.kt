package com.custom.ui.activity

import android.animation.ObjectAnimator
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.custom.animator.PointFEvaluator
import com.custom.dp
import com.custom.views.R
import kotlinx.android.synthetic.main.activity_show_point_view.*

class ShowPointViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_point_view)

        val animator = ObjectAnimator.ofObject(view, "point", PointFEvaluator(), PointF(100.dp, 200.dp))
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()
    }
}