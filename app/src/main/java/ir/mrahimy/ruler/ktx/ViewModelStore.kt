package ir.mrahimy.ruler.ktx

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.mrahimy.ruler.MainActivityViewModel

fun ComponentActivity.getMainActivityViewModel(): MainActivityViewModel {
    return ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel() as T
        }
    }).get(MainActivityViewModel::class.java)
}