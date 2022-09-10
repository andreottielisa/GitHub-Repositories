package com.andreotti.github.domain.usecase

import androidx.paging.PagingData
import com.andreotti.github.domain.vo.RepoVO
import kotlinx.coroutines.flow.Flow

internal interface GetKotlinReposUseCase {

    operator fun invoke(): Flow<PagingData<RepoVO>>
}