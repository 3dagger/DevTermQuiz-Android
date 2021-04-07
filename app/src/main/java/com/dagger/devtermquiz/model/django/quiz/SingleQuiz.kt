package com.dagger.devtermquiz.model.django.quiz

import com.google.gson.annotations.SerializedName

data class SingleQuiz(
    @SerializedName("count") var count: Int,
    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String,
    @SerializedName("results") var results: List<SingleQuizResults>
)

data class SingleQuizResults(
    @SerializedName("title") var title: String,
    @SerializedName("body") var body: String,
    @SerializedName("answer") var answer: Int,
    @SerializedName("subtitle") var subtitle: String
)