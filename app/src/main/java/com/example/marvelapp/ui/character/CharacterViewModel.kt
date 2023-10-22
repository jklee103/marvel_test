package com.example.marvelapp.ui.character

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.MarvelApp
import com.example.marvelapp.R
import com.example.marvelapp.domain.usecases.GetCharactersUseCase
import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.model.CharacterPage
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
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

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    fun getCharacters(keyword: String, page: Int) = viewModelScope.launch {
        _showProgress.value = true
        getCharactersUseCase.invoke(keyword, page).catch {
            Log.e("CharacterViewModel", "getCharacter Catch ${it.message}")
        }.collect {
            _characterData.value = it
        }
    }
    fun getFavorites(){
        val oldSet = Prefs.getStringSet(MarvelApp.instance.getString(R.string.pref_favorite_set), setOf())
        val gson = Gson()
        val prefList = arrayListOf<Character>()
        oldSet.forEach { prefList.add(gson.fromJson(it, Character::class.java)) }

        _favoriteData.value = prefList
    }
}