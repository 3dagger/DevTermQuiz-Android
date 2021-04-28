package com.dagger.devtermquiz.view.main.quiz

import androidx.lifecycle.LiveData
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.model.request.fail.RequestFail

interface QuizFragmentNavigator {
    interface View{
        fun dismissProgress()

        fun onExhaustQuiz()

        fun onRequestFailed(requestFail: RequestFail)
    }
    interface ViewModel{
        fun onLoadSearchSingleQuizData(id: Int)

        fun onInsertFavoriteData(data: Favorite)

        fun onLoadFavoriteData()

        fun onLoadLiveFavoriteData() : LiveData<Favorite>

        fun disposableClear()
    }
}