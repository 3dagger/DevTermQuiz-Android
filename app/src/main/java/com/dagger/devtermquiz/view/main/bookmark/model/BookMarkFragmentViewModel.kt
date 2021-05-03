package com.dagger.devtermquiz.view.main.bookmark.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.repository.local.favorite.LocalFavoriteRepoService
import com.dagger.devtermquiz.view.main.bookmark.BookMarkFragmentNavigator
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookMarkFragmentViewModel(private val localFavoriteRepoService: LocalFavoriteRepoService) :
    BaseViewModel<BookMarkFragmentNavigator.View>(), BookMarkFragmentNavigator.ViewModel {

    private val _allFavoriteData = MutableLiveData<List<Favorite>>()
    val allFavoriteData : MutableLiveData<List<Favorite>>
        get() = _allFavoriteData

    private val favoriteData: LiveData<Favorite> by lazy {
        localFavoriteRepoService.getAllFavorite()
    }

    fun onLoadLiveFavoriteData() : LiveData<Favorite> {
        return favoriteData
    }


    override fun disposableClear() {
        onCleared()
    }

    override fun onLoadAllFavoriteData() {
        addDisposable(localFavoriteRepoService.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ allFavoriteData ->
                if(allFavoriteData.isEmpty()) {
                    getNavigator().onRecyclerViewEmpty()
                    getNavigator().dismissProgress()
                }else {
                    getNavigator().onRecyclerViewNotEmpty()
                    _allFavoriteData.value = allFavoriteData
                    getNavigator().dismissProgress()
                }
            },{
                getNavigator().dismissProgress()
            }))

    }

    fun onDeleteById(id: Int) {
        addDisposable(Observable.fromCallable{ localFavoriteRepoService.deleteById(id)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {  })
    }
}