package com.custom.ui.activity

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.custom.dp
import com.custom.views.CameraPageTurningAnmView
import com.custom.views.R

class ShowCustomViewActivity : AppCompatActivity() {
    val a: String? = null

    val view: CameraPageTurningAnmView by lazy {
        findViewById(R.id.view)
    }

    private val ivView: ImageView by lazy {
        findViewById(R.id.iv_view)
    }

    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_custom_view)
        supportActionBar(toolbar)
        supportActionBar?.let {
//            setDisplayHomeAsUpEnabled(true)
        }

        showKeyframe()
    }

    private fun supportActionBar(toolbar: Toolbar) {

    }


    private fun showKeyframe() {
        val length = 200.dp
        val keyframe1 = Keyframe.ofFloat(0f, 0f)
        val keyframe2 = Keyframe.ofFloat(0.2f, 0.4f * length)
        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
        val keyframe4 = Keyframe.ofFloat(1f, 1f * length)
        val keyFrameHolder = PropertyValuesHolder.ofKeyframe(
            "translationX",
            keyframe1,
            keyframe2,
            keyframe3,
            keyframe4
        )
        val animator = ObjectAnimator.ofPropertyValuesHolder(ivView, keyFrameHolder)
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()
    }


    private fun toBottomFlipAnimator(): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "bottomFlip", 60f)
            .apply {
                startDelay = 1000
                duration = 1000
            }

    }

    private fun toFlipRotationAnimator(): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "flipRotation", 270f)
            .apply {
                startDelay = 200
                duration = 1000
            }
    }

    private fun toShowTopFlipAnimator(): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "topFlip", -60f)
            .apply {
                startDelay = 200
                duration = 1000
            }
    }

    private fun toShowAnimatorSet(): AnimatorSet {
        return AnimatorSet().apply {
            playSequentially(
                toBottomFlipAnimator(),
                toFlipRotationAnimator(),
                toShowTopFlipAnimator()
            )
        }
    }

    private fun toShowProperty() {
        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
        val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
        val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -60f)
        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            bottomFlipHolder,
            flipRotationHolder,
            topFlipHolder
        ).apply {
            startDelay = 1000
            duration = 1000
        }
        holderAnimator.start()
    }


}