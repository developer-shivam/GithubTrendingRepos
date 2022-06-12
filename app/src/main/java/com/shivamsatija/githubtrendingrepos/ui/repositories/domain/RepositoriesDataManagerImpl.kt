package com.shivamsatija.githubtrendingrepos.ui.repositories.domain

import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import com.shivamsatija.githubtrendingrepos.di.PerActivity
import com.shivamsatija.githubtrendingrepos.util.getResponse
import javax.inject.Inject

class RepositoriesDataManagerImpl @Inject constructor(
    private val repositoryService: RepositoryService
) : RepositoriesDataManager {

    override suspend fun fetchRepositories() = getResponse {
        repositoryService.fetchRepositories()
    }
}