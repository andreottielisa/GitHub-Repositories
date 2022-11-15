package com.andreotti.network.di

import com.andreotti.network.BuildConfig.DEBUG
import com.andreotti.network.NetworkProvider
import com.andreotti.network.cache.CacheInterceptor
import com.andreotti.network.cache.StoreCache
import com.andreotti.network.provider.RetrofitProvider
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

internal const val BASE_URL = "https://api.github.com"
private const val HEADER_KEY_ACCEPT = "Accept"
private const val HEADER_VALUE_JSON = "application/json"

val networkModule = module {
    factory { GsonBuilder().setLenient().create() }
    factory<Converter.Factory> { GsonConverterFactory.create(get()) }

    factory<Interceptor> { CacheInterceptor() }
    factory<Cache> { StoreCache(androidContext()).invoke() }

    factory {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (DEBUG) BASIC else NONE
            })
            .cache(get())
            .addNetworkInterceptor(get<Interceptor>())
            .addInterceptor { chain ->

                val request = chain.request().newBuilder()
                    .addHeader(HEADER_KEY_ACCEPT, HEADER_VALUE_JSON)
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    single<NetworkProvider> { RetrofitProvider(get(), get()) }
}