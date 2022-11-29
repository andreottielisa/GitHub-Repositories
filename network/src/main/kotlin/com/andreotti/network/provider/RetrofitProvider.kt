package com.andreotti.network.provider

import com.andreotti.network.NetworkProvider
import com.andreotti.network.di.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

internal class RetrofitProvider(
    private val httpClient: OkHttpClient,
    private val factory: Converter.Factory
) : NetworkProvider {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(factory)
            .build()
    }

    override fun <T> create(service: Class<T>): T =
        retrofit.create(service)
}