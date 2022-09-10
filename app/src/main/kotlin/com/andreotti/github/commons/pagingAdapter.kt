package com.andreotti.github.commons

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

private const val EMPTY_SIZE = 0

internal fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.isNotEmpty() =
    itemCount > EMPTY_SIZE
