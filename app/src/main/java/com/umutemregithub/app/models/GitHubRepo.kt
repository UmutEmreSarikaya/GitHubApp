package com.umutemregithub.app.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GitHubRepo(
    @PrimaryKey val id: Int? = null,
    val name: String? = null,
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("forks_count") val forksCount: Int? = null,
    @SerializedName("stargazers_count") val starsCount: Int? = null,
    val language: String? = null,
    val owner: Owner? = null,
    var isFavorite: Boolean? = false
) : Parcelable
