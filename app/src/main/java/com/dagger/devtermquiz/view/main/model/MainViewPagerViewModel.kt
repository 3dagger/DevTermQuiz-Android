package com.dagger.devtermquiz.view.main.model

import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.view.main.MainViewPagerNavigator

class MainViewPagerViewModel: BaseViewModel<MainViewPagerNavigator.View>(), MainViewPagerNavigator.ViewModel {


    override fun disposableClear() {
        onCleared()
    }
}