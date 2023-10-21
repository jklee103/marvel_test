package com.example.marvelapp.data.network

import com.example.marvelapp.BuildConfig
import javax.inject.Inject

class CharacterService @Inject constructor(private val api: CharacterApiClient) {
    suspend fun getCharacters(keyword: String, limit: Int, offset: Int) =
        api.getCharacters(keyword, limit, offset, BuildConfig.API_KEY, 1, BuildConfig.HASH_KEY)
}