package com.shivamsatija.githubtrendingrepos.data

import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.util.Response

interface RepositoriesDataManager {

    suspend fun fetchRepositories(): Response<List<Repository>>
}