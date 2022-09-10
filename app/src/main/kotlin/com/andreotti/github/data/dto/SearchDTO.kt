package com.andreotti.github.data.dto

import com.google.gson.annotations.SerializedName

internal data class SearchDTO(
    @SerializedName("total_count") val totalCount: Long,
    @SerializedName("items") val items: List<RepoDTO>? = null
)