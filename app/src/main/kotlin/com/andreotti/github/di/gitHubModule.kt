package com.andreotti.github.di

import com.andreotti.github.data.GitHubApi
import com.andreotti.github.data.repository.GitHubRepository
import com.andreotti.github.data.repository.GitHubRepositoryImp
import com.andreotti.github.domain.converter.RepoConverter
import com.andreotti.github.domain.usecase.GetKotlinReposUseCase
import com.andreotti.github.domain.usecase.GetKotlinReposImp
import com.andreotti.github.ui.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal val gitHubModule = module {
    factory<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }

    factory<GitHubRepository> { GitHubRepositoryImp(get()) }

    factory { RepoConverter(get()) }

    factory<GetKotlinReposUseCase> { GetKotlinReposImp(get(), get()) }

    viewModel { RepositoriesViewModel(get()) }
}