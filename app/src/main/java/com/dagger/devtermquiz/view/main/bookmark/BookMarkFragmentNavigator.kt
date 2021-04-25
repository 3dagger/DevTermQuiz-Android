package com.dagger.devtermquiz.view.main.bookmark

interface BookMarkFragmentNavigator {
    interface View {

    }
    interface ViewModel {
        fun disposableClear()

        fun onLoadAllFavoriteData()
    }
}