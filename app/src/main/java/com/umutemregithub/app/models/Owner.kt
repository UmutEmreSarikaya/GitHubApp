package com.umutemregithub.app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val login: String? = null,
    val id: Int? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null
): Parcelable
