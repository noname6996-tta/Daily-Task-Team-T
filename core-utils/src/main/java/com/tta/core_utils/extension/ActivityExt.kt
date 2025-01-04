package com.tta.core_utils.extension

import android.app.Activity
import android.app.Activity.OVERRIDE_TRANSITION_CLOSE
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle
import com.tta.core_utils.R

/** Extension handle toast in act ------------------------------------------------------------------------------------*/
fun Context.toast(@StringRes id: Int) {
    Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()
}

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T> AppCompatActivity.collectLifecycleFlow(flow: Flow<T>, collect: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                collect(it)
            }
        }
    }
}

fun Activity?.setOrientation(isLandscape: Boolean) {
    if (isLandscape) {
        this?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    } else {
        this?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}

/** Extension handle startActivity using Intent ----------------------------------------------------------------------*/

// Using this when launch activity with intent which hasn't contain data
fun <T> Activity.nextScreen(dataClass: Class<T>) {
    val option = ActivityOptionsCompat.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    )
    startActivity(Intent(this, dataClass), option.toBundle())
}

fun Activity.nextScreen(intent: Intent) {
    val option = ActivityOptionsCompat.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    )
    startActivity(intent, option.toBundle())
}

fun <T> Activity.nextAndRemoveScreen(dataClass: Class<T>) {
    val option = ActivityOptionsCompat.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    )
    startActivity(Intent(this, dataClass), option.toBundle())
    finish()
}

fun Activity.exitScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        // Android 14 or higher
        finish()
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.slide_in_left, R.anim.slide_out_right)
    } else {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}

