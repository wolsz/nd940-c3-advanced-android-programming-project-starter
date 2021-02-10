package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var buttonBackgroundColor = 0
    private var buttonTextColor = 0

    private val valueAnimator = ValueAnimator()

    private val downloadText = resources.getString(R.string.download_text)
    private val loadingText = resources.getString(R.string.loading_text)

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }
    @Volatile
    var animationProgress = 0.0f

    init {
        isClickable = true

        buttonState = ButtonState.Completed
        setupAnimation()

        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_backgroundColor, 0)
            buttonTextColor = getColor(R.styleable.LoadingButton_buttonTextColor, 0)


        }
    }

    override fun performClick(): Boolean {
         super.performClick()

        if (buttonState == ButtonState.Completed) {
            buttonState = ButtonState.Loading
            valueAnimator.start()
        }

         invalidate()
         return true
    }

    fun doneLoading() {
//        valueAnimator.removeAllUpdateListeners()
        valueAnimator.cancel()
        buttonState = ButtonState.Completed
        Log.d("MMM", "I am in done")
        invalidate()
    }

    private fun setupAnimation() {
       valueAnimator.apply {
           setFloatValues(0f, 1.0f)
           interpolator = LinearInterpolator()
           duration = 3000
           repeatCount = ValueAnimator.INFINITE
           repeatMode = ValueAnimator.RESTART
           addUpdateListener {
               animationProgress = animatedValue as Float
               invalidate()
           }
       }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = buttonBackgroundColor
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)

        val buttonText = if (buttonState == ButtonState.Loading) loadingText else downloadText

        if (buttonState == ButtonState.Loading) {
            paint.color = Color.RED
            canvas?.drawRect(
                0f, 0f,
                (widthSize * (animationProgress)).toFloat(), heightSize.toFloat(), paint)
        }
        paint.color = buttonTextColor
        canvas?.drawText(buttonText, (widthSize/2).toFloat(), (heightSize/2).toFloat(), paint)
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

}