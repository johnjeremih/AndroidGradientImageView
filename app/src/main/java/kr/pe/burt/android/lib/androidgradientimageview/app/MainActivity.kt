package kr.pe.burt.android.lib.androidgradientimageview.app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import kotlinx.android.synthetic.main.activity_main_agassi.*

class MainActivity : AppCompatActivity() {


    internal var animationIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_agassi)
    }

    override fun onResume() {
        super.onResume()
        rotateAnimation()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            animationIndex = animationIndex % 7
            when (animationIndex) {
                0 -> showUpAnimation()
                1 -> alphaAnimation()
                2 -> widthAnimation()
                3 -> startXAnimation()
                4 -> startYAnimation()
                5 -> rotateAnimation()
                6 -> heightAnimation()
                else -> showUpAnimation()
            }
            animationIndex++
        }
        return super.onTouchEvent(event)
    }

    private fun rotateAnimation() {
        val animator = ObjectAnimator.ofFloat(gradientImageView, "rotate", 0.0f, 359.0f)
        animator.duration = 1000
        animator.start()
    }

    private fun startXAnimation() {
        val animator = ObjectAnimator.ofFloat(gradientImageView, "startX", 1.0f, 0.0f)
        animator.duration = 1000
        animator.start()
    }

    private fun startYAnimation() {
        val animator = ObjectAnimator.ofFloat(gradientImageView, "startY", 1.0f, 0.0f)
        animator.duration = 1000
        animator.start()
    }

    private fun widthAnimation() {
        val animator = ObjectAnimator.ofFloat(gradientImageView, "widthRatio", 1.0f, 0.0f, 1.0f)
        animator.duration = 1000
        animator.start()
    }

    private fun heightAnimation() {
        val animator = ObjectAnimator.ofFloat(gradientImageView, "heightRatio", 1.0f, 0.0f, 1.0f)
        animator.duration = 1000
        animator.start()
    }

    private fun alphaAnimation() {
        val animator = ObjectAnimator.ofFloat(gradientImageView, "gradientAlpha", 1.0f, 0.0f, 1.0f)
        animator.duration = 1000
        animator.start()
    }

    private fun showUpAnimation() {
        val animator1 = ObjectAnimator.ofFloat(gradientImageView, "startY", 1.0f, 0.0f)
        val animator2 = ObjectAnimator.ofFloat(gradientImageView, "gradientAlpha", 0.0f, 1.0f)
        val set = AnimatorSet()
        set.playTogether(animator1, animator2)
        set.duration = 1000
        set.interpolator = AccelerateInterpolator()

        val animator3 = ObjectAnimator.ofFloat(title_textView, "alpha", 0f, 1.0f)
        val animator4 = ObjectAnimator.ofFloat(desc_textView, "alpha", 0f, 1.0f)

        val set2 = AnimatorSet()
        set2.playTogether(animator3, animator4)
        set2.duration = 1000
        set2.interpolator = AccelerateInterpolator()

        val set3 = AnimatorSet()
        set3.playSequentially(set, set2)
        set3.start()
    }
}