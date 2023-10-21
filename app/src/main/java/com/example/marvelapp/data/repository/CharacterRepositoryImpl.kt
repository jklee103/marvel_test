package com.example.marvelapp.data.repository

import android.util.Log
import com.example.marvelapp.data.network.CharacterService
import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.model.CharacterPage
import com.example.marvelapp.domain.repository.CharacterRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterRepositoryImpl @Inject constructor(private val api: CharacterService) :
    CharacterRepository {
    override fun getCharacters(keyword: String, limit: Int, offset: Int): Flow<CharacterPage> {
        return flow {
            api.getCharacters(keyword, limit, offset).body()?.data?.let { it.toDomain()
                ?.let { it1 -> emit(it1) } }
        }.flowOn(Dispatchers.IO)
    }
}