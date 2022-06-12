package com.shivamsatija.githubtrendingrepos.ui.repositories.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import com.shivamsatija.githubtrendingrepos.util.Response
import com.shivamsatija.githubtrendingrepos.utils.MockWebServerBaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class RepositoriesDataManagerTest : MockWebServerBaseTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var repositoryService: RepositoryService

    // Subject under test
    private lateinit var repositoriesDataManager: RepositoriesDataManager

    @Before
    fun start() {
        MockitoAnnotations.initMocks(this)
        repositoryService = provideTestRepositoryService()
        repositoriesDataManager = RepositoriesDataManagerImpl(repositoryService)
    }

    @Test
    fun givenSuccessResponse_whenFetchRepositories_shouldReturnSuccess() {
        runBlocking {
            // Given
            mockHttpResponseWithUri("json/success_response.json", HttpURLConnection.HTTP_OK)

            // When
            val actual = repositoriesDataManager.fetchRepositories()

            // Then
            Assert.assertTrue(actual is Response.Success)
            actual as Response.Success
            Assert.assertEquals(actual.data[0].username, "borisdayma")
        }
    }

    @Test
    fun givenNoResponse_whenFetchCurrentWeather_shouldReturnError() {
        runBlocking {

            // Given
            mockHttpResponse("", HttpURLConnection.HTTP_OK)

            // When
            val actual = repositoriesDataManager.fetchRepositories()

            // Then
            Assert.assertTrue(actual is Response.Error)
        }
    }
}