package com.andreotti.network.cache

import io.mockk.every
import io.mockk.mockk
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CacheInterceptorTest {

    private val request = Request.Builder()
        .addHeader("Cache-Control", "no-cache")
        .addHeader("Pragma", "no-cache")
        .url("https://localhost:8080/")
        .build()

    private val response: Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_1_1)
        .code(200)
        .message("Test")
        .body("abc".toResponseBody())
        .build()

    private val mockChain: Interceptor.Chain = mockk(relaxed = true) {
        every { request() } returns request
        every { proceed(any()) } returns response
    }

    @Test
    fun `when intercept, check header Cache-Control was updated`() {
        val response = CacheInterceptor()
            .intercept(mockChain)

        assertEquals("max-age=5", response.headers["Cache-Control"])
    }

    @Test
    fun `when intercept, check header Pragma was deleted`() {
        val response = CacheInterceptor()
            .intercept(mockChain)

        assertNull(response.headers["Pragma"])
    }
}