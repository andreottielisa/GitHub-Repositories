package com.andreotti.github.di

import android.content.res.Resources
import com.andreotti.github.data.GitHubApi
import com.andreotti.github.data.repository.GitHubRepository
import com.andreotti.github.data.repository.GitHubRepositoryImp
import com.andreotti.github.domain.converter.RepoConverter
import com.andreotti.github.domain.usecase.GetKotlinReposUseCase
import com.andreotti.github.ui.viewmodel.RepositoriesViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.ClosingKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import retrofit2.Retrofit

class GitHubModuleTest : ClosingKoinTest {

    private val mockModule = module {
        factory { mockk<Resources>(relaxed = true) }
        factory {
            mockk<Retrofit> {
                every { create(eq(GitHubApi::class.java)) } returns mockk(relaxed = true)
            }
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(mockModule, gitHubModule)
    }

    @Test
    fun `it should provide GitHubApi`() {
        val api by inject<GitHubApi>()
        assertNotNull(api)
    }

    @Test
    fun `it should provide GitHubRepository`() {
        val repository by inject<GitHubRepository>()
        assertThat(repository, instanceOf(GitHubRepositoryImp::class.java))
    }

    @Test
    fun `it should provide GetKotlinReposUseCase`() {
        val repository by inject<GetKotlinReposUseCase>()
        assertThat(repository, instanceOf(GetKotlinReposUseCase::class.java))
    }

    @Test
    fun `it should provide RepoConverter`() {
        val converter by inject<RepoConverter>()
        assertNotNull(converter)
    }

    @Test
    fun `it should provide ReposViewModel`() {
        val viewModel by inject<RepositoriesViewModel>()
        assertNotNull(viewModel)
    }
}