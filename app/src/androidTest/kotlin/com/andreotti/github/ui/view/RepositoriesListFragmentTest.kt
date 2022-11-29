package com.andreotti.github.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreotti.github.R
import com.andreotti.github.domain.vo.RepoVO
import com.andreotti.github.testing.RecyclerViewMatcher
import com.andreotti.github.testing.isRefreshing
import com.andreotti.github.testing.pagingDataMock
import com.andreotti.github.testing.withAccessibilityHeading
import com.andreotti.github.ui.viewmodel.RepositoriesViewModel
import com.andreotti.network.state.ErrorAction
import com.andreotti.network.state.LoadAction
import com.andreotti.network.state.SuccessAction
import com.andreotti.network.state.UiAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

@RunWith(AndroidJUnit4::class)
class RepositoriesListFragmentTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val reposLiveData = MutableLiveData<UiAction<PagingData<RepoVO>>>()

    private val viewModel: RepositoriesViewModel = mockk(relaxed = true) {
        every { repositories } returns reposLiveData
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module {
            viewModel { viewModel }
        })
    }

    @Before
    fun setup() {
        launchFragmentInContainer<RepositoriesListFragment>(
            themeResId = R.style.Theme_GitHub
        )
    }

    @Test
    fun whenLaunch_verifyCallFetchRepositories() {
        verify { viewModel.fetchRepositories() }
    }

    @Test
    fun whenLaunch_checkToolbarIsDisplayed() {
        onView(withId(R.id.repos_toolbar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenLoadingRepositories_checkLoadIsDisplayed() {
        reposLiveData.value = LoadAction

        onView(withId(R.id.repos_refresh_layout))
            .check(matches(isRefreshing()))
    }

    @Test
    fun whenLoadingRepositories_checkAlertViewIsNotDisplayed() {
        reposLiveData.value = LoadAction

        onView(withId(R.id.repos_alert))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun whenSuccessRepositories_checkUserNameIsDisplayed() {
        reposLiveData.value = SuccessAction(pagingDataMock)

        onView(
            RecyclerViewMatcher(R.id.repos_recycler_view)
                .atPosition(0, R.id.item_user_name)
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("gitHubTest"),
                    withContentDescription("Usuário: gitHubTest")
                )
            )
        )
    }

    @Test
    fun whenSuccessRepositories_checkRepoNameIsDisplayed() {
        reposLiveData.value = SuccessAction(pagingDataMock)

        onView(
            RecyclerViewMatcher(R.id.repos_recycler_view)
                .atPosition(0, R.id.item_repo_name)
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withAccessibilityHeading(true),
                    withText("github-test")
                )
            )
        )
    }

    @Test
    fun whenSuccessRepositories_checkForksAmountIsDisplayed() {
        reposLiveData.value = SuccessAction(pagingDataMock)

        onView(
            RecyclerViewMatcher(R.id.repos_recycler_view)
                .atPosition(0, R.id.item_num_forks)
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("10.000"),
                    withContentDescription("Quantidade de forks: 10.000")
                )
            )
        )
    }

    @Test
    fun whenSuccessRepositories_checkStargazersAmountIsDisplayed() {
        reposLiveData.value = SuccessAction(pagingDataMock)

        onView(
            RecyclerViewMatcher(R.id.repos_recycler_view)
                .atPosition(1, R.id.item_num_starts)
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("5"),
                    withContentDescription("Quantidade de estrelas: 5")
                )
            )
        )
    }

    @Test
    fun whenClickTryAgainButton_verifyCallFetchRepositories() {
        reposLiveData.value = ErrorAction(IllegalArgumentException())

        onView(
            allOf(
                withId(R.id.alert_button),
                isDescendantOfA(withId(R.id.repos_alert))
            )
        ).perform(click())

        verify { viewModel.fetchRepositories() }
    }

    @Test
    fun whenErrorRepositories_withPagingDataEmpty_checkSwipeRefreshLayoutIsNotDisplayed() {
        reposLiveData.value = ErrorAction(IllegalArgumentException())

        onView(withId(R.id.repos_refresh_layout))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun whenErrorRepositories_withMessageException_checkMessageIsDisplayed() {
        reposLiveData.value = ErrorAction(IllegalArgumentException("Teste de mensagem de erro"))

        onView(
            allOf(
                withId(R.id.alert_description),
                isDescendantOfA(withId(R.id.repos_alert))
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(), withText("Teste de mensagem de erro")
                )
            )
        )
    }

    @Test
    fun whenErrorRepositories_withoutMessageException_checkDefaultMessageIsDisplayed() {
        reposLiveData.value = ErrorAction(IllegalArgumentException())

        onView(
            allOf(
                withId(R.id.alert_description),
                isDescendantOfA(withId(R.id.repos_alert))
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("Entramos um problema no processamento. Tente novamente mais tarde.")
                )
            )
        )
    }

    @Test
    fun whenErrorRepositories_checkTitleIsDisplayed() {
        reposLiveData.value = ErrorAction(IllegalArgumentException())

        onView(
            allOf(
                withId(R.id.alert_title),
                isDescendantOfA(withId(R.id.repos_alert))
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("Atenção"),
                    withAccessibilityHeading(true)
                )
            )
        )
    }

    @Test
    fun whenErrorRepositories_withPagingDataNotEmpty_checkShowSnackBar() {
        reposLiveData.value = SuccessAction(pagingDataMock)
        reposLiveData.value = ErrorAction(IllegalArgumentException())

        onView(
            withText("Entramos um problema no processamento. Tente novamente mais tarde.")
        ).check(
            matches(isDisplayed())
        )
    }
}