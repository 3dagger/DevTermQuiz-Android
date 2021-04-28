package com.dagger.devtermquiz.model.request.fail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RequestFail(
    @SerializedName("title") var title: String,
    @SerializedName("msg") var msg: String,
    @SerializedName("runnable") var runnable: Runnable
): Serializable
