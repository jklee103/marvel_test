package com.example.marvelapp.ui.character

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.MarvelApp
import com.example.marvelapp.domain.usecases.GetCharactersUseCase
import com.example.marvelapp.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : AndroidViewModel(MarvelApp.instance) {
    private val _characterData = MutableLiveData<List<Character>>()
    val characterData: LiveData<List<Character>>
        get() = _characterData

    fun getCharacters(keyword: String, page: Int) = viewModelScope.launch {
        getCharactersUseCase.invoke(keyword, page).catch {

        }.collect {
            _characterData.value = it
        }
    }
}