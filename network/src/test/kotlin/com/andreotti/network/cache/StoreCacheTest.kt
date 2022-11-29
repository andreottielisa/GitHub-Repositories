package com.andreotti.network.cache

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.andreotti.network.BuildConfig.BUILD_TYPE
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import kotlin.test.assertEquals

private const val CACHE_SIZE = (256 * 1024 * 1024).toLong()

@RunWith(AndroidJUnit4::class)
class StoreCacheTest {

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val cache = StoreCache(context)

    @Test
    fun `when running invoke method, check if directory is correct`() {
        val expected = File(context.cacheDir, "NETWORK_$BUILD_TYPE")

        assertThat(cache.invoke().directory, samePropertyValuesAs(expected))
    }

    @Test
    fun `when running invoke method, check if max cache size is correct`() {
        assertEquals(CACHE_SIZE, cache.invoke().maxSize())
    }
}