package com.andreotti.github.domain.vo

internal data class RepoVO(
    val nameRepo: String,
    val username: String,
    val avatar: String,
    val forksAmount: String,
    val forksAmountDescription: String,
    val stargazersAmount: String,
    val stargazersAmountDescription: String,
    val usernameDescription: String
)