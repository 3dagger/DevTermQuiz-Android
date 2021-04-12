package com.dagger.devtermquiz.utility

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.ext.showLottie
import com.dagger.devtermquiz.listener.ProgressDismissListener
import kotlinx.android.synthetic.main.loading_view.*

class CustomProgressDialog(context: Context,
                           private val message: String,
                           private val cancelable: Boolean): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.8f
        window!!.attributes = layoutParams

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.requestFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.loading_view)
        setCancelable(cancelable)

        txt_progress.text = message

//        customProgressListener.onDismiss(dialog = this)
    }

//    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
}