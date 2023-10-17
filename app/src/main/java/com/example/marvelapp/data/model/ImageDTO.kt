package com.example.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
)
