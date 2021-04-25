package com.dagger.devtermquiz.view.main.quiz.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.ResponseCode
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.django.quiz.SearchQuiz
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.repository.local.favorite.LocalFavoriteRepoService
import com.dagger.devtermquiz.repository.remote.RemoteService
import com.dagger.devtermquiz.view.main.quiz.QuizFragmentNavigator
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuizFragmentViewModel(private val remoteService: RemoteService,
                            private val localFavoriteRepoService: LocalFavoriteRepoService
): BaseViewModel<QuizFragmentNavigator.View>(),
    QuizFragmentNavigator.ViewModel {
    private val _searchSingleQuizData = MutableLiveData<SearchQuiz>()
    val searchSingleQuizData: MutableLiveData<SearchQuiz> get() = _searchSingleQuizData

    override fun onLoadSearchSingleQuizData(id: Int) {
        addDisposable(remoteService.requestSearchSingeQuiz(id = id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchSingleQuizData ->
//                Logger.d("Response :: $searchSingleQuizData")
//                Logger.d("Code :: ${searchSingleQuizData.code()}")
//                Logger.d("Body :: ${searchSingleQuizData.body()}")
//                Logger.d("ErrorBody :: ${searchSingleQuizData.errorBody()}")
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

    override fun onInsertFavoriteData(data: Favorite) {
        addDisposable(Observable.fromCallable{ localFavoriteRepoService.insertAll((data)) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {  }
        )

    }

    private val favoriteData: LiveData<Favorite> by lazy {
        localFavoriteRepoService.getAllFavorite()
    }

    override fun onLoadLiveFavoriteData() : LiveData<Favorite> {
        return favoriteData
    }



    override fun onLoadFavoriteData() {
//        addDisposable(localFavoriteRepoService.getAllFavorite()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
////                _favoriteAllData.value = it
//            }, {
//
//            }))
    }

    override fun disposableClear() {
        onCleared()
    }

}