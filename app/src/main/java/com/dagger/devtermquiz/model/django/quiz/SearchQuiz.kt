package com.dagger.devtermquiz.model.django.quiz

import com.google.gson.annotations.SerializedName

data class SearchQuiz(
    @SerializedName("id") var id: Int,
    @SerializedName("question") var question: String,
    @SerializedName("answer") var answer: Int,
    @SerializedName("firstExample") var firstExample: String,
    @SerializedName("secondExample") var secondExample: String,
    @SerializedName("thirdExample") var thirdExample: String,
    @SerializedName("fourthExample") var fourthExample: String,
    @SerializedName("firstCommentary") var firstCommentary: String,
    @SerializedName("secondCommentary") var secondCommentary: String,
    @SerializedName("thirdCommentary") var thirdCommentary: String,
    @SerializedName("fourthCommentary") var fourthCommentary: String
)
