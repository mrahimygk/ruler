package ir.mrahimy.ruler.ktx

import android.app.Activity
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import ir.mrahimy.ruler.data.DisplayInfo

fun Activity.getRealDeviceSizeInPixels(): DisplayInfo {
    val windowManager = windowManager
    val display = windowManager.defaultDisplay
    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)

    var mWidthPixels = displayMetrics.widthPixels
    var mHeightPixels = displayMetrics.heightPixels

    try {
        mWidthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
        mHeightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
    } catch (ignored: Exception) {
    }

    try {
        val realSize = Point()
        Display::class.java.getMethod("getRealSize", Point::class.java)
            .invoke(display, realSize)
        mWidthPixels = realSize.x
        mHeightPixels = realSize.y
    } catch (ignored: Exception) {
    }

    return DisplayInfo(
        mWidthPixels.toFloat(),
        mHeightPixels.toFloat(),
        displayMetrics.xdpi,
        displayMetrics.ydpi,
        mWidthPixels.toFloat() / displayMetrics.xdpi,
        mHeightPixels.toFloat() / displayMetrics.ydpi
    )
}