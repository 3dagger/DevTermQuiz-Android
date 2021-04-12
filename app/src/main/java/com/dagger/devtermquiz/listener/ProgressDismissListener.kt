package com.dagger.devtermquiz.listener

import com.dagger.devtermquiz.utility.CustomProgressDialog

interface ProgressDismissListener {
    fun onDismiss(dialog: CustomProgressDialog)
}