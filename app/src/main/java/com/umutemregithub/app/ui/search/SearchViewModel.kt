package com.umutemregithub.app.ui.search

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
class SearchViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository
) : ViewModel() {
    private val _repos = MutableSharedFlow<List<GitHubRepo>>(1)
    val repos: SharedFlow<List<GitHubRepo>> = _repos

    private val _isRepoNotFound = MutableSharedFlow<Boolean>(1)
    val isRepoNotFound: SharedFlow<Boolean> = _isRepoNotFound

    fun searchUsersRepos(username: String) {
        viewModelScope.launch {
            gitHubRepoRepository.getUsersRepos(username).catch {
                _isRepoNotFound.emit(true)
            }.collect {
                it?.let {
                    _repos.emit(it)
                    _isRepoNotFound.emit(false)
                }
            }
        }
    }
    fun addRepoAsFavorite(gitHubRepo: GitHubRepo){
        viewModelScope.launch {
            gitHubRepoRepository.addRepoAsFavorite(gitHubRepo)
        }
    }
}