package com.umutemregithub.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutemregithub.app.models.GitHubRepo
import com.umutemregithub.app.repository.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository
) : ViewModel() {
    private val _favoriteRepos = MutableSharedFlow<List<GitHubRepo>>(1)
    val favoriteRepos = _favoriteRepos.asSharedFlow()

    private val _searchedRepos = MutableSharedFlow<List<GitHubRepo>>()
    val searchedRepos = _searchedRepos.asSharedFlow()

    private val _isRepoNotFound = MutableSharedFlow<Boolean>(1)
    val isRepoNotFound = _isRepoNotFound.asSharedFlow()

    private val _updateRow = MutableSharedFlow<Pair<GitHubRepo, Int>>()
    val updateRow = _updateRow.asSharedFlow()

    private val _searchedUsername = MutableStateFlow("")
    val searchedUsername = _searchedUsername.asStateFlow()

    private val _searchedUserRepoCount = MutableStateFlow("")
    val searchedUserRepoCount = _searchedUserRepoCount.asStateFlow()

    private val _searchedUserAvatarUrl = MutableStateFlow("")
    val searchedUserAvatarUrl = _searchedUserAvatarUrl.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _changedItem = MutableSharedFlow<GitHubRepo>()
    val changedItem = _changedItem.asSharedFlow()

    fun searchUsersRepos(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            gitHubRepoRepository.getUsersRepos(username).catch {
                _isRepoNotFound.emit(true)
            }.collect {
                it?.let {
                    _searchedUserAvatarUrl.value = it[0].owner?.avatarUrl.toString()
                    _searchedUsername.value = it[0].owner?.login.toString()
                    _searchedUserRepoCount.value = it.size.toString()
                    _searchedRepos.emit(it)
                    _isRepoNotFound.emit(false)
                }
            }
            _isLoading.value = false
        }
    }

    fun addOrRemoveRepoFromFavorite(gitHubRepo: GitHubRepo, position: Int) {
        viewModelScope.launch {
            gitHubRepoRepository.addOrRemoveRepoFromFavorite(gitHubRepo).collect {
                _updateRow.emit(Pair(gitHubRepo.apply { isFavorite = it }, position))
            }
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
            _changedItem.emit(gitHubRepo)
            collectFavoriteRepos()
        }
    }

    private suspend fun collectFavoriteRepos() {
        gitHubRepoRepository.getFavoriteRepos().collect {
            it?.let {
                _favoriteRepos.emit(it)
            }
        }
    }
}