package com.andreotti.github.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreotti.github.domain.usecase.GetKotlinReposUseCase
import com.andreotti.github.domain.vo.RepoVO
import com.andreotti.network.state.LiveDataUiAction
import com.andreotti.network.state.asLiveData
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

internal class RepositoriesViewModel(private val useCase: GetKotlinReposUseCase) : ViewModel() {

    private val _repositories = MutableLiveData<Unit>()
    val repositories: LiveDataUiAction<PagingData<RepoVO>> = _repositories.switchMap {
        useCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .asLiveData()
    }

    fun fetchRepositories() = viewModelScope.launch {
        _repositories.value = Unit
    }
}