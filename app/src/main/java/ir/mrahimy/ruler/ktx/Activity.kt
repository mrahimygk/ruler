package ir.mrahimy.ruler.ktx

import android.app.Activity
import android.view.View

fun Activity.hideToolBr() {
    val uiOptions = window.decorView.systemUiVisibility
    var newUiOptions = uiOptions
    newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
    newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    window.decorView.systemUiVisibility = newUiOptions
}