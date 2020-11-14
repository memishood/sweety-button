package net.memish.sweetybutton

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import kotlin.math.min

class SweetyButton  constructor(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val paint3 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val rectF = RectF()
    private var startMills = 0
    private var endMills = 10000
    private val animator: ValueAnimator
    private var progressWidth = 5f
    private var progressColor = 0
    private var buttonColor = 0

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SweetyButton).apply {
            endMills = getInt(R.styleable.SweetyButton_endMills, 10000)
            progressWidth = getFloat(R.styleable.SweetyButton_progressWidth, 5f)
            progressColor = getColor(
                R.styleable.SweetyButton_progressColor,
                Color.parseColor("#FFFFFF")
            )
            buttonColor = getColor(
                R.styleable.SweetyButton_buttonColor,
                Color.parseColor("#F9F9F9")
            )
        }.run {
            recycle()
        }

        if (progressWidth > 20f) {
            progressWidth = 20f
        }

        paint.color = buttonColor
        paint2.color = buttonColor
        paint2.strokeWidth = progressWidth
        paint3.strokeWidth = progressWidth * 1.2f
        paint3.color = progressColor

        ValueAnimator.ofInt(startMills, endMills).apply {
            duration = endMills.toLong()
            interpolator = LinearInterpolator()
            addUpdateListener { postInvalidate() }
        }.also {
            animator = it
        }

        setOnLongClickListener { true }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = (width / 2).toFloat()
        val cy = (height / 2).toFloat()
        val radius = cx - 40
        val radius2 = radius + 20
        canvas.drawCircle(cx, cy, radius, paint)
        canvas.drawCircle(cx, cy, radius2, paint2)
        val startAngle = 270f
        val sweepAngle = (360 * animator.currentPlayTime / endMills).toFloat()
        val useCenter = false
        rectF.apply {
            left = cx - radius2
            top = cy - radius2
            right = cx + radius2
            bottom = cy + radius2
        }.also {
            canvas.drawArc(it, startAngle, sweepAngle, useCenter, paint3)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = dp2Px(64f).toInt()
        val desiredHeight = dp2Px(64f).toInt()

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun performLongClick(): Boolean {
        animator.start()
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN
            -> anim(1.1f, 1.1f)

            MotionEvent.ACTION_UP
            -> anim(1f, 1f).run { animator.cancel() }
        }
        return super.onTouchEvent(event)
    }

    fun getAnimator() = animator
}

const val SCALE_ANIM_DURATION = 300L

fun View.anim(scaleX: Float, scaleY: Float) {
    animate().scaleX(scaleX)
        .scaleY(scaleY)
        .setInterpolator(OvershootInterpolator(1f))
        .setDuration(SCALE_ANIM_DURATION)
        .start()
}

fun SweetyButton.setOnStartRecordListener(action: (animator: ValueAnimator) -> Unit) {
    getAnimator()
        .doOnStart {
            action(it as ValueAnimator)
        }
}

fun SweetyButton.setOnEndRecordListener(action: (animator: ValueAnimator) -> Unit) {
    getAnimator()
        .doOnEnd {
            action(it as ValueAnimator)
        }
}

fun View.dp2Px(value: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)
}