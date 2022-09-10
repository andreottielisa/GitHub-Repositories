package com.andreotti.github.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.andreotti.github.data.repository.GitHubRepository
import com.andreotti.github.domain.converter.RepoConverter
import com.andreotti.github.domain.vo.RepoVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val LANGUAGE_REPOS = "language:kotlin"

internal class GetKotlinReposImp(
    private val repository: GitHubRepository,
    private val converter: RepoConverter
) : GetKotlinReposUseCase {

    override operator fun invoke(): Flow<PagingData<RepoVO>> =
        repository.getRepos(LANGUAGE_REPOS)
            .map { pageData ->
                pageData.map(converter::convert)
            }
}