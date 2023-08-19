package com.umutemregithub.app.repository

import com.umutemregithub.app.models.GitHubRepo
import kotlinx.coroutines.flow.Flow

interface GitHubRepoRepository {
    fun getUsersRepos(username: String): Flow<List<GitHubRepo>?>
    //suspend fun addRepoAsFavorite(gitHubRepo: GitHubRepo)
    suspend fun removeRepoFromFavorite(gitHubRepo: GitHubRepo)
    fun addOrRemoveRepoFromFavorite(gitHubRepo: GitHubRepo): Flow<Boolean>
    fun getFavoriteRepos(): Flow<List<GitHubRepo>?>
}