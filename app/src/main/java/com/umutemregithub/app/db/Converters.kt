package com.umutemregithub.app.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.umutemregithub.app.models.Owner

class Converters {
    @TypeConverter
    fun fromOwner(owner: Owner): String{
        return Gson().toJson(owner)
    }

    @TypeConverter
    fun toOwner(json: String): Owner{
        return Gson().fromJson(json, Owner::class.java)
    }
}