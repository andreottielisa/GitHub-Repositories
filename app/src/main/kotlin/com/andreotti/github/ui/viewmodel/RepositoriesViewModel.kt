package com.andreotti.github.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreotti.github.commons.UiAction
import com.andreotti.github.commons.asLiveData
import com.andreotti.github.domain.usecase.GetKotlinReposUseCase
import com.andreotti.github.domain.vo.RepoVO
import kotlinx.coroutines.flow.distinctUntilChanged

internal class RepositoriesViewModel(private val useCase: GetKotlinReposUseCase) : ViewModel() {

    private val _repositories = MutableLiveData<Unit>()
    val repositories: LiveData<UiAction<PagingData<RepoVO>>> = _repositories.switchMap {
        useCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .asLiveData()
    }

    fun fetchRepositories() {
        _repositories.value = Unit
    }
}