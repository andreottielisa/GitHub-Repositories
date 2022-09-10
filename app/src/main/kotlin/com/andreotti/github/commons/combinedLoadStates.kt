package com.andreotti.github.commons

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

internal fun CombinedLoadStates.isLoading() =
    refresh is LoadState.Loading || append is LoadState.Loading

internal fun CombinedLoadStates.isError() =
    refresh is LoadState.Error || append is LoadState.Error

internal fun CombinedLoadStates.getThrowable(): Throwable? =
    (refresh as? LoadState.Error)?.error ?: (append as? LoadState.Error)?.error