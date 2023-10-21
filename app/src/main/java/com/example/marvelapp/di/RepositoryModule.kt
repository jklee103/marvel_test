package com.example.marvelapp.di

import com.example.marvelapp.data.network.CharacterService
import com.example.marvelapp.data.repository.CharacterRepositoryImpl
import com.example.marvelapp.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun providesViewModelRepository(
        characterService: CharacterService
    ): CharacterRepository = CharacterRepositoryImpl(characterService)
}