package ir.mrahimy.ruler

import android.view.View
import android.widget.Switch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _isRulerInCentimeters = MutableLiveData(false)
    val isRulerInCentimeters: LiveData<Boolean>
        get() = _isRulerInCentimeters

    init {
        _isRulerInCentimeters.postValue(false)
    }

    fun onSwitchClick(v: View) {
        _isRulerInCentimeters.postValue((v as Switch).isChecked)
    }
}