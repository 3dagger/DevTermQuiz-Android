package com.dagger.devtermquiz.view.splash.model

import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.ResponseCode
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.django.version.VersionCheck
import com.dagger.devtermquiz.model.request.fail.RequestFail
import com.dagger.devtermquiz.repository.remote.RemoteService
import com.dagger.devtermquiz.view.splash.SplashNavigator
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashViewModel(private val remoteService: RemoteService) : BaseViewModel<SplashNavigator.View>(), SplashNavigator.ViewModel{
    private val _versionData = MutableLiveData<VersionCheck>()
    val versionData: MutableLiveData<VersionCheck> get() = _versionData


    override fun onLoadApplicationVersionStatus() {
        addDisposable(remoteService.requestVersionStatus()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.code()) {
                    ResponseCode.CODE200.value -> {
                        versionData.value = it.body()
                    }
                    ResponseCode.CODE404.value -> {

                    }
                }
                Logger.d(it.body())
                
            },{
                getNavigator().onRequestFailed(
                    RequestFail(
                        title = Constants.REQUEST_FAIL_MESSAGE_TITLE,
                        msg = Constants.REQUEST_FAIL_MESSAGE_MSG,
                        runnable = Runnable{ onLoadApplicationVersionStatus() }
                    )
                )
            }))
    }

    override fun disposableClear() {
        onCleared()
    }
}