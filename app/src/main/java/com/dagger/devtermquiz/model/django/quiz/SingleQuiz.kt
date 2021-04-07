package com.dagger.devtermquiz.model.django.quiz

import com.google.gson.annotations.SerializedName

data class SingleQuiz(
    @SerializedName("count") var count: Int,
    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String,
    @SerializedName("results") var results: List<SingleQuizResults>
)

data class SingleQuizResults(
    @SerializedName("question") var question: String,
    @SerializedName("answer") var answer: Int,
    @SerializedName("ex") var ex: List<SingleQuizExample>,
    @SerializedName("comm") var comm: List<SingleQuizCommentary>
)

data class SingleQuizExample(
    @SerializedName("firstExample") var firstExample: String,
    @SerializedName("secondExample") var secondExample: String,
    @SerializedName("thirdExample") var thirdExample: String,
    @SerializedName("fourthExample") var fourthExample: String
)

data class SingleQuizCommentary(
    @SerializedName("firstCommentary") var firstCommentary: String,
    @SerializedName("secondCommentary") var secondCommentary: String,
    @SerializedName("thirdCommentary") var thirdCommentary: String,
    @SerializedName("fourthCommentary") var fourthCommentary: String
)