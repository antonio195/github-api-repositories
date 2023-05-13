package com.antoniocostadossantos.githubapirepositories.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentCommitsBinding
import com.antoniocostadossantos.githubapirepositories.ui.adapter.CommitsAdapter
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startLink
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommitsFragment : Fragment() {

    private lateinit var binding: FragmentCommitsBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var commitsAdapter: CommitsAdapter
    private lateinit var owner: String
    private lateinit var repositoryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommitsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owner = arguments?.getString("OWNER_REPOSITORY")!!
        repositoryName = arguments?.getString("NAME_REPOSITORY")!!
        mainViewModel.getRepoCommits(owner, repositoryName)
        getCommits()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        commitsAdapter = CommitsAdapter(requireContext(), acessCommitClick = { commitItem ->
            startLink(commitItem.html_url)
        })

        val recyclerView = binding.commitRecyclerView
        recyclerView.adapter = commitsAdapter
    }

    private fun getCommits() {

        mainViewModel.repoCommits.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    commitsAdapter.setItems(response.data!!)
                }

                is StateResource.Loading -> {
                    Toast.makeText(requireContext(), "Carregando", Toast.LENGTH_SHORT).show()
                }

                is StateResource.Error -> {
                    Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show()
                    Log.e("ERRO_REQUEST", response.message.toString())
                }
            }
        }
    }
}