package com.dagger.devtermquiz.view.old

import androidx.lifecycle.LiveData
import com.dagger.devtermquiz.model.fav.Favorite

interface MainNavigator {
    interface View{
        fun dismissProgress()

        fun onExhaustQuiz()
    }
    interface ViewModel{
        fun onLoadSearchSingleQuizData(id: Int)

        fun onInsertFavoriteData(data: Favorite)

        fun onLoadFavoriteData()

        fun onLoadLiveFavoriteData() : LiveData<Favorite>

        fun disposableClear()
    }
}