package com.rrr.shaket.data.local

import androidx.annotation.WorkerThread
import com.rrr.shaket.data.local.db.FavouriteDao
import com.rrr.shaket.data.local.db.FavouritePokemon
import com.rrr.shaket.data.local.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  Repository manages queries and allows you to use multiple backends
 *  For this application we only requires local data base for CRUD operations
 */
class FavouritesRepository @Inject constructor(private val favouriteDao: FavouriteDao) {

    val allFavourites: Flow<List<FavouritePokemon>> = favouriteDao.getAllFavourites()

    @WorkerThread
    suspend fun save(pokemon: Pokemon) {
        favouriteDao.save(FavouritePokemon(pokemon))
    }

    @WorkerThread
    suspend fun remove(pokemon: Pokemon) {
        favouriteDao.remove(FavouritePokemon(pokemon))
    }
}