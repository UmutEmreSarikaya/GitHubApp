package com.umutemregithub.app.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutemregithub.app.R
import com.umutemregithub.app.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val gitHubRepoAdapter = GitHubRepoAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerRepo.adapter = gitHubRepoAdapter.apply {
            itemClickListener = {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToGitHubRepoDetailFragment(it)
                findNavController().navigate(action)
            }

            favoriteButtonClickListener = { gitHubRepo ->
                viewModel.addRepoAsFavorite(gitHubRepo)
            }
        }

        initObservers()
        initClickListeners()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.repos.collect {
                gitHubRepoAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.isRepoNotFound.collect { isRepoNotFound ->
                if (isRepoNotFound) {
                    binding.apply {
                        recyclerRepo.visibility = View.GONE
                        tvUserNotFound.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        recyclerRepo.visibility = View.VISIBLE
                        tvUserNotFound.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.btnSearch.setOnClickListener {
            viewModel.searchUsersRepos(binding.etUsername.text.toString())
        }
    }
}