package com.umutemregithub.app.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.umutemregithub.app.R

@BindingAdapter("app:showRepoLanguage")
fun TextView.showRepoLanguage(language: String?) {
    if (language != null) {
        this.text = language
    } else {
        this.text = resources.getText(R.string.no_language_detected)
    }
}

/*@BindingAdapter("app:markIfFavorite")
fun ImageView.markIfFavorite(id: Int?) {
    this.setImageResource(if (gitHubRepoDao.getItemById(id) != null) R.drawable.baseline_favorite_24_red else R.drawable.baseline_favorite_24)
}*/