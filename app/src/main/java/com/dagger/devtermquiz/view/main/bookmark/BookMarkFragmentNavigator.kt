package com.dagger.devtermquiz.view.main.bookmark

interface BookMarkFragmentNavigator {
    interface View {
        fun onRecyclerViewEmpty()
        fun onRecyclerViewNotEmpty()

    }
    interface ViewModel {
        fun disposableClear()

        fun onLoadAllFavoriteData()
    }
}