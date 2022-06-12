package com.shivamsatija.githubtrendingrepos.data

import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import com.shivamsatija.githubtrendingrepos.di.PerActivity
import com.shivamsatija.githubtrendingrepos.util.getResponse
import javax.inject.Inject

@PerActivity
class RepositoriesDataManagerImpl @Inject constructor(
    private val repositoryService: RepositoryService
) : RepositoriesDataManager {

    override suspend fun fetchRepositories() = getResponse {
        repositoryService.fetchRepositories()
    }
}