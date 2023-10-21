package com.example.marvelapp.ui.character

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.MarvelApp
import com.example.marvelapp.domain.usecases.GetCharactersUseCase
import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.model.CharacterPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : AndroidViewModel(MarvelApp.instance) {
    private val _characterData = MutableLiveData<CharacterPage>()
    val characterData: LiveData<CharacterPage>
        get() = _characterData

    private val _favoriteData = MutableLiveData<List<com.example.marvelapp.domain.model.Character>>()
    val favoriteData: LiveData<List<com.example.marvelapp.domain.model.Character>>
        get() = _favoriteData

    fun getCharacters(keyword: String, page: Int) = viewModelScope.launch {
        getCharactersUseCase.invoke(keyword, page).catch {
            Log.e("CharacterViewModel", "getCharacter Catch ${it.message}")
        }.collect {
            Log.e("CharacterViewModel", "getCharacter Collect ${it.data}")
            _characterData.value = it
        }
    }
    fun getFavorites(){
        //TODO db 연결
        _favoriteData.value = listOf(Character(0,"12312","qweqwe", "https://placehold.co/200", true),
            Character(1,"1555555","skdnkjsdfbsdkjfsdjkfhe", "https://placehold.co/200", true),
            Character(2,"3833","asgdkajshdjsadkjasgdkajshdjsadkjasgdkajshdjsadkjasgdkajshdjsadkj", "https://placehold.co/200", true))
    }
}