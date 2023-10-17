package com.example.marvelapp.data.repository

import com.example.marvelapp.data.network.CharacterService
import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.repository.CharacterRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterRepositoryImpl @Inject constructor(private val api: CharacterService) : CharacterRepository {
    override fun getCharacters(keyword: String, limit: Int, offset: Int): Flow<List<Character>> {
        return flow {
            api.getCharacters(keyword, limit, offset).body()?.data?.results?.let { emit(it.map { it1 -> it1.toDomain() }) }
        }
    }
}