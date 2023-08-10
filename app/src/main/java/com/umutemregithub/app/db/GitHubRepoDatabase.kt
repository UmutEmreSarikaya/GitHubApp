package com.umutemregithub.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.umutemregithub.app.models.GitHubRepo

@Database(entities = [GitHubRepo::class], version = 1)
@TypeConverters(Converters::class)
abstract class GitHubRepoDatabase : RoomDatabase() {
    abstract fun gitHubRepoDao(): GitHubRepoDao
}