package com.dagger.devtermquiz.view.main

import com.dagger.devtermquiz.model.favorite.Favorite

interface MainNavigator {
    interface View{
        fun dismissProgress()

        fun onExhaustQuiz()
    }
    interface ViewModel{
        fun onLoadSearchSingleQuizData(id: Int)

        fun onInsertFavoriteData(data: Favorite)

        fun onLoadFavoriteData()

        fun disposableClear()
    }
}