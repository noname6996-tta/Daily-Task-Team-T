// File: ProgressCardView.kt
package com.tta.dailytaskteamt.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class ProgressCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var title = "Office Project"
    private var subtitle = "Grocery shopping app design"
    private var progress = 50 // Giá trị tiến trình (0 - 100)
    private var progressColor = Color.parseColor("#007BFF") // Màu xanh dương cho thanh tiến trình

    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY // Màu chữ tiêu đề
        textSize = 40f
        typeface = Typeface.DEFAULT_BOLD
    }

    private val subtitlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK // Màu chữ phụ
        textSize = 50f
        typeface = Typeface.DEFAULT
    }

    private val progressBarPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = progressColor
        style = Paint.Style.FILL
    }

    private val backgroundBarPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY // Màu nền thanh tiến trình
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Vẽ tiêu đề
        canvas.drawText(title, 40f, 60f, titlePaint)

        // Vẽ phụ đề
        canvas.drawText(subtitle, 40f, 140f, subtitlePaint)

        // Tính kích thước cho thanh tiến trình
        val barStartX = 40f
        val barEndX = width - 40f
        val barY = 180f
        val barHeight = 20f

        // Vẽ nền thanh tiến trình
        canvas.drawRect(barStartX, barY, barEndX, barY + barHeight, backgroundBarPaint)

        // Vẽ thanh tiến trình
        val progressWidth = barStartX + ((progress / 100f) * (barEndX - barStartX))
        canvas.drawRect(barStartX, barY, progressWidth, barY + barHeight, progressBarPaint)
    }

    // Phương thức cập nhật giá trị tiến trình
    fun setProgress(value: Int) {
        progress = value.coerceIn(0, 100) // Giới hạn từ 0-100
        invalidate() // Vẽ lại view
    }

    // Phương thức cập nhật tiêu đề và phụ đề
    fun setText(title: String, subtitle: String) {
        this.title = title
        this.subtitle = subtitle
        invalidate()
    }
}
