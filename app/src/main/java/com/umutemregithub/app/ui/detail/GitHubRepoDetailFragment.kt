package com.umutemregithub.app.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umutemregithub.app.R
import com.umutemregithub.app.databinding.FragmentGitHubRepoDetailBinding

class GitHubRepoDetailFragment : BottomSheetDialogFragment() {
    private val safeArgs : GitHubRepoDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentGitHubRepoDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_git_hub_repo_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gitHubRepo = safeArgs.gitHubRepo

        binding.btnOpenUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(safeArgs.gitHubRepo.htmlUrl))
            startActivity(intent)
            dismiss()
        }
    }
}