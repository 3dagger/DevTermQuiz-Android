package com.dagger.devtermquiz.view.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.django.quiz.SingleQuiz
import com.dagger.devtermquiz.model.django.quiz.SingleQuizExample
import com.dagger.devtermquiz.model.django.quiz.SingleQuizResults
import com.dagger.devtermquiz.repository.remote.RemoteService
import com.dagger.devtermquiz.view.main.MainNavigator
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel{
    private val _singleQuizData = MutableLiveData<SingleQuiz>()
    val singleQuizData: MutableLiveData<SingleQuiz> get() = _singleQuizData

    private val _singleQuizListData = MutableLiveData<List<String>>()
    val singleQuizListData: MutableLiveData<List<String>> get() = _singleQuizListData

    fun onLoadSingleQuizData() {
        addDisposable(remoteService.requestSingleQuiz()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({singleQuizData ->
//                Logger.d("singleQuizData :: ${singleQuizData.results[0].body.split("/")}")
//                Logger.d(singleQuizData)
                _singleQuizData.value = singleQuizData
//                _singleQuizListData.value = singleQuizData.results[0].ex

                _singleQuizListData.value = listOf<String>(
                    singleQuizData.results[0].ex[0].firstExample,
                    singleQuizData.results[0].ex[0].secondExample,
                    singleQuizData.results[0].ex[0].thirdExample,
                    singleQuizData.results[0].ex[0].fourthExample
                )


//                Logger.d(singleQuizData.results[0].ex)
//                _singleQuizListData.value = singleQuizData.results

            }, {

            }))
    }


    override fun disposableClear() {
        onCleared()
    }

}