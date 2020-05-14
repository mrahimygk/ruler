package ir.mrahimy.ruler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

/**
 * @property mLineColor: providing the color in xml, defaults to black
 * @property borderWidth: defines the line width to be drawn
 */
class Ruler(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var borderWidth = 4.0f
    private var mLineColor = Color.BLUE
    private var screenHeightInInch = 1.0f
    private var screenHeightInPixel = 1.0f

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
        /**
         * finding the ratio of int and pixel
         */
        val oneInchInPixel = (screenHeightInPixel / screenHeightInInch)

        /**
         * drawing x horizontal lines, 1 more to be sure
         */
        val count = (screenHeightInPixel / oneInchInPixel).roundToInt() + 1

        repeat(count) {
            canvas?.drawLine(
                0.0f,
                oneInchInPixel * it,
                64.0f,
                oneInchInPixel * it,
                linesPaint
            )
        }

        repeat(count * 2) {
            canvas?.drawLine(
                0.0f,
                oneInchInPixel / 2 * it,
                32.0f,
                oneInchInPixel / 2 * it,
                linesPaint
            )
        }

        repeat(count * 4) {
            canvas?.drawLine(
                0.0f,
                oneInchInPixel / 4 * it,
                16.0f,
                oneInchInPixel / 4 * it,
                linesPaint
            )
        }

        repeat(count * 8) {
            canvas?.drawLine(
                0.0f,
                oneInchInPixel / 8 * it,
                8.0f,
                oneInchInPixel / 8 * it,
                linesPaint
            )
        }
    }

    fun setScreenDimensions(screenHeightInInch: Float, screenHeightInPixel: Float) {
        this.screenHeightInInch = screenHeightInInch
        this.screenHeightInPixel = screenHeightInPixel
        invalidate()
    }
}