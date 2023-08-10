package com.umutemregithub.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val login: String? = null,
    val id: Int? = null
): Parcelable
