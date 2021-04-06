package com.dagger.devtermquiz.repository.remote

import com.dagger.devtermquiz.ApiData
import com.dagger.devtermquiz.model.django.quiz.SingleQuiz
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RemoteService {
//    @GET(ApiData.DJANGO_REQUEST_QUIZ_LIST)  fun requestQuizList(): Single<AllQuizList>

    @GET(ApiData.DJANGO_REQUEST_SINGLE_QUIZ) fun requestSingleQuiz() : Single<SingleQuiz>

}