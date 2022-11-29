package com.andreotti.network.cache

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

private const val MAX_AGE = 5
private const val HEADER_CACHE_CONTROL = "Cache-Control"
private const val HEADER_PRAGMA = "Pragma"

internal class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(MAX_AGE, TimeUnit.SECONDS)
            .build()

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}