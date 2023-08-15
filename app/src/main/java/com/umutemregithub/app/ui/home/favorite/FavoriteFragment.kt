package com.umutemregithub.app.ui.home.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutemregithub.app.R
import com.umutemregithub.app.ui.home.SharedViewModel
import com.umutemregithub.app.ui.home.search.GitHubRepoAdapter
import com.umutemregithub.app.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //private val viewModel: FavoriteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var binding: FragmentFavoriteBinding
    private val gitHubRepoAdapter = GitHubRepoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.recyclerFavorite.adapter = gitHubRepoAdapter.apply {
            itemClickListener = {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToGitHubRepoDetailFragment(it)
                findNavController().navigate(action)
            }

            gitHubRepoAdapter.favoriteButtonClickListener = {gitHubRepo, position->
                sharedViewModel.apply {
                //viewModel.apply {
                    removeRepoFromFavorite(gitHubRepo)
                    //getFavoriteRepos()
                }
            }
        }

        sharedViewModel.getFavoriteRepos()
        //viewModel.getFavoriteRepos()

    }

    private fun initObservers(){
        lifecycleScope.launch {
            sharedViewModel.favoriteRepos.collect {
            //viewModel.favoriteRepos.collect {
                gitHubRepoAdapter.submitList(it)
            }
        }
    }
}