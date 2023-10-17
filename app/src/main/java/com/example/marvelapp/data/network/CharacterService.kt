package com.example.marvelapp.data.network

import javax.inject.Inject

class CharacterService @Inject constructor(private val api: CharacterApiClient) {
    suspend fun getCharacters(keyword: String, limit: Int, offset: Int) =
        api.getCharacters(keyword, limit, offset)
}