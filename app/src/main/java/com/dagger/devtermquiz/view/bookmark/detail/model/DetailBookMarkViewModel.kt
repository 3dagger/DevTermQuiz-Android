package com.dagger.devtermquiz.view.bookmark.detail.model

import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.view.bookmark.detail.DetailBookMarkNavigator

class DetailBookMarkViewModel : BaseViewModel<DetailBookMarkNavigator.View>(), DetailBookMarkNavigator.ViewModel {
    override fun disposableClear() {
        onCleared()
    }
}