package kr.pe.burt.android.lib.androidgradientimageview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * Created by burt on 2016. 6. 13..
 */
class AndroidGradientImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var startX = 0f
    private var startY = 0f
    private var widthRatio = 1.0f
    private var heightRatio = 1.0f
    private var rotate = 0.0f
    private var startColor = Color.parseColor("#00000000")
    private var endColor = Color.parseColor("#FF000000")
    private var middleColor = -1
    private var gradientAlpha = 1.0f
    private var startOffset = 0.0f
    private var endOffset = 1.0f
    private var middleOffset = 0.5f

    internal var colors: IntArray? = null
    internal var offsets: FloatArray? = null
    internal var gradient: Shader? = null
    internal var rotateMatrix: Matrix? = null
    internal var gradientPaint: Paint? = null

    init {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs == null)
            return

        val array = context.obtainStyledAttributes(attrs, R.styleable.AndroidGradientImageViewAttrs)

        startX = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_x, startX)
        startY = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_y, startY)
        widthRatio = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_width, widthRatio)
        heightRatio = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_height, heightRatio)
        rotate = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_rotate, rotate)

        startColor = array.getColor(R.styleable.AndroidGradientImageViewAttrs_giv_startColor, startColor)
        endColor = array.getColor(R.styleable.AndroidGradientImageViewAttrs_giv_endColor, endColor)
        middleColor = array.getColor(R.styleable.AndroidGradientImageViewAttrs_giv_middleColor, middleColor)

        startOffset = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_startOffset, startOffset)
        endOffset = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_endOffset, endOffset)
        middleOffset = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_middleOffset, middleOffset)

        gradientAlpha = array.getFloat(R.styleable.AndroidGradientImageViewAttrs_giv_alpha, gradientAlpha)

        array.recycle()

        if (middleColor == -1) {
            colors = intArrayOf(startColor, endColor)
            offsets = floatArrayOf(startOffset, endOffset)
        } else {
            colors = intArrayOf(startColor, middleColor, endColor)
            offsets = floatArrayOf(startOffset, middleOffset, endOffset)
        }

        gradientPaint = Paint()
        rotateMatrix = Matrix()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val left = startX * width
        val top = startY * height

        val right = left + widthRatio * width
        val bottom = top + heightRatio * height

        if (gradient == null) {
            gradient = LinearGradient(
                    left, top,
                    right, bottom,
                    colors!!,
                    offsets,
                    Shader.TileMode.CLAMP)
        }
        rotateMatrix!!.setRotate(rotate, (width / 2).toFloat(), (height / 2).toFloat())
        gradient!!.setLocalMatrix(rotateMatrix)
        gradientPaint!!.shader = gradient
        gradientPaint!!.alpha = (gradientAlpha * 255).toInt()
        canvas.drawRect(left, top, right, bottom, gradientPaint!!)
    }

    /**
     * Provide get/set methods for Property Animation
     */
    fun getRotate(): Float {
        return rotate
    }

    fun setRotate(rotate: Float) {
        this.rotate = rotate
        gradient = null
        postInvalidate()
    }

    fun getStartX(): Float {
        return startX
    }

    fun setStartX(startX: Float) {
        this.startX = startX
        gradient = null
        postInvalidate()
    }

    fun getStartY(): Float {
        return startY
    }

    fun setStartY(startY: Float) {
        this.startY = startY
        gradient = null
        postInvalidate()
    }

    fun getWidthRatio(): Float {
        return widthRatio
    }

    fun setWidthRatio(widthRatio: Float) {
        this.widthRatio = widthRatio
        gradient = null
        postInvalidate()
    }

    fun getHeightRatio(): Float {
        return heightRatio
    }

    fun setHeightRatio(heightRatio: Float) {
        this.heightRatio = heightRatio
        gradient = null
        postInvalidate()
    }

    fun getGradientAlpha(): Float {
        return gradientAlpha
    }

    fun setGradientAlpha(gradientAlpha: Float) {
        this.gradientAlpha = gradientAlpha
        postInvalidate()
    }
}
