package com.dagger.devtermquiz.view.main.bookmark.detail.model

import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.view.main.bookmark.detail.DetailBookMarkNavigator

class DetailBookMarkViewModel : BaseViewModel<DetailBookMarkNavigator.View>(), DetailBookMarkNavigator.ViewModel {
    override fun disposableClear() {
        onCleared()
    }
}