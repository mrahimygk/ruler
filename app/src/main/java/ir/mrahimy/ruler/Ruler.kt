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
 * @property strokeWidth: defines the line width to be drawn
 */
class Ruler(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var screenHeightInInch = 1.0f
    private var screenHeightInPixel = 1.0f
    private var screenWidthInInch = 1.0f
    private var screenWidthInPixel = 1.0f
    private var oneInchInPixel = 1.0f
    private var oneCentimeterInPixel = 1.0f
    private var inchLineCount = 1
    private var centimeterLineCount = 1

    private var mLineColor = Color.BLUE
    private var mInCentimeter = false

    private val numberOfInchSubdivisions = 16
    private val numberOfCentimeterSubdivisions = 10
    private val maxWidthOfUnit = 64.0f
    private val strokeWidth = 4.0f

    private val linesPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = mLineColor
        strokeWidth = this@Ruler.strokeWidth
    }

    private val textPaintForWholeUnit = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = mLineColor
        textSize = 42f
        typeface = ResourcesCompat.getFont(context, R.font.iran_sans)
    }

    private val textPaintForSubdivision = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = textPaintForWholeUnit.style
        color = textPaintForWholeUnit.color
        textSize = textPaintForWholeUnit.textSize / 2
        typeface = textPaintForWholeUnit.typeface
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Ruler, 0, 0)

        typedArray.apply {
            mLineColor = getColor(R.styleable.Ruler_lineColor, Color.BLUE)
            mInCentimeter = getBoolean(R.styleable.Ruler_isCentimeter, false)
        }
        linesPaint.color = mLineColor
        typedArray.recycle()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawRuler(canvas)
    }

    private fun drawRuler(canvas: Canvas?) {

        var unitIndex = 0

        if (mInCentimeter)
            repeat(centimeterLineCount * numberOfCentimeterSubdivisions) {
                val width = when {
                    it % numberOfCentimeterSubdivisions == 0 -> maxWidthOfUnit
                    it % (numberOfCentimeterSubdivisions / 2) == 0 -> maxWidthOfUnit / 1.5f
                    else -> maxWidthOfUnit / 3
                }

                val lineY = oneCentimeterInPixel / numberOfCentimeterSubdivisions * it
                canvas?.drawLine(
                    0.0f,
                    lineY,
                    width,
                    lineY,
                    linesPaint.halfStroke(width, maxWidthOfUnit, this.strokeWidth)
                )

                /**
                 * Drawing text by using a custom type face which draws persian numbers for english numbers
                 */
                if (maxWidthOfUnit == width && unitIndex != 0)
                    canvas?.drawText(
                        (unitIndex).toString(),
                        width + 32,
                        lineY + textPaintForWholeUnit.textSize / 3,
                        textPaintForWholeUnit
                    )

                if (maxWidthOfUnit / 1.5f == width && unitIndex != 0)
                    canvas?.drawText(
                        "${unitIndex - 1}٫5",
                        width + 32,
                        lineY + textPaintForSubdivision.textSize / 3,
                        textPaintForSubdivision
                    )
                if (maxWidthOfUnit == width) unitIndex++
            }
        else
            repeat(inchLineCount * numberOfInchSubdivisions) {
                val width = when {
                    it % (numberOfInchSubdivisions / 1) == 0 -> maxWidthOfUnit / 1
                    it % (numberOfInchSubdivisions / 2) == 0 -> maxWidthOfUnit / 2
                    it % (numberOfInchSubdivisions / 4) == 0 -> maxWidthOfUnit / 4
                    it % (numberOfInchSubdivisions / 8) == 0 -> maxWidthOfUnit / 8
                    it % (numberOfInchSubdivisions / 16) == 0 -> maxWidthOfUnit / 16
                    else -> 1.0f
                }

                val lineY = oneInchInPixel / numberOfInchSubdivisions * it

                /**
                 * drawing a big line for whole unit, half for 1/2 unit and so on.
                 */
                canvas?.drawLine(
                    0.0f,
                    lineY,
                    width,
                    lineY,
                    linesPaint.halfStroke(width, maxWidthOfUnit, this.strokeWidth)
                )

                /**
                 * Drawing text by using a custom type face which draws persian numbers for english numbers
                 */
                if (maxWidthOfUnit == width && unitIndex != 0)
                    canvas?.drawText(
                        (unitIndex).toString(),
                        width + 32,
                        lineY + 16,
                        textPaintForWholeUnit
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
         * finding the ratio of inch and pixel
         */
        this.oneInchInPixel = screenHeightInPixel / screenHeightInInch
        this.oneCentimeterInPixel = oneInchInPixel / 2.54f

        /**
         * drawing x horizontal lines, 1 more to be sure
         */
        inchLineCount = (screenHeightInPixel / oneInchInPixel).roundToInt() + 1
        centimeterLineCount = (screenHeightInPixel / oneCentimeterInPixel).roundToInt() + 1

        invalidate()
    }
}

fun Paint.halfStroke(width: Float, maxWidthOfUnit: Float, inStrokeWidth: Float): Paint {
    return if (width == maxWidthOfUnit) this else this.apply {
        strokeWidth = inStrokeWidth / 2
    }
}