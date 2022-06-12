package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.util.Response
import com.shivamsatija.githubtrendingrepos.util.ViewState
import com.shivamsatija.githubtrendingrepos.utils.TestCoroutineContextProvider
import com.shivamsatija.githubtrendingrepos.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
class RepositoriesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var dataManager: RepositoriesDataManager

    @Mock
    private lateinit var repositoriesObserver: Observer<ViewState<List<Repository>>>

    //SUT
    private lateinit var viewModel: RepositoriesViewModel

    private var repository1 = Repository(username = "developer-shivam")
    private var repository2 = Repository(username = "google")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoriesViewModel(dataManager, TestCoroutineContextProvider()).apply {
            repositoriesLiveData.observeForever(repositoriesObserver)
        }
    }

    @Test
    fun givenError_whenFetchRepositories_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            // given
            whenever(dataManager.fetchRepositories())
                .thenReturn(Response.Error())

            // when
            viewModel.fetchRepositories()

            // then
            verify(repositoriesObserver).onChanged(ViewState.Error())
        }
    }

    @Test
    fun givenSuccessResponse_whenFetchRepositories_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            // given
            whenever(dataManager.fetchRepositories())
                .thenReturn(Response.Success(listOf(repository1, repository2)))

            // when
            viewModel.fetchRepositories()

            // then
            verify(repositoriesObserver).onChanged(
                ViewState.Success(listOf(repository1, repository2))
            )
        }
    }

    @Test
    fun givenRepositories_whenSearchRepositories_shouldReturnFilteredRepositories() {
        testCoroutineRule.runBlockingTest {
            // given
            val successResponse = listOf(repository1, repository2)
            viewModel.originalRepositories.addAll(successResponse)
            // Test won't work with delay
            viewModel.DELAY = 0

            // when
            viewModel.searchLocal("google")

            // then
            verify(repositoriesObserver)
                .onChanged(ViewState.Success(listOf(repository2)))
        }
    }
}