package ir.mrahimy.ruler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.math.roundToInt

/**
 * @property mLineColor: providing the color in xml, defaults to black
 * @property borderWidth: defines the line width to be drawn
 */
class Ruler(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var screenHeightInInch = 1.0f
    private var screenHeightInPixel = 1.0f
    private var screenWidthInInch = 1.0f
    private var screenWidthInPixel = 1.0f
    private var oneInchInPixel = 1.0f
    private var inchLineCount = 1

    private var mLineColor = Color.BLUE
    private var mInCentimeter = false

    private val numberOfSubdivisions = 16
    private val maxWidthOfUnit = 64.0f
    private val borderWidth = 4.0f

    private val linesPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = mLineColor
        strokeWidth = borderWidth
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = mLineColor
        textSize = 64f
        typeface = ResourcesCompat.getFont(context, R.font.iran_sans)
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Ruler, 0, 0)

        typedArray?.apply {
            mLineColor = getColor(R.styleable.Ruler_lineColor, Color.BLUE)
            mInCentimeter = getBoolean(R.styleable.Ruler_isCentimeter, false)
        }
        linesPaint.color = mLineColor
        typedArray?.recycle()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawRuler(canvas)
    }

    private fun drawRuler(canvas: Canvas?) {

        if (mInCentimeter) return
        var unitIndex = 0
        repeat(inchLineCount * numberOfSubdivisions) {
            val width = when {
                it % (numberOfSubdivisions / 1) == 0 -> maxWidthOfUnit / 1
                it % (numberOfSubdivisions / 2) == 0 -> maxWidthOfUnit / 2
                it % (numberOfSubdivisions / 4) == 0 -> maxWidthOfUnit / 4
                it % (numberOfSubdivisions / 8) == 0 -> maxWidthOfUnit / 8
                it % (numberOfSubdivisions / 16) == 0 -> maxWidthOfUnit / 16
                else -> 1.0f
            }

            val lineY = oneInchInPixel / numberOfSubdivisions * it

            /**
             * drawing a big line for whole unit, half for 1/2 unit and so on.
             */
            canvas?.drawLine(
                0.0f,
                lineY,
                width,
                lineY,
                linesPaint
            )

            /**
             * drawing a big line for whole unit, half for 1/2 unit and so on.
             */
            canvas?.drawLine(
                0.0f,
                lineY,
                width,
                lineY,
                linesPaint
            )

            /**
             * Drawing text by using a custom type face which draws persian numbers for english numbers
             */
            if (maxWidthOfUnit == width && unitIndex != 0)
                canvas?.drawText(
                    (unitIndex).toString(),
                    width + 32,
                    lineY + 16,
                    textPaint
                )
            if (maxWidthOfUnit == width) unitIndex++
        }
    }

    fun setScreenDimensions(
        screenHeightInInch: Float,
        screenHeightInPixel: Float,
        screenWidthInInch: Float,
        screenWidthInPixel: Float
    ) {
        this.screenHeightInInch = screenHeightInInch
        this.screenHeightInPixel = screenHeightInPixel
        this.screenWidthInPixel = screenWidthInPixel
        this.screenWidthInInch = screenWidthInInch

        /**
         * finding the ratio of int and pixel
         */
        this.oneInchInPixel = screenHeightInPixel / screenHeightInInch

        /**
         * drawing x horizontal lines, 1 more to be sure
         */
        inchLineCount = (screenHeightInPixel / oneInchInPixel).roundToInt() + 1

        invalidate()
    }
}