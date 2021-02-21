package com.dagger.devtermquiz.view.splash

import android.os.Bundle
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivitySplashBinding
import com.dagger.devtermquiz.view.splash.model.SplashViewModel
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator.View {
    override val layoutResourceId: Int get() = R.layout.activity_splash
    override val viewModel: SplashViewModel by inject()


    override fun initView() {
    }

    override fun onProcess() {
    }

    override fun onViewModelCleared() {
    }
}