package com.dagger.devtermquiz.view.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.QuizList
import com.dagger.devtermquiz.model.django.quiz.AllQuizList
import com.dagger.devtermquiz.model.django.quiz.QuizTest
import com.dagger.devtermquiz.repository.remote.RemoteService
import com.dagger.devtermquiz.view.main.MainNavigator
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val remoteService: RemoteService) : BaseViewModel<MainNavigator.View>(), MainNavigator.ViewModel{
    private val _allQuizListData = MutableLiveData<AllQuizList>()
    val allQuizListData: MutableLiveData<AllQuizList> get() = _allQuizListData

    private val _quizTestData = MutableLiveData<QuizTest>()
    val quizTestData: MutableLiveData<QuizTest> get() = _quizTestData

    fun onLoadAllQuizListData() {
          addDisposable(remoteService.requestQuizList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ allQuizListData ->
                        Logger.d("?")
                        _allQuizListData.value = allQuizListData
                        Logger.d(allQuizListData)
                        Logger.d(_allQuizListData)
                        },{

                        })


        )
    }

    fun onLoadQuitTestData() {
        addDisposable(remoteService.requestQuizTest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({quizTestData ->
                    _quizTestData.value = quizTestData
                    Logger.d(quizTestData)
                    Logger.d(quizTestData.id)
                    Logger.d(quizTestData.content)
                },{
                }))
    }

    override fun disposableClear() {
        onCleared()
    }

}