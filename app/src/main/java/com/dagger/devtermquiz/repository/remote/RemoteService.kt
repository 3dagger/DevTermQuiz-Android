package com.dagger.devtermquiz.repository.remote

import com.dagger.devtermquiz.ApiData
import com.dagger.devtermquiz.model.django.quiz.AllQuizList
import com.dagger.devtermquiz.model.django.quiz.QuizTest
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RemoteService {
    @GET(ApiData.DJANGO_REQUEST_QUIZ_LIST)  fun requestQuizList(): Single<AllQuizList>

    @GET(ApiData.DJANGO_REQUEST_TEST) fun requestQuizTest(): Single<QuizTest>
}