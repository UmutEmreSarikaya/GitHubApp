package com.umutemregithub.app.ui.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umutemregithub.app.databinding.ItemGitHubRepoBinding
import com.umutemregithub.app.models.GitHubRepo

class GitHubRepoAdapter : ListAdapter<GitHubRepo, GitHubRepoAdapter.GitHubRepoListViewHolder>(
    CharacterDiffCallback
) {
    private lateinit var binding: ItemGitHubRepoBinding
    var favoriteButtonClickListener: ((GitHubRepo, Int) -> Unit)? = null
    var itemClickListener: ((GitHubRepo) -> Unit)? = null

    class GitHubRepoListViewHolder(
        private val binding: ItemGitHubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            gitHubRepo: GitHubRepo,
            favoriteButtonClickListener: ((GitHubRepo, Int) -> Unit)?,
            itemClickListener: ((GitHubRepo) -> Unit)?,
            position: Int
        ) {
            binding.apply {

                this.item = gitHubRepo
                itemLayout.setOnClickListener {
                    itemClickListener?.invoke(gitHubRepo)
                }

                btnFavorite.setOnClickListener {
                    favoriteButtonClickListener?.invoke(gitHubRepo, position)
                }

                languageView.setProfileView(gitHubRepo.language)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoListViewHolder {
        binding = ItemGitHubRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitHubRepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubRepoListViewHolder, position: Int) {
        holder.bind(getItem(position), favoriteButtonClickListener, itemClickListener, position)
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<GitHubRepo>() {
    override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
    }

    override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem == newItem
    }
}