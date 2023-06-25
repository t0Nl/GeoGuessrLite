package com.example.android.geoguessrlite.ui.game

import android.animation.ValueAnimator
import android.animation.ValueAnimator.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnRepeat
import androidx.core.content.withStyledAttributes
import com.example.android.geoguessrlite.R

private const val SPINNER_ANIMATION_DURATION = 800L
private const val FULL_CIRCLE_RADIUS = 360f
private const val PROGRESS_SPINNER_STARTING_ANGLE = -90f

@SuppressLint("ResourceType")
class LoadingSpinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var buttonColor: Int = androidx.appcompat.R.attr.colorPrimary
    private var buttonTextColor: Int = androidx.appcompat.R.attr.colorPrimary

    private var valueAnimator = ValueAnimator()

    private var isAnimatorReversing = false

    private val spinnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    init {
        isClickable = true

        context.withStyledAttributes(attrs, R.styleable.StartButton) {
            buttonColor = getColor(R.styleable.StartButton_buttonColor, buttonColor)
            buttonTextColor = getColor(R.styleable.StartButton_buttonTextColor, buttonColor)
        }

        animateLoadingProgress()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Draw progress bar circle based on value from ValueAnimator
        val spinnerRadius = valueAnimator.animatedValue?.let {
            getSpinnerRadius(it as Float)
        } ?: 0f

        val circularClipPath = Path()

        circularClipPath.addCircle(
            widthSize * .5f,
            heightSize * .5f,
            widthSize * .4f,
            Path.Direction.CW
        )

        canvas?.clipOutPath(circularClipPath)

        // Draw spinner
        if (isAnimatorReversing) {
            canvas?.drawArc(
                0f,
                0f,
                widthSize.toFloat(),
                heightSize.toFloat(),
                PROGRESS_SPINNER_STARTING_ANGLE,
                -spinnerRadius,
                true,
                spinnerPaint
            )
        } else {
            canvas?.drawArc(
                0f,
                0f,
                widthSize.toFloat(),
                heightSize.toFloat(),
                PROGRESS_SPINNER_STARTING_ANGLE,
                spinnerRadius,
                true,
                spinnerPaint
            )
        }
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

    private fun animateLoadingProgress() {
        valueAnimator = ofFloat(0f, 100f)
        valueAnimator.apply {
            duration = SPINNER_ANIMATION_DURATION
            interpolator = LinearInterpolator()
            repeatMode = REVERSE
            repeatCount = INFINITE
            addUpdateListener {
                invalidate()
            }
            doOnRepeat {
                isAnimatorReversing = !isAnimatorReversing
            }
        }
        valueAnimator.start()
    }

    private fun getSpinnerRadius(progress: Float): Float {
        return FULL_CIRCLE_RADIUS * (progress / 100f)
    }
}
