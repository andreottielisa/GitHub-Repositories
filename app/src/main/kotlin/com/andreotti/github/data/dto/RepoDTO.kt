package com.andreotti.github.data.dto

import com.google.gson.annotations.SerializedName

internal data class RepoDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val nameRepo: String,
    @SerializedName("full_name") val fullNameRepo: String,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksCount: Long,
    @SerializedName("stargazers_count") val stargazersCount: Long,
    @SerializedName("owner") val owner: OwnerDTO
) {
    data class OwnerDTO(
        @SerializedName("login") val username: String,
        @SerializedName("avatar_url") val avatarUrl: String
    )
}
