package com.dagger.devtermquiz.view.main.bookmark

interface BookMarkFragmentNavigator {
    interface View {
        fun onRecyclerViewEmpty()
        fun onRecyclerViewNotEmpty()
        fun dismissProgress()
    }
    interface ViewModel {
        fun disposableClear()

        fun onLoadAllFavoriteData()
    }
}