package ir.mrahimy.ruler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.mrahimy.ruler.ktx.getRealDeviceSizeInPixels
import ir.mrahimy.ruler.ktx.hideToolBr
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideToolBr()

        val displayInfo = getRealDeviceSizeInPixels()
        ruler.setScreenDimensions(displayInfo)
    }
}