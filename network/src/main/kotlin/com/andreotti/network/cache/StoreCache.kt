package com.andreotti.network.cache

import android.content.Context
import com.andreotti.network.BuildConfig.BUILD_TYPE
import okhttp3.Cache
import java.io.File

private const val MAX_CACHE_SIZE = (256 * 1024 * 1024).toLong()
private const val CACHE_NAME = "NETWORK_"

internal class StoreCache(private val context: Context) {

    operator fun invoke() = Cache(getCacheDir(), MAX_CACHE_SIZE)

    private fun getCacheDir() =
        File(context.cacheDir, CACHE_NAME.plus(BUILD_TYPE))
}