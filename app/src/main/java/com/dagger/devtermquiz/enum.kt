package com.dagger.devtermquiz

enum class ResponseCode(val value: Int, val description: String) {
    CODE200(value = 200, description = "Normal communication"),
    CODE400(value = 400, description = "Wrong request"),
    CODE404(value = 404, description = "No resource requested")
}