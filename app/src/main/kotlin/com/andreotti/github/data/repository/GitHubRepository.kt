package com.andreotti.github.data.repository

import androidx.paging.PagingData
import com.andreotti.github.data.dto.RepoDTO
import kotlinx.coroutines.flow.Flow

internal interface GitHubRepository {

    fun getRepos(language: String): Flow<PagingData<RepoDTO>>
}