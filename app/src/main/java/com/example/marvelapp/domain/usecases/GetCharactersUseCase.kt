package com.example.marvelapp.domain.usecases

import com.example.marvelapp.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    fun invoke(
        keyword: String,
        offset: Int
    ) = repository.getCharacters(keyword, 10, offset)
}