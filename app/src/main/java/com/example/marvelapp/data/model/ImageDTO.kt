package com.example.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("extension") val type: String,
    @SerializedName("path") val url: String
)
