package com.dagger.devtermquiz.model

import com.google.gson.annotations.SerializedName

data class QuizList(
        @SerializedName("key") val key: String?,
        @SerializedName("value") val value: String?
)