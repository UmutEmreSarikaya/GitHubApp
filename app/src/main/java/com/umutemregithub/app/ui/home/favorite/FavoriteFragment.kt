package com.umutemregithub.app.ui.home.favorite

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutemregithub.app.R
import com.umutemregithub.app.databinding.FragmentFavoriteBinding
import com.umutemregithub.app.ui.home.SharedViewModel
import com.umutemregithub.app.ui.home.search.GitHubRepoAdapter
import com.umutemregithub.app.ui.home.search.SearchUIState
import com.umutemregithub.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    //private val viewModel: FavoriteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    //private lateinit var binding: FragmentFavoriteBinding
    override val layoutRes = R.layout.fragment_favorite
    private val gitHubRepoAdapter = GitHubRepoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFavorite.adapter = gitHubRepoAdapter.apply {
            itemClickListener = {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToGitHubRepoDetailFragment(it)
                findNavController().navigate(action)
            }

            gitHubRepoAdapter.favoriteButtonClickListener = { gitHubRepo, _ ->
                sharedViewModel.removeRepoFromFavorite(gitHubRepo)
                sharedViewModel.setUIState(SearchUIState.Loading)
            }
        }

        sharedViewModel.getFavoriteRepos()
        //viewModel.getFavoriteRepos()

    }

    override fun initObservers() {
        super.initObservers()
        lifecycleScope.launch {
            sharedViewModel.favoriteRepos.collect {
                //viewModel.favoriteRepos.collect {
                gitHubRepoAdapter.submitList(it)
            }
        }
    }
}