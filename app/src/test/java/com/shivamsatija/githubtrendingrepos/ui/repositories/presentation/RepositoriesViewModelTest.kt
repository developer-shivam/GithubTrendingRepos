package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.util.Response
import com.shivamsatija.githubtrendingrepos.util.ViewState
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

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoriesViewModel(dataManager).apply {
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
                .thenReturn(Response.Success(listOf(repository)))

            // when
            viewModel.fetchRepositories()

            // then
            verify(repositoriesObserver).onChanged(ViewState.Success(listOf(repository)))
        }
    }
}