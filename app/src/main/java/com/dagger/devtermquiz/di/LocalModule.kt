package com.dagger.devtermquiz.di

import androidx.room.Room
import com.dagger.devtermquiz.room.AppDatabase
import org.koin.dsl.module

var localModules = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "soohyun").build() }

    single { get<AppDatabase>().favoriteDao() }
}