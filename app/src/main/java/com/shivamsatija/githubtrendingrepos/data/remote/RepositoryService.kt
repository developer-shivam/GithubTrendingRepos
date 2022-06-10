package com.shivamsatija.githubtrendingrepos.data.remote

import com.shivamsatija.githubtrendingrepos.data.model.Repository
import retrofit2.http.GET

interface RepositoryService {

    @GET("repositories")
    suspend fun fetchRepositories(): List<Repository>
}