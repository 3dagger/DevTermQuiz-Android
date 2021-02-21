package com.dagger.devtermquiz.di

import com.dagger.devtermquiz.repository.remote.RemoteService
import org.koin.dsl.module
import retrofit2.Retrofit

var apiModules = module {
    single(createdAtStart = false) {get<Retrofit>().create(RemoteService::class.java)}
}