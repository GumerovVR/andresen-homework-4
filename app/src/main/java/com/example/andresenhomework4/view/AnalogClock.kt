package com.example.andresenhomework4.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.example.andresenhomework4.R
import java.text.SimpleDateFormat
import kotlin.math.cos
import kotlin.math.sin

class AnalogClock(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var radius = 0
    private var r1 = 0
    private var centerX = 0
    private var centerY = 0
    private var degreeHour = 0.0f
    private var degreeMin = 0.0f
    private var degreeSecond = 0.0f

    private var hourArrowColor: Int = 0
    private var minArrowColor: Int = 0
    private var secArrowColor: Int = 0

    private var hourArrowWidth: Int = 10
    private var minArrowWidth: Int = 10
    private var secArrowWidth: Int = 3
    private var hourArrowHeight: Int = 260
    private var minArrowHeight: Int = 80
    private var secArrowHeight: Int = 40


    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 10f
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.AnalogClock) {
            hourArrowColor = getInt(R.styleable.AnalogClock_hourArrowColor, Color.BLACK)
            minArrowColor = getInt(R.styleable.AnalogClock_minArrowColor, Color.BLACK)
            secArrowColor = getInt(R.styleable.AnalogClock_secArrowColor, Color.BLACK)

            hourArrowWidth = getInt(R.styleable.AnalogClock_hourArrowWidth, 10)
            minArrowWidth = getInt(R.styleable.AnalogClock_minArrowWidth, 10)
            secArrowWidth = getInt(R.styleable.AnalogClock_secArrowWidth, 3)

            hourArrowHeight = getInt(R.styleable.AnalogClock_hourArrowHeight, 260)
            minArrowHeight = getInt(R.styleable.AnalogClock_minArrowHeight, 80)
            secArrowHeight = getInt(R.styleable.AnalogClock_secArrowHeight, 40)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.TRANSPARENT)

        drawCircle(canvas)
        drawMarks(canvas)
        drawNumbs(canvas)
        getTime()
        drawHourArray(canvas)
        drawMinArray(canvas)
        drawSecArray(canvas)
        invalidate()
    }

    private fun drawCircle(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        radius = width / 2 - 60
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius.toFloat(),
            paint
        )

        paint.color = Color.BLACK
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius.toFloat(),
            paint
        )
    }

    private fun drawMarks(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = 20f
        r1 = radius - 20
        centerX = width / 2
        centerY = height / 2
        val marks = FloatArray(7 * 4)
        val marks2 = FloatArray(5 * 4)
        for (i in 0..11) {
            when {
                i <= 3 -> {
                    val degree = i * 30
                    val xS = (sin(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val yS = (cos(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val xE = (sin(Math.toRadians(degree.toDouble())) * radius).toInt()
                    val yE = (cos(Math.toRadians(degree.toDouble())) * radius).toInt()
                    marks[i * 4] = (centerX + xS).toFloat()
                    marks[i * 4 + 1] = (centerY - yS).toFloat()
                    marks[i * 4 + 2] = (centerX + xE).toFloat()
                    marks[i * 4 + 3] = (centerY - yE).toFloat()
                }
                i <= 6 -> {
                    val degree = (i - 3) * 30
                    val yS = (sin(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val xS = (cos(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val yE = (sin(Math.toRadians(degree.toDouble())) * radius).toInt()
                    val xE = (cos(Math.toRadians(degree.toDouble())) * radius).toInt()
                    marks[i * 4] = (centerX + xS).toFloat()
                    marks[i * 4 + 1] = (centerY + yS).toFloat()
                    marks[i * 4 + 2] = (centerX + xE).toFloat()
                    marks[i * 4 + 3] = (centerY + yE).toFloat()
                }
                i <= 9 -> {
                    val degree = (i - 6) * 30
                    val xS = (sin(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val yS = (cos(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val xE = (sin(Math.toRadians(degree.toDouble())) * radius).toInt()
                    val yE = (cos(Math.toRadians(degree.toDouble())) * radius).toInt()
                    marks2[(i - 7) * 4] = (centerX - xS).toFloat()
                    marks2[(i - 7) * 4 + 1] = (centerY + yS).toFloat()
                    marks2[(i - 7) * 4 + 2] = (centerX - xE).toFloat()
                    marks2[(i - 7) * 4 + 3] = (centerY + yE).toFloat()
                }
                i <= 11 -> {
                    val degree = (i - 9) * 30
                    val yS = (sin(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val xS = (cos(Math.toRadians(degree.toDouble())) * r1).toInt()
                    val yE = (sin(Math.toRadians(degree.toDouble())) * radius).toInt()
                    val xE = (cos(Math.toRadians(degree.toDouble())) * radius).toInt()
                    marks2[(i - 7) * 4] = (centerX - xS).toFloat()
                    marks2[(i - 7) * 4 + 1] = (centerY - yS).toFloat()
                    marks2[(i - 7) * 4 + 2] = (centerX - xE).toFloat()
                    marks2[(i - 7) * 4 + 3] = (centerY - yE).toFloat()
                }
            }
        }
        canvas.drawLines(marks, paint)
        canvas.drawLines(marks2, paint)
    }

    private fun drawNumbs(canvas: Canvas) {
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 50f
        paint.color = Color.BLACK
        for (i in 0..11) {
            val degree = i * 30
            val xS = (sin(Math.toRadians(degree.toDouble())) * (r1 - 50)).toInt()
            val yS = (cos(Math.toRadians(degree.toDouble())) * (r1 - 50)).toInt()
            var str = ""
            str = if (i == 0) {
                "12"
            } else {
                i.toString()
            }
            val fm = paint.fontMetrics
            canvas.drawText(
                str,
                (centerX + xS).toFloat(),
                centerY - yS + (fm.descent - fm.ascent) / 2 - fm.descent,
                paint
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime() {
        val time = System.currentTimeMillis()
        val format = SimpleDateFormat("HH-mm-ss")
        val str = format.format(time)
        val hourMinSeconds = str.split("-").toTypedArray()

        val hour = Integer.valueOf(hourMinSeconds[0]) % 12
        val min = Integer.valueOf(hourMinSeconds[1]) % 60
        val second = Integer.valueOf(hourMinSeconds[2]) % 60
        degreeHour = hour * 30 + min * 0.5f
        degreeMin = (min * 360 / 60).toFloat()
        degreeSecond = (second * 360 / 60).toFloat()
    }

    private fun drawHourArray(canvas: Canvas) {
        paint.color = hourArrowColor
        paint.style = Paint.Style.FILL
        canvas.rotate(degreeHour, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawRect(
            (width / 2 - hourArrowWidth).toFloat(),
            (height / 2 - r1 + hourArrowHeight).toFloat(),
            (width / 2 + hourArrowWidth).toFloat(),
            (height / 2 + 40).toFloat(),
            paint
        )
    }

    private fun drawMinArray(canvas: Canvas) {
        paint.color = minArrowColor
        canvas.rotate(360 - degreeHour, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.rotate(degreeMin, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawRect(
            (width / 2 - minArrowWidth).toFloat(),
            (height / 2 - r1 + minArrowHeight).toFloat(),
            (width / 2 + minArrowWidth).toFloat(),
            (height / 2 + 40).toFloat(),
            paint
        )
    }

    private fun drawSecArray(canvas: Canvas) {
        paint.color = secArrowColor
        canvas.rotate(360 - degreeMin, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.rotate(degreeSecond, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawRect(
            (width / 2 - secArrowWidth).toFloat(),
            (height / 2 - r1 + secArrowHeight).toFloat(),
            (width / 2 + secArrowWidth).toFloat(),
            (height / 2 + 50).toFloat(),
            paint
        )
    }
}