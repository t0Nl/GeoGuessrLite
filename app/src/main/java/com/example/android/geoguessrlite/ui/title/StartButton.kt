package com.example.android.geoguessrlite.ui.title

import android.animation.ValueAnimator
import android.animation.ValueAnimator.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import com.example.android.geoguessrlite.R

private const val INNER_CIRCLE_ALPHA = 100
private const val INNER_CIRCLE_ANIMATION_DURATION = 700L

private const val OUTER_CIRCLE_ALPHA = 100
private const val OUTER_CIRCLE_ANIMATION_DURATION = 1000L

@SuppressLint("ResourceType")
class StartButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var buttonColor: Int = androidx.appcompat.R.attr.colorPrimary
    private var buttonTextColor: Int = androidx.appcompat.R.attr.colorPrimary

    private var innerCircleValueAnimator = ValueAnimator()
    private var outerCircleValueAnimator = ValueAnimator()

    private val buttonTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 75.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = buttonTextColor
    }

    private val innerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = buttonColor
    }

    private val outerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = buttonColor
    }

    init {
        isClickable = true

        context.withStyledAttributes(attrs, R.styleable.StartButton) {
            buttonColor = getColor(R.styleable.StartButton_buttonColor, buttonColor)
            buttonTextColor = getColor(R.styleable.StartButton_buttonTextColor, buttonColor)
        }

        innerCirclePaint.color = buttonColor
        innerCirclePaint.alpha = INNER_CIRCLE_ALPHA

        outerCirclePaint.color = buttonColor
        outerCirclePaint.alpha = OUTER_CIRCLE_ALPHA

        animateInnerCircle()
        animateOuterCircle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val innerCircleSize = innerCircleValueAnimator.animatedValue?.let {
            getInnerCircleRadius(it as Float)
        } ?: 0f

        val outerCircleSize = outerCircleValueAnimator.animatedValue?.let {
            getOuterCircleRadius(it as Float)
        } ?: 0f

        // Two overlying circles
        canvas?.drawCircle(
            widthSize / 2f,
            heightSize / 2f,
            innerCircleSize,
            innerCirclePaint
        )
        canvas?.drawCircle(
            widthSize / 2f,
            heightSize / 2f,
            outerCircleSize,
            outerCirclePaint
        )

        // Draw button text based on button status
        canvas?.drawText(
            resources.getString(R.string.start_game_button),
            widthSize / 2f,
            heightSize / 1.8f,
            buttonTextPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun performClick(): Boolean {
        if (super.performClick()) {
            buttonColor = androidx.appcompat.R.attr.colorPrimary
        }

        return true
    }

    private fun animateInnerCircle() {
        innerCircleValueAnimator = ofFloat(96f, 100f)
        innerCircleValueAnimator.apply {
            duration = INNER_CIRCLE_ANIMATION_DURATION
            interpolator = LinearInterpolator()
            repeatMode = REVERSE
            repeatCount = INFINITE
            addUpdateListener {
                invalidate()
            }
        }
        innerCircleValueAnimator.start()
    }

    private fun animateOuterCircle() {
        outerCircleValueAnimator = ofFloat(88f, 100f)
        outerCircleValueAnimator.apply {
            duration = OUTER_CIRCLE_ANIMATION_DURATION
            interpolator = LinearInterpolator()
            repeatMode = REVERSE
            repeatCount = INFINITE
            addUpdateListener {
                invalidate()
            }
        }
        outerCircleValueAnimator.start()
    }

    private fun getInnerCircleRadius(progress: Float): Float {
        return widthSize * .5f * (progress / 100f)
    }

    private fun getOuterCircleRadius(progress: Float): Float {
        return widthSize * .42f * (progress / 100f)
    }
}
