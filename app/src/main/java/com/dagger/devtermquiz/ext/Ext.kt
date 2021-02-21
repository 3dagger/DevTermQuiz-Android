package com.dagger.devtermquiz.ext

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast

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

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}