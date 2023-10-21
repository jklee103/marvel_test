package com.example.marvelapp.data.network

import com.example.marvelapp.data.model.CharacterDataContainerDTO
import com.example.marvelapp.data.model.ResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiClient {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Int,
        @Query("hash") hash: String
    ): Response<ResponseModel<CharacterDataContainerDTO>>
}