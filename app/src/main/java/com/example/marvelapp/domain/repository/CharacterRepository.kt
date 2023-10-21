package com.example.marvelapp.domain.repository

import com.example.marvelapp.domain.model.CharacterPage
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(keyword: String, limit: Int, offset: Int): Flow<CharacterPage>
}