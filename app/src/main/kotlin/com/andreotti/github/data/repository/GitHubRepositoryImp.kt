package com.andreotti.github.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreotti.github.data.GitHubApi
import com.andreotti.github.data.dto.RepoDTO
import com.andreotti.github.data.source.GithubPagingSource
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 10
private const val PAGE_PLACE_HOLDERS = false

internal class GitHubRepositoryImp(private val service: GitHubApi) : GitHubRepository {

    override fun getRepos(language: String): Flow<PagingData<RepoDTO>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = PAGE_PLACE_HOLDERS
            ),
            pagingSourceFactory = { GithubPagingSource(service, language) }
        ).flow
}