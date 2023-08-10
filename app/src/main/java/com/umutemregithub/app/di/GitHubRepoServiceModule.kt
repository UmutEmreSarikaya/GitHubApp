package com.umutemregithub.app.di

import com.umutemregithub.app.api.GitHubRepoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GitHubRepoServiceModule {
    @Provides
    @Singleton
    fun provideGitHubRepoService(retrofit: Retrofit): GitHubRepoService {
        return retrofit.create(GitHubRepoService::class.java)
    }
}