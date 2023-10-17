package com.example.marvelapp.domain.repository

import com.example.marvelapp.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(keyword: String, limit: Int, offset: Int): Flow<List<Character>>
}