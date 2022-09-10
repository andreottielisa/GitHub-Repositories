package com.andreotti.github.di

import com.andreotti.github.BuildConfig.DEBUG
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com"
private const val HEADER_KEY_ACCEPT = "Accept"
private const val HEADER_VALUE_JSON = "application/json"

internal val networkModule = module {
    factory { GsonBuilder().setLenient().create() }

    factory {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (DEBUG) BASIC else NONE
            })
            .addInterceptor { chain ->

                val request = chain.request().newBuilder()
                    .addHeader(HEADER_KEY_ACCEPT, HEADER_VALUE_JSON)
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}