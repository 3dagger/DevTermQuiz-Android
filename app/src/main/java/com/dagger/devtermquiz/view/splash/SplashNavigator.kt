package com.dagger.devtermquiz.view.splash

import com.dagger.devtermquiz.model.request.fail.RequestFail

interface SplashNavigator {
    interface View {
        fun onMoveMain()

        fun onVersionCheck(serverStatus: String, androidVersion: String)

        fun onRequestFailed(requestFail: RequestFail)
    }
    interface ViewModel {
        fun onLoadApplicationVersionStatus()

        fun disposableClear()
    }
}