package com.andreotti.github.commons

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import io.mockk.mockk
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.beans.SamePropertyValuesAs
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CombinedLoadStatesTest {

    private val source = LoadStates(
        refresh = LoadState.NotLoading(endOfPaginationReached = false),
        prepend = LoadState.NotLoading(endOfPaginationReached = false),
        append = LoadState.NotLoading(endOfPaginationReached = false)
    )

    @Test
    fun `when called isLoading method, with refresh loading, check result is correct`() {
        val loadStates = CombinedLoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
            source = source
        )

        assertTrue(loadStates.isLoading())
    }

    @Test
    fun `when called isLoading method, with append loading, check result is correct`() {
        val loadStates = CombinedLoadStates(
            append = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.NotLoading(false),
            source = source
        )

        assertTrue(loadStates.isLoading())
    }

    @Test
    fun `when called isLoading method, with append and refresh status different from loading, check result is correct`() {
        val loadStates = CombinedLoadStates(
            append = LoadState.Error(mockk(relaxed = true)),
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.NotLoading(false),
            source = source
        )

        assertFalse(loadStates.isLoading())
    }

    @Test
    fun `when called isError method, with append status of the error, check result is correct`() {
        val loadStates = CombinedLoadStates(
            append = LoadState.Error(mockk(relaxed = true)),
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.NotLoading(false),
            source = source
        )

        assertTrue(loadStates.isError())
    }

    @Test
    fun `when called isError method, with refresh status of the error, check result is correct`() {
        val loadStates = CombinedLoadStates(
            refresh = LoadState.Error(mockk(relaxed = true)),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
            source = source
        )

        assertTrue(loadStates.isError())
    }

    @Test
    fun `when called isLoading method, with append and refresh status different from error, check result is correct`() {
        val loadStates = CombinedLoadStates(
            append = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.NotLoading(false),
            source = source
        )

        assertFalse(loadStates.isError())
    }

    @Test
    fun `when called getThrowable method, with refresh status of the error, check result is correct`() {
        val expected = Exception("Test")
        val loadStates = CombinedLoadStates(
            append = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.Error(expected),
            source = source
        )

        assertThat(loadStates.getThrowable(), SamePropertyValuesAs(expected, emptyList()))
    }

    @Test
    fun `when called getThrowable method, with append status of the error, check result is correct`() {
        val expected = Exception("Test")
        val loadStates = CombinedLoadStates(
            append = LoadState.Error(expected),
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.NotLoading(false),
            source = source
        )

        assertThat(loadStates.getThrowable(), SamePropertyValuesAs(expected, emptyList()))
    }

    @Test
    fun `when called getThrowable method, with append and refresh status different from error, check result is correct`() {
        val loadStates = CombinedLoadStates(
            append = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            refresh = LoadState.NotLoading(false),
            source = source
        )

        assertNull(loadStates.getThrowable())
    }
}


