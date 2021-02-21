package com.dagger.devtermquiz.di

import com.dagger.devtermquiz.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var networkModules = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single<Gson> {
        GsonBuilder().create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            cache(get())
            connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 연결 실패시 재 시도 할 것인가?
            addInterceptor(HttpLoggingInterceptor().apply {
//                if (BuildConfig.DEBUG) {
//                    level = HttpLoggingInterceptor.Level.BODY
//                }
            })
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
//            .baseUrl(ApiData.getHostUrl())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
    }
}