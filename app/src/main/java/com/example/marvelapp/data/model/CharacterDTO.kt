package com.example.marvelapp.data.model

import com.google.gson.annotations.SerializedName
import com.example.marvelapp.domain.model.Character

data class CharacterDTO(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String,
    @SerializedName("resourceURI") val thumbnail: String
){
    fun toDomain() = Character(
        id = id,
        name = name,
        description = description,
        imageUrl = thumbnail
    )
}
