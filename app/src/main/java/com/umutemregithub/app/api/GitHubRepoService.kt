package com.umutemregithub.app.api

import com.umutemregithub.app.models.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepoService {
    @GET("users/{username}/repos")
    suspend fun getUsersRepos(
        @Path("username") username: String
    ): List<GitHubRepo>?
}