package ir.mrahimy.ruler

import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideToolBr()

        val dm = getRealDeviceSizeInPixels()
        ruler.setScreenDimensions(dm.hInInch, dm.hInPixel, dm.wInInch, dm.wInPixel)

    }

    private fun getRealDeviceSizeInPixels(): DisplayInfo {
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)


        // since SDK_INT = 1;
        var mWidthPixels = displayMetrics.widthPixels
        var mHeightPixels = displayMetrics.heightPixels

        // includes window decorations (statusbar bar/menu bar)
        try {
            mWidthPixels = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
            mHeightPixels = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
        } catch (ignored: Exception) {
        }

        // includes window decorations (statusbar bar/menu bar)
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

    private fun hideToolBr() {
        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = newUiOptions
    }
}

data class DisplayInfo(
    val wInPixel: Float,
    val hInPixel: Float,
    val xdpi: Float,
    val ydpi: Float,
    val wInInch: Float,
    val hInInch: Float
)