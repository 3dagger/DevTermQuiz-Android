package com.dagger.devtermquiz.di

import android.os.Bundle
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.utility.Utility
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress
import com.kaushikthedeveloper.doublebackpress.setup.display.ToastDisplay
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var appModules = module {
    single<DoubleBackPress> {
        val firstBackPressAction : ToastDisplay = ToastDisplay().standard(get(), androidApplication().getString(
            R.string.str_double_back_press))

        DoubleBackPress()
            .withDoublePressDuration(3000)
            .withFirstBackPressAction(firstBackPressAction)
    }

    single { Utility(get()) }

    single { Bundle() }



}