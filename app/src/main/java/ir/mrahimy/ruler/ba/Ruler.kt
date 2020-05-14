package ir.mrahimy.ruler.ba

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import ir.mrahimy.ruler.Ruler

@BindingAdapter("app:isInCentimeters")
fun Ruler.setIsInCentimeters(isInCentimeter: Boolean?) {
    isInCentimeter?.let { this.mInCentimeter = isInCentimeter }
}

@InverseBindingAdapter(attribute = "app:isInCentimeters")
fun Ruler.getIsInCentimeters() = this.mInCentimeter