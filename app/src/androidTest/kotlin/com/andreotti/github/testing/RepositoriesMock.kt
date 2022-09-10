package com.andreotti.github.testing

import androidx.paging.PagingData
import com.andreotti.github.domain.vo.RepoVO

internal val pagingDataMock = PagingData.from(
    listOf(
        RepoVO(
            nameRepo = "github-test",
            username = "gitHubTest",
            avatar = "https://avatars.githubusercontent.com/u/15308811?v=4",
            forksAmount = "10.000",
            forksAmountDescription = "Quantidade de forks: 10.000",
            stargazersAmount = "500",
            stargazersAmountDescription = "Quantidade de estrelas: 500",
            usernameDescription = "Usuário: gitHubTest"
        ),
        RepoVO(
            nameRepo = "test-patterns",
            username = "test",
            avatar = "invalid_url",
            forksAmount = "0",
            forksAmountDescription = "Quantidade de forks: 0",
            stargazersAmount = "5",
            stargazersAmountDescription = "Quantidade de estrelas: 5",
            usernameDescription = "Usuário: test"
        )
    )
)