package com.umutemregithub.app.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.umutemregithub.app.R

@BindingAdapter("app:showRepoLanguage")
fun TextView.showRepoLanguage(language: String?) {
    if (language != null) {
        this.text = language
    } else {
        this.text = resources.getText(R.string.no_language_detected)
    }
}

@BindingAdapter("app:markIfFavorite")
fun ImageView.markIfFavorite(isFavorite: Boolean) {
    this.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24_red else R.drawable.baseline_favorite_24)
}

@BindingAdapter("app:loadImageFromURL")
fun ImageView.loadImageFromURL(url: String) {
    Glide.with(this.context).load(url).into(this)
}