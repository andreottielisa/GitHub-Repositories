package com.andreotti.github.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource.LoadParams.Prepend
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingSource.LoadResult.Page
import com.andreotti.github.data.GitHubApi
import com.andreotti.github.data.dto.RepoDTO
import com.andreotti.github.testing.CoroutineRule
import com.andreotti.github.testing.repoMock
import com.andreotti.github.testing.searchMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GithubPagingSourceTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: GitHubApi = mockk(relaxed = true)
    private val language = "kotlin"
    private val source = GithubPagingSource(service, language)

    private val loadParams = Prepend(key = 1, loadSize = 1, placeholdersEnabled = false)

    @Test
    fun `when running loading page, verify result`() = runTest {
        val expected = Page(data = listOf(repoMock), prevKey = null, nextKey = 2)

        coEvery { service.getSearchRepositories(any(), any(), any(), any()) } returns searchMock

        val result = source.load(loadParams)

        assertEquals(expected, result)
    }

    @Test
    fun `when running loading last page, verify next key`() = runTest {
        val expected = Page(data = listOf(repoMock), prevKey = 2, nextKey = null)

        coEvery { service.getSearchRepositories(any(), any(), any(), any()) } returns searchMock

        val loadParams = Prepend(key = 3, loadSize = 3, placeholdersEnabled = false)
        val result = source.load(loadParams)

        assertEquals(expected, result)
    }

    @Test
    fun `when running loading page, verify called remote service`() = runTest {
        coEvery { service.getSearchRepositories(page = any(), language = any()) } returns searchMock

        source.load(loadParams)

        coVerify { service.getSearchRepositories(page = 1, language = language) }
    }

    @Test
    fun `when running loading page, with IOException, verify result`() = runTest {
        val exception = IOException()
        val expected = Error<Int, RepoDTO>(exception)

        coEvery { service.getSearchRepositories(page = any(), language = any()) } throws exception

        val result = source.load(loadParams)

        assertEquals(expected, result)
    }

    @Test
    fun `when running loading page, with HttpException, verify result`() = runTest {
        val exception = HttpException(Response.success(404))
        val expected = Error<Int, RepoDTO>(exception)

        coEvery { service.getSearchRepositories(page = any(), language = any()) } throws exception

        val result = source.load(loadParams)

        assertEquals(expected, result)
    }
}