package ir.mrahimy.ruler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ir.mrahimy.ruler.databinding.ActivityMainBinding
import ir.mrahimy.ruler.ktx.getMainActivityViewModel
import ir.mrahimy.ruler.ktx.getRealDeviceSizeInPixels
import ir.mrahimy.ruler.ktx.hideToolBr
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getMainActivityViewModel()

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            executePendingBindings()
        }
        hideToolBr()

        val displayInfo = getRealDeviceSizeInPixels()
        ruler.setScreenDimensions(displayInfo)
    }
}