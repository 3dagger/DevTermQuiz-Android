package com.dagger.devtermquiz.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [com.dagger.devtermquiz.model.favorite.Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao(): com.dagger.devtermquiz.repository.local.favorite.LocalFavoriteRepoService
}