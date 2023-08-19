package com.umutemregithub.app.ui.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.umutemregithub.app.R
import com.umutemregithub.app.ui.home.SharedViewModel
import com.umutemregithub.app.databinding.FragmentSearchBinding
import com.umutemregithub.app.util.gone
import com.umutemregithub.app.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    //private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var binding: FragmentSearchBinding
    private val gitHubRepoAdapter = GitHubRepoAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentSearchBinding?>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        ).apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
        }
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

            favoriteButtonClickListener = { gitHubRepo, position ->
                //viewModel.addOrRemoveRepoFromFavorite(gitHubRepo)
                sharedViewModel.addOrRemoveRepoFromFavorite(gitHubRepo, position)
            }
        }


        initObservers()
        /*if(sharedViewModel.searchedUsername.value != ""){
            sharedViewModel.searchUsersRepos(sharedViewModel.searchedUsername.value)
        }*/
        initClickListeners()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            sharedViewModel.searchedRepos.collect {
                //viewModel.repos.collect {
                gitHubRepoAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            sharedViewModel.isRepoNotFound.collect { isRepoNotFound ->
                //viewModel.isRepoNotFound.collect { isRepoNotFound ->
                if (isRepoNotFound) {
                    binding.apply {
                        cvUserInfo.gone()
                        recyclerRepo.gone()
                        tvUserNotFound.visible()
                    }

                } else {
                    binding.apply {
                        cvUserInfo.visible()
                        recyclerRepo.visible()
                        tvUserNotFound.gone()
                    }
                }
            }
        }

        lifecycleScope.launch {
            sharedViewModel.updateRow.collect {
                gitHubRepoAdapter.notifyItemChanged(it.second, it.first)
            }
        }

        lifecycleScope.launch {
            sharedViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visible()

                } else {
                    binding.progressBar.gone()
                }
            }
        }

        /*lifecycleScope.launch {
            sharedViewModel.changedItem.collect{repo->
                val filteredList = gitHubRepoAdapter.currentList.filter {
                    it.id!= repo.id
                }
                val index = gitHubRepoAdapter.currentList.indexOf(repo)
                withContext(Dispatchers.Main){
                    gitHubRepoAdapter.notifyItemChanged(index, repo.apply { isFavorite = false })
                    //gitHubRepoAdapter.submitList(null)
                    gitHubRepoAdapter.notifyDataSetChanged()
                    binding.recyclerRepo.adapter = gitHubRepoAdapter
                }
            }
        }*/
    }

    private fun initClickListeners() {

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
                        cvUserInfo.gone()
                        recyclerRepo.gone()
                        tvUserNotFound.gone()
                    }
                }
                return false
            }
        })
    }
}