package com.rrr.shaket.di

import android.content.Context
import androidx.room.Room
import com.rrr.shaket.data.local.db.FavouriteDao
import com.rrr.shaket.data.local.db.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Dependency injection module for database creation
 */
@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideFavouriteDao(favDatabase: FavouritesDatabase): FavouriteDao {
        return favDatabase.favouriteDao()
    }

    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext appContext: Context): FavouritesDatabase {
        return Room.databaseBuilder(
            appContext,
            FavouritesDatabase::class.java,
            "RssReader"
        ).build()
    }
}