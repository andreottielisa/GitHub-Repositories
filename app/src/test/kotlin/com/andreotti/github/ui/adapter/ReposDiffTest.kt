package com.andreotti.github.ui.adapter

import com.andreotti.github.domain.vo.RepoVO
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReposDiffTest {

    private val diff = ReposDiff()

    @Test
    fun `when objects are different, check result if items are not equals`() {

        val object1 = RepoVO(
            nameRepo = "iluwatar/java-design-patterns",
            username = "iluwatar",
            avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
            forksAmount = "75.755",
            forksAmountDescription = "Quantidade de forks: 75.755",
            stargazersAmount = "85.755",
            stargazersAmountDescription = "Quantidade de estrelas: 85.755",
            usernameDescription = "Usuário: iluwatar"
        )

        val object2 = RepoVO(
            nameRepo = "test/test-sample",
            username = "test",
            avatar = "https://avatars.githubusercontent.com/u/002346?v=4",
            forksAmount = "75.755",
            forksAmountDescription = "Quantidade de forks: 75.755",
            stargazersAmount = "85.755",
            stargazersAmountDescription = "Quantidade de estrelas: 85.755",
            usernameDescription = "Usuário: iluwatar"
        )

        assertFalse(diff.areItemsTheSame(object1, object2))
    }

    @Test
    fun `when objects are the same, check result if items are equals`() {
        val object1 = RepoVO(
            nameRepo = "iluwatar/java-design-patterns",
            username = "iluwatar",
            avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
            forksAmount = "75.000",
            forksAmountDescription = "Quantidade de forks: 75.000",
            stargazersAmount = "85.000",
            stargazersAmountDescription = "Quantidade de estrelas: 85.000",
            usernameDescription = "Usuário: iluwatar"
        )

        val object2 = RepoVO(
            nameRepo = "iluwatar/java-design-patterns",
            username = "iluwatar",
            avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
            forksAmount = "75.000",
            forksAmountDescription = "Quantidade de forks: 75.000",
            stargazersAmount = "85.000",
            stargazersAmountDescription = "Quantidade de estrelas: 85.000",
            usernameDescription = "Usuário: iluwatar"
        )

        assertTrue(diff.areItemsTheSame(object1, object2))
    }

    @Test
    fun `when contents are different, check if the result content are not equals`() {
        val object1 = RepoVO(
            nameRepo = "iluwatar/java-design-patterns",
            username = "iluwatar",
            avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
            forksAmount = "75.755",
            forksAmountDescription = "Quantidade de forks: 75.755",
            stargazersAmount = "85.755",
            stargazersAmountDescription = "Quantidade de estrelas: 85.755",
            usernameDescription = "Usuário: iluwatar"
        )

        val object2 = RepoVO(
            nameRepo = "test/test-sample",
            username = "test",
            avatar = "https://avatars.githubusercontent.com/u/002346?v=4",
            forksAmount = "75.755",
            forksAmountDescription = "Quantidade de forks: 75.755",
            stargazersAmount = "85.755",
            stargazersAmountDescription = "Quantidade de estrelas: 85.755",
            usernameDescription = "Usuário: iluwatar"
        )

        assertFalse(diff.areContentsTheSame(object1, object2))
    }

    @Test
    fun `when contents are the same, check if result content are equals`() {
        val object1 = RepoVO(
            nameRepo = "iluwatar/java-design-patterns",
            username = "iluwatar",
            avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
            forksAmount = "75.000",
            forksAmountDescription = "Quantidade de forks: 75.000",
            stargazersAmount = "80.000",
            stargazersAmountDescription = "Quantidade de estrelas: 80.000",
            usernameDescription = "Usuário: iluwatar"
        )

        val object2 = RepoVO(
            nameRepo = "iluwatar/java-design-patterns",
            username = "iluwatar",
            avatar = "https://avatars.githubusercontent.com/u/582346?v=4",
            forksAmount = "75.000",
            forksAmountDescription = "Quantidade de forks: 75.000",
            stargazersAmount = "80.000",
            stargazersAmountDescription = "Quantidade de estrelas: 80.000",
            usernameDescription = "Usuário: iluwatar"
        )

        assertTrue(diff.areContentsTheSame(object1, object2))
    }
}