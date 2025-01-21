// File: CircularProgressView.kt
package com.tta.dailytaskteamt.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY // Màu nền (xám nhạt)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFFFFF") // Màu tím cho tiến trình
        style = Paint.Style.STROKE
        strokeWidth = 20f
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE // Màu chữ trắng
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }

    private var progress = 0f // Giá trị tiến trình (0-100)
    private val circleBounds = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val padding = 20f
        circleBounds.set(
            padding,
            padding,
            w - padding,
            h - padding
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Vẽ vòng tròn nền
        canvas.drawOval(circleBounds, backgroundPaint)

        // Vẽ vòng tròn tiến trình
        val sweepAngle = (progress / 100) * 360 // Góc quét
        canvas.drawArc(circleBounds, -90f, sweepAngle, false, progressPaint)

        // Vẽ text hiển thị phần trăm
        val progressText = "${progress.toInt()}%"
        canvas.drawText(progressText, width / 2f, height / 2f + textPaint.textSize / 3, textPaint)
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 100f) // Giới hạn giá trị từ 0-100
        invalidate() // Vẽ lại
    }
}
