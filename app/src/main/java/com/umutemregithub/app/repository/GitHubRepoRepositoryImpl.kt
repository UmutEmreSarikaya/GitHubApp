package com.umutemregithub.app.repository

import com.umutemregithub.app.api.GitHubRepoService
import com.umutemregithub.app.db.GitHubRepoDao
import com.umutemregithub.app.models.GitHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitHubRepoRepositoryImpl @Inject constructor(
    private val gitHubRepoService: GitHubRepoService, private val gitHubRepoDao: GitHubRepoDao
) : GitHubRepoRepository {
    override fun getUsersRepos(username: String): Flow<List<GitHubRepo>?> {
        return flow {
            val response = gitHubRepoService.getUsersRepos(username)
            if (response != null) {
                emit(response)
            }
        }
    }

    override suspend fun addRepoAsFavorite(gitHubRepo: GitHubRepo) {
        gitHubRepoDao.insert(gitHubRepo)
    }

    override suspend fun removeRepoFromFavorite(gitHubRepo: GitHubRepo) {
        gitHubRepoDao.delete(gitHubRepo)
    }

    override suspend fun addOrRemoveRepoFromFavorite(gitHubRepo: GitHubRepo) {
        if(gitHubRepoDao.getItemById(gitHubRepo.id) != null){
            gitHubRepoDao.delete(gitHubRepo)
        } else {
            gitHubRepoDao.insert(gitHubRepo)
        }
    }

    override fun getFavoriteRepos(): Flow<List<GitHubRepo>?> {
        return flow {
            val response = gitHubRepoDao.getAll()
            if (response != null) {
                emit(response)
            }
        }
    }
}