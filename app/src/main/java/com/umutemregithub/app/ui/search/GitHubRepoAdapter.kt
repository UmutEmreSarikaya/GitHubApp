package com.umutemregithub.app.ui.search

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
    var favoriteButtonClickListener: ((GitHubRepo) -> Unit)? = null
    var itemClickListener: ((GitHubRepo) -> Unit)? = null

    class GitHubRepoListViewHolder(
        private val binding: ItemGitHubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            gitHubRepo: GitHubRepo,
            favoriteButtonClickListener: ((GitHubRepo) -> Unit)?,
            itemClickListener: ((GitHubRepo) -> Unit)?
        ) {
            binding.apply {

                this.gitHubRepo = gitHubRepo
                itemLayout.setOnClickListener {
                    itemClickListener?.invoke(gitHubRepo)
                }

                btnFavorite.setOnClickListener {
                    favoriteButtonClickListener?.invoke(gitHubRepo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoListViewHolder {
        binding = ItemGitHubRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitHubRepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubRepoListViewHolder, position: Int) {
        holder.bind(getItem(position), favoriteButtonClickListener, itemClickListener)
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<GitHubRepo>() {
    override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem == newItem
    }
}