package ir.mrahimy.ruler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

/**
 * Simple view that shows a frame with transparent bg
 *
 * @property mLineColor: providing the color in xml, defaults to black
 * @property borderWidth: defines the line width to be drawn
 */
class Ruler(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var borderWidth = 4.0f
    private var mLineColor = Color.GRAY
    private var screenLength = 17.0

    private val linesPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = mLineColor
        strokeWidth = borderWidth
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
//        val typedArray =
//            context.obtainStyledAttributes(attrs, R.styleable.Ruler, 0, 0)
//
//        typedArray?.apply {
//            mLineColor = getColor(R.styleable.Ruler_lineColor, Color.BLACK)
//        }
//        linesPaint.color = mLineColor
//        typedArray?.recycle()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawRuler(canvas)
    }

    private fun drawRuler(canvas: Canvas?) {

        val startX = paddingStart.toFloat()
        val startY = paddingTop.toFloat()
        val endX = width - paddingEnd.toFloat()
        val endY = height - paddingBottom.toFloat()

//        canvas?.drawLine(startX, startY, startX + 50f, startY, linesPaint)
//        canvas?.drawLine(startX, startY, startX, startY + 50f, linesPaint)
//
//        canvas?.drawLine(startX, endY, startX + 50f, endY, linesPaint)
//        canvas?.drawLine(startX, endY, startX, endY - 50f, linesPaint)
//
//        canvas?.drawLine(endX, startY, endX - 50f, startY, linesPaint)
//        canvas?.drawLine(endX, startY, endX, startY + 50f, linesPaint)
//
//        canvas?.drawLine(endX, endY, endX - 50f, endY, linesPaint)
//        canvas?.drawLine(endX, endY, endX, endY - 50f, linesPaint)

        val _1_InchInPixel = (endY / screenLength)
        val count = (endY / _1_InchInPixel).roundToInt()
        repeat(count + 1) {
            canvas?.drawLine(
                0.0f,
                _1_InchInPixel.toFloat() * it,
                64.0f,
                _1_InchInPixel.toFloat() * it,
                linesPaint
            )
        }
    }

    fun setScreenLength(x: Double) {
        screenLength = x
        invalidate()
    }
}