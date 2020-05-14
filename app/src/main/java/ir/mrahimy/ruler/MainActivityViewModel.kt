package ir.mrahimy.ruler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _isRulerInCentimeters = MutableLiveData(false)
    val isRulerInCentimeters: LiveData<Boolean>
        get() = _isRulerInCentimeters

    init {
        _isRulerInCentimeters.postValue(false)

        viewModelScope.launch {
            while (true) {
                delay(666)
                _isRulerInCentimeters.postValue(_isRulerInCentimeters.value?.not())
            }
        }
    }
}