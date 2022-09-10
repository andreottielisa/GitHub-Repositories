package com.andreotti.github.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.andreotti.github.data.repository.GitHubRepository
import com.andreotti.github.domain.converter.RepoConverter
import com.andreotti.github.testing.CoroutineRule
import com.andreotti.github.testing.repoMock
import com.andreotti.github.testing.repoVOMock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetKotlinReposUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: GitHubRepository = mockk(relaxed = true) {
        coEvery { getRepos(any()) } coAnswers { flowOf(PagingData.from(listOf(repoMock))) }
    }
    private val converter: RepoConverter = mockk(relaxed = true) {
        every { convert(any()) } returns repoVOMock
    }
    private val useCase: GetKotlinReposUseCase = GetKotlinReposImp(repository, converter)

    @Test
    fun `when invoking use case, verify if the repository is called`() {
        useCase()

        verify { repository.getRepos(eq("language:kotlin")) }
    }
}