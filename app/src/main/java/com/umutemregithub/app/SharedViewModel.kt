package com.umutemregithub.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutemregithub.app.models.GitHubRepo
import com.umutemregithub.app.repository.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository
): ViewModel() {
    private val _favoriteRepos = MutableSharedFlow<List<GitHubRepo>>(1)
    val favoriteRepos: SharedFlow<List<GitHubRepo>> = _favoriteRepos

    private val _searchedRepos = MutableSharedFlow<List<GitHubRepo>>(1)
    val searchedRepos: SharedFlow<List<GitHubRepo>> = _searchedRepos

    private val _isRepoNotFound = MutableSharedFlow<Boolean>(1)
    val isRepoNotFound: SharedFlow<Boolean> = _isRepoNotFound

    fun searchUsersRepos(username: String) {
        viewModelScope.launch {
            gitHubRepoRepository.getUsersRepos(username).catch {
                _isRepoNotFound.emit(true)
            }.collect {
                it?.let {
                    _searchedRepos.emit(it)
                    _isRepoNotFound.emit(false)
                }
            }
        }
    }
    fun addOrRemoveRepoFromFavorite(gitHubRepo: GitHubRepo){
        viewModelScope.launch {
            gitHubRepoRepository.addOrRemoveRepoFromFavorite(gitHubRepo)
        }
    }

    fun getFavoriteRepos() {
        viewModelScope.launch {
            collectFavoriteRepos()
        }
    }

    fun removeRepoFromFavorite(gitHubRepo: GitHubRepo) {
        viewModelScope.launch {
            gitHubRepoRepository.removeRepoFromFavorite(gitHubRepo)
            collectFavoriteRepos()
        }
    }

    private suspend fun collectFavoriteRepos(){
        gitHubRepoRepository.getFavoriteRepos().collect {
            it?.let {
                _favoriteRepos.emit(it)
            }
        }
    }
}