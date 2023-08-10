package com.umutemregithub.app.di

import android.content.Context
import androidx.room.Room
import com.umutemregithub.app.db.GitHubRepoDao
import com.umutemregithub.app.db.GitHubRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGitHubRepoDao(@ApplicationContext applicationContext: Context): GitHubRepoDao {
        return Room.databaseBuilder(
            applicationContext,
            GitHubRepoDatabase::class.java,
            "repo-database"
        )
            .build().gitHubRepoDao()
    }
}