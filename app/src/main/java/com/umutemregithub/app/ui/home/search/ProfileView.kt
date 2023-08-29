package com.umutemregithub.app.ui.home.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.umutemregithub.app.R
import com.umutemregithub.app.databinding.ProfileViewBinding

class ProfileView(context: Context, attrs: AttributeSet) : CardView(context, attrs) {
    private val binding: ProfileViewBinding

    init {
        binding = ProfileViewBinding.inflate(LayoutInflater.from(context), this, true)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProfileView)


        binding.apply {
            tvUsername.setTextColor(
                attributes.getColor(
                    R.styleable.ProfileView_usernameTextColor,
                    0
                )
            )
            tvUsername.setTextColor(
                attributes.getColor(
                    R.styleable.ProfileView_usernameTextColor,
                    0
                )
            )
        }
        attributes.recycle()
    }

    fun setProfileUrl(imageUrl: String) {
        Glide.with(context).load(imageUrl).into(binding.ivUser)
    }

    fun setUsernameText(username: String) {
        binding.tvUsername.text = username
    }

    fun setRepoCountText(repoCount: String) {
        binding.tvRepoCount.text = repoCount
    }

    fun setProfileImageClickListener(onClickListener: (()-> Unit)? = null){
        binding.ivUser.setOnClickListener {
            onClickListener?.invoke()
        }
    }
}