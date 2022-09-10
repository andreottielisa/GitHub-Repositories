package com.andreotti.github.testing

import com.andreotti.github.data.dto.RepoDTO
import com.andreotti.github.data.dto.SearchDTO
import com.andreotti.github.domain.vo.RepoVO

internal val repoMock = RepoDTO(
    id = 1,
    nameRepo = "java-design-patterns",
    fullNameRepo = "iluwatar/java-design-patterns",
    description = "Design patterns implemented in Java",
    forksCount = 75755,
    stargazersCount = 85755,
    owner = RepoDTO.OwnerDTO(
        username = "iluwatar",
        avatarUrl = "https://avatars.githubusercontent.com/u/582346?v=4"
    )
)

internal val repoVOMock = RepoVO(
    nameRepo = "iluwatar/java-design-patterns",
    username = "iluwatar",
    avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
    forksAmount = "75.755",
    forksAmountDescription = "Quantidade de forks: 75.755",
    stargazersAmount = "85.755",
    stargazersAmountDescription = "Quantidade de estrelas: 85.755",
    usernameDescription = "Usuário: iluwatar"
)

internal val searchMock = SearchDTO(
    totalCount = 3,
    items = listOf(repoMock)
)