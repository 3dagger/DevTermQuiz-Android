package com.dagger.devtermquiz.model.django.quiz

import com.google.gson.annotations.SerializedName

data class AllQuizList(
        @SerializedName("title") var title: String,
        @SerializedName("body") var body: String,
        @SerializedName("answer") var answer: Int
)