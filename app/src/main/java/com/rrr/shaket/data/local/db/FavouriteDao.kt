package com.rrr.shaket.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data access object to perform CRUD operations on favourite db items
 */
@Dao
interface FavouriteDao {
    @Insert
    suspend fun save(vararg favouritePokemon: FavouritePokemon)

    @Delete
    suspend fun remove(favouritePokemon: FavouritePokemon)

    @Query("delete from favourite_table")
    suspend fun deleteAll()

    @Query("select * from favourite_table order by name desc")
    fun getAllFavourites(): Flow<List<FavouritePokemon>>
}