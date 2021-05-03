package com.dagger.devtermquiz.ext

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun Activity.showDialog(activity: Activity, title: String, contents: String, cancelable: Boolean) {
    AlertDialog.Builder(activity).apply {
        setTitle(title)
        setMessage(contents)
        setCancelable(cancelable)
        setPositiveButton("확인") {_, _ ->
        }
        create()
        show()
    }
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun LottieAnimationView.showLottie(ani: String,  loopBool: Boolean) {
    setAnimation(ani)
//    imageAssetsFolder = asFolder
    playAnimation()
    loop(loopBool)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}
