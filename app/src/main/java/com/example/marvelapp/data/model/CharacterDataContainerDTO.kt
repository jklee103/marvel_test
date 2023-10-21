package com.example.marvelapp.data.model

import android.util.Log
import com.example.marvelapp.domain.model.CharacterPage
import com.google.gson.annotations.SerializedName

data class CharacterDataContainerDTO(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: ArrayList<CharacterDTO>
) {
    fun toDomain(): CharacterPage?{
        return results.let { it.map { it1 -> it1.toDomain() } }?.let {
            CharacterPage(
                hasNext = total > count + offset,
                data = it
            )
        }
    }
}
