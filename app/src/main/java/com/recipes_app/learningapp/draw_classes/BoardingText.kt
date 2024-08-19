package com.recipes_app.learningapp.draw_classes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class BoardingText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val outlinePaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f  // Ширина контура
        color = Color.BLACK  // Цвет контура
        isAntiAlias = true  // Уменьшение визуальных артефактов
    }

    private val textPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        textSize = this@BoardingText.textSize
        color = currentTextColor
        isAntiAlias = true  // Уменьшение визуальных артефактов
    }

    override fun onDraw(canvas: Canvas) {
        // Сохранение состояния Canvas
        canvas.save()

        // Получаем текст
        val text = text.toString()

        // Вычисляем позицию текста
        val x = paddingLeft.toFloat()
        val y = (height / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2)

        // Рисуем контур текста
        canvas.drawText(text, x, y, outlinePaint)
        // Рисуем основной текст
        canvas.drawText(text, x, y, textPaint)

        // Восстановление состояния Canvas
        canvas.restore()
    }
}
