package com.example.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class CharacterDataContainerDTO(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: ArrayList<CharacterDTO>
)