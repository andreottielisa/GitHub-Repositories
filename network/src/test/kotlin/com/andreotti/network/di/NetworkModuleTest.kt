package com.andreotti.network.di

import com.andreotti.network.NetworkProvider
import com.andreotti.network.cache.CacheInterceptor
import com.andreotti.network.provider.RetrofitProvider
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.koin.test.ClosingKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class NetworkModuleTest : ClosingKoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(networkModule)
    }

    @Test
    fun `it should provide Gson`() {
        val gson by inject<Gson>()
        assertNotNull(gson)
    }

    @Test
    fun `it should provide OkHttpClient`() {
        val okHttpClient by inject<OkHttpClient>()
        assertNotNull(okHttpClient)
    }

    @Test
    fun `it should provide NetworkProvider`() {
        val provider by inject<NetworkProvider>()
        assertThat(provider, instanceOf(RetrofitProvider::class.java))
    }

    @Test
    fun `it should provide CacheInterceptor`() {
        val interceptor by inject<Interceptor>()
        assertThat(interceptor, instanceOf(CacheInterceptor::class.java))
    }

    @Test
    fun `it should provide StoreCache`() {
        val cache by inject<Cache>()
        assertNotNull(cache)
    }
}