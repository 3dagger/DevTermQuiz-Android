package com.dagger.devtermquiz.view.main.model

import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.ResponseCode
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.django.quiz.SearchQuiz
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
    private val _searchSingleQuizData = MutableLiveData<SearchQuiz>()
    val searchSingleQuizData: MutableLiveData<SearchQuiz> get() = _searchSingleQuizData

    override fun onLoadSearchSingleQuizData(id: Int) {
        addDisposable(remoteService.requestSearchSingeQuiz(id = id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchSingleQuizData ->
                Logger.d("Response :: $searchSingleQuizData")
                Logger.d("Code :: ${searchSingleQuizData.code()}")
                Logger.d("Body :: ${searchSingleQuizData.body()}")
                Logger.d("ErrorBody :: ${searchSingleQuizData.errorBody()}")
                when(searchSingleQuizData.code()) {
                    ResponseCode.CODE200.value -> {
                        _searchSingleQuizData.value = searchSingleQuizData.body()
                        getNavigator().dismissProgress()
                    }
                    ResponseCode.CODE404.value -> {
                        getNavigator().dismissProgress()
                        getNavigator().onExhaustQuiz()
                    }
                }
            },{
                Logger.d(it)
            }))
    }

    override fun disposableClear() {
        onCleared()
    }

}