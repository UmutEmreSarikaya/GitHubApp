package com.umutemregithub.app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umutemregithub.app.models.GitHubRepo

@Dao
interface GitHubRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gitHubRepo: GitHubRepo)
    @Delete
    suspend fun delete(gitHubRepo: GitHubRepo)

    @Query("SELECT * FROM gitHubRepo")
    suspend fun getAll(): List<GitHubRepo>?

    @Query("SELECT * FROM gitHubRepo WHERE id = :itemId")
    suspend fun getItemById(itemId: Int?): GitHubRepo?
}