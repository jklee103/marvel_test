package com.example.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    @SerializedName("code") val code: Int = -1,
    @SerializedName("status") val status: String? = null,
    @SerializedName("data") val data: T? = null
)
