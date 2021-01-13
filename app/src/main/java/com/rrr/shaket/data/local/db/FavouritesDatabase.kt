package com.rrr.shaket.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database with a table (entity) of FavouritePokemon entries
 */
@Database(entities = [FavouritePokemon::class], version = 1)
abstract class FavouritesDatabase: RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}