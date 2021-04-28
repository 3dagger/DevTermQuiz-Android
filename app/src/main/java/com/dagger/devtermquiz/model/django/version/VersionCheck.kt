package com.dagger.devtermquiz.model.django.version

import com.google.gson.annotations.SerializedName

data class VersionCheck(
    @SerializedName("count") var count: Int,
    @SerializedName("next") var next: String,
    @SerializedName("previous") var previous: String,
    @SerializedName("results") var results: List<VersionCheckResults>
)

data class VersionCheckResults(
    @SerializedName("id") var id: Int,
    @SerializedName("android_version") var android_version: String,
    @SerializedName("android_msg") var android_msg: String,
    @SerializedName("android_status") var android_status: String,
    @SerializedName("ios_version") var ios_version: String,
    @SerializedName("ios_msg") var ios_msg: String,
    @SerializedName("ios_status") var ios_status: String
)
