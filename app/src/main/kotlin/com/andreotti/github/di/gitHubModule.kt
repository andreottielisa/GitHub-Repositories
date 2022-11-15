package com.andreotti.github.di

import com.andreotti.github.data.GitHubApi
import com.andreotti.github.data.repository.GitHubRepository
import com.andreotti.github.domain.converter.RepoConverter
import com.andreotti.github.domain.usecase.GetKotlinReposUseCase
import com.andreotti.github.ui.viewmodel.RepositoriesViewModel
import com.andreotti.network.NetworkProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val gitHubModule = module {
    factory<GitHubApi> { get<NetworkProvider>().create(GitHubApi::class.java) }

    factory { GitHubRepository(get()) }

    factory { RepoConverter(get()) }

    factory { GetKotlinReposUseCase(get(), get()) }

    viewModel { RepositoriesViewModel(get()) }
}