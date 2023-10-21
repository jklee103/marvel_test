package com.example.marvelapp.domain.model

import com.example.marvelapp.domain.model.Character

data class CharacterPage(
    val hasNext: Boolean,
    val data: List<Character>
)
