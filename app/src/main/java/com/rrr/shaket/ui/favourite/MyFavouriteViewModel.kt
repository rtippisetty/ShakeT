package com.rrr.shaket.ui.favourite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rrr.shaket.data.local.FavouritesRepository
import com.rrr.shaket.data.local.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Favourites screen Viewmodel to handle display, save or remove from the db
 */
class MyFavouriteViewModel @ViewModelInject constructor(private val repository: FavouritesRepository)
    : ViewModel() {

    /**
     * Gets all saved favourites from db
     */
    val favouritePokemons: LiveData<List<Pokemon>> =
        repository.allFavourites.asLiveData().map { list -> list.map { Pokemon(it) } }

    /**
     * saves favourite pokemon to local db
     */
    fun saveFavourite(pokemon: Pokemon) {
        CoroutineScope(Dispatchers.Default).launch { repository.save(pokemon) }
    }

    /**
     * removes pokemon from favourite's db
     */
    fun removeFavourite(pokemon: Pokemon) {
        CoroutineScope(Dispatchers.Default).launch { repository.remove(pokemon) }
    }
}