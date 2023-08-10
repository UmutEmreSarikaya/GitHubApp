package com.umutemregithub.app.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutemregithub.app.models.GitHubRepo
import com.umutemregithub.app.repository.GitHubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository
) : ViewModel() {
    private val _favoriteRepos = MutableSharedFlow<List<GitHubRepo>>(1)
    val favoriteRepos: SharedFlow<List<GitHubRepo>> = _favoriteRepos

    fun getFavoriteRepos() {
        viewModelScope.launch {
            gitHubRepoRepository.getFavoriteRepos().collect {
                it?.let {
                    _favoriteRepos.emit(it)
                }
            }
        }
    }

    fun removeRepoFromFavorite(gitHubRepo: GitHubRepo) {
        viewModelScope.launch {
            gitHubRepoRepository.removeRepoFromFavorite(gitHubRepo)
        }
    }
}