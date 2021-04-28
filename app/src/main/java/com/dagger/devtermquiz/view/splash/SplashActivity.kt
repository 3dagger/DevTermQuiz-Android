package com.dagger.devtermquiz.view.splash

import android.content.pm.PackageInfo
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.ServerState
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivitySplashBinding
import com.dagger.devtermquiz.ext.openActivity
import com.dagger.devtermquiz.ext.toast
import com.dagger.devtermquiz.listener.AwesomeDialogListener
import com.dagger.devtermquiz.model.request.fail.RequestFail
import com.dagger.devtermquiz.utility.Utility
import com.dagger.devtermquiz.view.main.MainViewPagerActivity
import com.dagger.devtermquiz.view.splash.model.SplashViewModel
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator.View {
    override val layoutResourceId   : Int get() = R.layout.activity_splash
    override val viewModel          : SplashViewModel   by inject()
    private  val utility            : Utility           by inject()

    override fun initView() {
        viewModel.setNavigator(this@SplashActivity)
        viewModel.onLoadApplicationVersionStatus()
    }

    override fun onProcess() {
        viewModel.versionData.observe(this@SplashActivity, Observer {
            onVersionCheck(serverStatus = it.results[0].android_status, androidVersion = it.results[0].android_version)
        })
    }

    /**
     * @author : 이수현
     * @Date : 4/28/21 3:17 PM
     * @Description : 
     * @History : 
     *
     **/
    override fun onVersionCheck(serverStatus: String, androidVersion: String) {
        val appVersion= packageManager.getPackageInfo(packageName, 0).versionName

        when (serverStatus) {
            ServerState.REACHABLE.description -> {
                if(androidVersion == appVersion) {
                    Looper.myLooper()?.let { Handler(it).postDelayed({
                            onMoveMain()
                        }, Constants.MOVE_MAIN_DELAYED_MILLIS)
                    }
                    toast(message = "접속을 환영합니다.")
                }else {
                    toast(message = "업데이트 버전이 있습니다.\n업데이트를 위해 마켓으로 이동합니다!")
                }
            }
              ServerState.UNREACHABLE.description -> {
                  utility.serverFixingDialog(activity = this@SplashActivity, cancelabel = false)
            }
        }
    }

    /**
     * @author : 이수현
     * @Date : 4/28/21 2:55 PM
     * @Description : 통신 실패 시 다이얼로그 생성
     *                다시시도  ->  서버 재 요청
     *                종료     -> 어플리케이션 종료
     * @History : 
     *
     **/
    override fun onRequestFailed(requestFail: RequestFail) {
        utility.requestFailDialog(
            activity = this@SplashActivity,
            cancelable = false,
            title = requestFail.title,
            body = requestFail.msg,
            listener = object : AwesomeDialogListener{
                override fun onConfirmClick() {
                    requestFail.runnable.run()
                }

                override fun onAddBookMarkClick() {
                    finishAffinity()
                }

            }
        )
    }

    /**
     * @author : 이수현
     * @Date : 4/28/21 2:55 PM
     * @Description : 메인 이동
     * @History : 
     *
     **/
    override fun onMoveMain() {
        openActivity(MainViewPagerActivity::class.java)
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}