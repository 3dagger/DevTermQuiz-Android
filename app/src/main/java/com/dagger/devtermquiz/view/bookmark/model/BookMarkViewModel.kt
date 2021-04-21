package com.dagger.devtermquiz.view.bookmark.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.repository.local.favorite.LocalFavoriteRepoService
import com.dagger.devtermquiz.view.bookmark.BookMarkNavigator
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookMarkViewModel(private val localFavoriteRepoService: LocalFavoriteRepoService) :
    BaseViewModel<BookMarkNavigator.View>(), BookMarkNavigator.ViewModel {
    private val _allFavoriteData = MutableLiveData<List<Favorite>>()
    val allFavoriteData : MutableLiveData<List<Favorite>>
        get() = _allFavoriteData

    private val favoriteData: LiveData<Favorite> by lazy {
        localFavoriteRepoService.getAllFavorite()
    }

    fun onLoadLiveFavoriteData() : LiveData<Favorite> {
        return favoriteData
    }

    override fun onLoadAllFavoriteData() {
        addDisposable(localFavoriteRepoService.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ allFavoriteData ->
                _allFavoriteData.value = allFavoriteData
            },{

            }))
    }



    override fun disposableClear() {
        onCleared()
    }
}