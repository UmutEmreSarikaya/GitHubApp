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
            //val favoriteRepos = gitHubRepoDao.getAll()
            val response = gitHubRepoService.getUsersRepos(username)
            if (response != null) {/*response.forEach { repo ->
                    val result = favoriteRepos?.contains(repo)
                    Log.d("myLog", "result repo " + repo.name + ": " + result.toString() + "\n")
                    repo.isFavorite = result
                }*/
                /*response.forEach{ repo ->
                    favoriteRepos?.forEach{favoriteRepo ->
                        if(repo.id == favoriteRepo.id){
                            repo.isFavorite = true
                        }
                    }
                }*/
                response.forEach { repo ->
                    if (gitHubRepoDao.getItemById(repo.id) != null) {
                        repo.isFavorite = true
                    }
                }
                emit(response)
            }
        }
    }

    /*override suspend fun addRepoAsFavorite(gitHubRepo: GitHubRepo) {
        gitHubRepoDao.insert(gitHubRepo)
    }*/

    override suspend fun removeRepoFromFavorite(gitHubRepo: GitHubRepo) {
        gitHubRepoDao.delete(gitHubRepo)
    }

    override fun addOrRemoveRepoFromFavorite(gitHubRepo: GitHubRepo): Flow<Boolean> {
        return flow {
            if (gitHubRepoDao.getItemById(gitHubRepo.id) != null) {
                emit(false)
                gitHubRepoDao.delete(gitHubRepo)
            } else {
                emit(true)
                gitHubRepoDao.insert(gitHubRepo.apply { isFavorite = true })
            }
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