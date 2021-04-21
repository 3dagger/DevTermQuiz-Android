package com.dagger.devtermquiz.view.bookmark

interface BookMarkNavigator {
    interface View{

    }
    interface ViewModel{
        fun disposableClear()

        fun onLoadAllFavoriteData()
    }
}