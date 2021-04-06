package com.dagger.devtermquiz.view.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.django.quiz.SingleQuiz
import com.dagger.devtermquiz.repository.remote.RemoteService
import com.dagger.devtermquiz.view.main.MainNavigator
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel{
    private val _singleQuizData = MutableLiveData<SingleQuiz>()
    val singleQuizData: MutableLiveData<SingleQuiz> get() = _singleQuizData

    fun onLoadSingleQuizData() {
        addDisposable(remoteService.requestSingleQuiz()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({singleQuizData ->
                Logger.d("singleQuizData :: $singleQuizData")
                _singleQuizData.value = singleQuizData
            }, {

            }))
    }


    override fun disposableClear() {
        onCleared()
    }

}