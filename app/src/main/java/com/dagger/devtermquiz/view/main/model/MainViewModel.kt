package com.dagger.devtermquiz.view.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.ResponseCode
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.django.quiz.SingleQuiz
import com.dagger.devtermquiz.model.django.quiz.SingleQuizExample
import com.dagger.devtermquiz.model.django.quiz.SingleQuizResults
import com.dagger.devtermquiz.repository.remote.RemoteService
import com.dagger.devtermquiz.view.main.MainNavigator
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class MainViewModel(private val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel{
    private val _singleQuizData = MutableLiveData<SingleQuiz>()
    val singleQuizData: MutableLiveData<SingleQuiz> get() = _singleQuizData

    private val _singleQuizListData = MutableLiveData<List<SingleQuizExample>>()
    val singleQuizListData: MutableLiveData<List<SingleQuizExample>> get() = _singleQuizListData


    fun onLoadSingleQuizData() {
        addDisposable(remoteService.requestSingleQuiz()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({singleQuizData ->
                when(singleQuizData.code()) {
                    ResponseCode.CODE200.value -> {
                        _singleQuizData.value = singleQuizData.body()
                        _singleQuizListData.value = singleQuizData.body()?.results?.get(0)?.ex
                        getNavigator().dismissProgress()
                    }
                }
            }, {

            }))
    }


    override fun disposableClear() {
        onCleared()
    }

}