package com.rrr.shaket.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rrr.shakemon.IServiceCallback
import com.rrr.shakemon.ShakemonManager
import com.rrr.shaket.data.Result
import com.rrr.shaket.data.local.model.Pokemon
import java.lang.Exception

/**
 * Search screen viewmodel to handle pokemon search and returns results from sdk
 * Class that integrated "shakemon" SDK
 */
class SearchViewModel : ViewModel() {

    val TAG = SearchViewModel::class.java.simpleName
    private val _pokemon = Pokemon()

    private val _searchResult = MutableLiveData<Result<Pokemon>>().apply {
        Result.Loading
    }
    val searchResult: LiveData<Result<Pokemon>> = _searchResult

    /**
     * searches for give pokemon and gets its ShakespeareTex and it's Sprite
     */
    fun searchPokemon(name: String) {
        _searchResult.postValue(Result.Loading)
        _pokemon.reset()
        _pokemon.name = name
        ShakemonManager.INSTANCE.getShakespeareText(name, object : IServiceCallback{
            override fun success(text: String) {
                _pokemon.shakespeareDescription = text
                publishData()
            }

            override fun error(exception: Exception) {
                publishError(exception)
            }
        })

        ShakemonManager.INSTANCE.getPokemonSprite(name, object : IServiceCallback{
            override fun success(text: String) {
                _pokemon.pokemonSprite = text
                publishData()
            }

            override fun error(exception: Exception) {
                publishError(exception)
            }
        })
    }

    /**
     * publishes success results asynchronously to UI
     */
    private fun publishData() {
        if(!_pokemon.isEmpty()){
            _searchResult.postValue(Result.Success(_pokemon))
        }
    }

    /**
     * publishes error results asynchronously to UI
     */
    private fun publishError(exception: Exception) {
        _searchResult.postValue(Result.Error(exception))
    }
}