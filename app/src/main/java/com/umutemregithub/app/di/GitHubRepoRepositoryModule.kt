package com.umutemregithub.app.di

import com.umutemregithub.app.repository.GitHubRepoRepository
import com.umutemregithub.app.repository.GitHubRepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GitHubRepoRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSuggestionRepository(
        gitHubRepoRepositoryImpl: GitHubRepoRepositoryImpl
    ): GitHubRepoRepository
}