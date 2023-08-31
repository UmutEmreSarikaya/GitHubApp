package com.umutemregithub.app.ui.home.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.umutemregithub.app.R
import com.umutemregithub.app.databinding.FragmentSearchBinding
import com.umutemregithub.app.ui.home.SharedViewModel
import com.umutemregithub.app.util.gone
import com.umutemregithub.app.util.visible
import com.umutemregithub.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    //private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    //private lateinit var binding: FragmentSearchBinding
    override val layoutRes = R.layout.fragment_search
    private val gitHubRepoAdapter = GitHubRepoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = sharedViewModel

        binding.recyclerRepo.adapter = gitHubRepoAdapter.apply {
            itemClickListener = {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToGitHubRepoDetailFragment(it)
                findNavController().navigate(action)
            }

            favoriteButtonClickListener = { gitHubRepo, position ->
                //viewModel.addOrRemoveRepoFromFavorite(gitHubRepo)
                sharedViewModel.addOrRemoveRepoFromFavorite(gitHubRepo, position)
            }
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.searchedRepos.collect {
                    //viewModel.repos.collect {
                    gitHubRepoAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.isRepoNotFound.collect { isRepoNotFound ->
                    //viewModel.isRepoNotFound.collect { isRepoNotFound ->
                    if (isRepoNotFound) {
                        binding.apply {
                            profileView.gone()
                            recyclerRepo.gone()
                            tvUserNotFound.visible()
                        }

                    } else {
                        binding.apply {
                            profileView.visible()
                            recyclerRepo.visible()
                            tvUserNotFound.gone()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.updateRow.collect {
                    gitHubRepoAdapter.notifyItemChanged(it.second, it.first)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        binding.progressBar.visible()

                    } else {
                        binding.progressBar.gone()
                    }
                }
            }
        }

        /*lifecycleScope.launch {
            sharedViewModel.changedItem.collect { repo ->
                val indexOfRepo = gitHubRepoAdapter.currentList.indexOf(repo)
                Log.d("myLog", "repo: $repo")
                Log.d("myLog", "index: $indexOfRepo")
                if (indexOfRepo != -1) {
                    val updatedList = gitHubRepoAdapter.currentList.toMutableList()
                    updatedList[indexOfRepo] = repo.apply { isFavorite = false }
                    gitHubRepoAdapter.submitList(updatedList)
                    //gitHubRepoAdapter.notifyItemChanged(index, repo.apply { isFavorite = false })
                }
            }
        }*/

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collect {
                    when (it) {
                        SearchUIState.Loading -> {
                            if (sharedViewModel.searchedUsername.value != "") {
                                sharedViewModel.searchUsersRepos(sharedViewModel.searchedUsername.value)
                            }
                        }

                        SearchUIState.Loaded -> {

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.searchedUserAvatarUrl.collect {
                    binding.profileView.setProfileUrl(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.searchedUsername.collect {
                    binding.profileView.setUsernameText(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.searchedUserRepoCount.collect {
                    binding.profileView.setRepoCountText(
                        String.format(
                            getString(R.string.repository_count_label),
                            it
                        )
                    )
                }
            }
        }

        /*lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.searchedUserProfileUrl.collect {
                    sharedViewModel.profileUrl = it
                }
            }
        }*/
    }

    override fun initClickListener() {
        super.initClickListener()
        binding.profileView.setProfileImageClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sharedViewModel.searchedUserProfileUrl))
            startActivity(intent)
        }

        binding.svUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    sharedViewModel.searchUsersRepos(query)
                    //viewModel.searchUsersRepos(query)
                    binding.svUsername.clearFocus()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == "") {
                    binding.apply {
                        profileView.gone()
                        recyclerRepo.gone()
                        tvUserNotFound.gone()
                    }
                }
                return false
            }
        })
    }
}