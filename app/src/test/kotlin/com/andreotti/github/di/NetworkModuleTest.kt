package com.andreotti.github.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.koin.test.ClosingKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import retrofit2.Retrofit

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
    fun `it should provide Retrofit`() {
        val retrofit by inject<Retrofit>()
        assertNotNull(retrofit)
    }
}