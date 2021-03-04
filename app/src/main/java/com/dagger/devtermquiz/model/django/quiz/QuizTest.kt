package com.dagger.devtermquiz.model.django.quiz

import com.google.gson.annotations.SerializedName

data class QuizTest(
    @SerializedName("id") var id: Int,
    @SerializedName("user") var user: User,
    @SerializedName("title") var title: String,
    @SerializedName("subtitle") var subtitle: String,
    @SerializedName("content") var content: String,
    @SerializedName("created_at") var created_at: String
)

data class User(
        @SerializedName("id") var id: Int,
        @SerializedName("username") var username: String,
        @SerializedName("email") var email: String
)