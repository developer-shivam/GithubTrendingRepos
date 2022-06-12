package com.shivamsatija.githubtrendingrepos.ui.repositories.domain

import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.util.Response

interface RepositoriesDataManager {

    suspend fun fetchRepositories(): Response<List<Repository>>
}