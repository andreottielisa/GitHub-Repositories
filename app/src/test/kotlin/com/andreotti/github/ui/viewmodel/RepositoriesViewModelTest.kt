package com.andreotti.github.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.andreotti.github.domain.usecase.GetKotlinReposUseCase
import com.andreotti.github.domain.vo.RepoVO
import com.andreotti.github.testing.CoroutineRule
import com.andreotti.github.testing.repoVOMock
import com.andreotti.network.state.UiAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoriesViewModelTest {

    private val useCase: GetKotlinReposUseCase = mockk(relaxed = true)
    private val viewModel = RepositoriesViewModel(useCase)

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `when fetchRepositories, verify if observer changes`() {
        every { useCase.invoke() } returns flowOf(PagingData.from(listOf(repoVOMock)))

        val observer: Observer<UiAction<PagingData<RepoVO>>> = mockk(relaxed = true)

        viewModel.repositories.observeForever(observer)
        viewModel.fetchRepositories()

        verify { observer.onChanged(any()) }
    }

    @Test
    fun `when fetchRepositories, verify if use case called`() {
        viewModel.repositories.observeForever(mockk(relaxed = true))
        viewModel.fetchRepositories()

        verify { useCase.invoke() }
    }
}