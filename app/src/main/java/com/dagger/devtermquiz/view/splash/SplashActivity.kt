package com.dagger.devtermquiz.view.splash

import android.os.Handler
import android.os.Looper
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivitySplashBinding
import com.dagger.devtermquiz.ext.openActivity
import com.dagger.devtermquiz.view.splash.model.SplashViewModel
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator.View {
    override val layoutResourceId: Int get() = R.layout.activity_splash
    override val viewModel: SplashViewModel by inject()


    override fun initView() {


    }

    override fun onProcess() {
        Handler(Looper.getMainLooper()).postDelayed({
            onMoveMain()
        }, Constants.MOVE_MAIN_DELAYED_MILLIS)
    }

    override fun onMoveMain() {
//        openActivity(MainActivity::class.java)
    }

    override fun onViewModelCleared() {
    }
}