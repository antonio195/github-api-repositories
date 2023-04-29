package com.antoniocostadossantos.githubapirepositories.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentIssuesBinding
import com.antoniocostadossantos.githubapirepositories.ui.WebViewActivity
import com.antoniocostadossantos.githubapirepositories.ui.adapter.IssuesAdapter
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IssuesFragment : Fragment() {

    private lateinit var binding: FragmentIssuesBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var issuesAdapter: IssuesAdapter
    private lateinit var owner: String
    private lateinit var repositoryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIssuesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owner = arguments?.getString("OWNER_REPOSITORY")!!
        repositoryName = arguments?.getString("NAME_REPOSITORY")!!

        getIssues()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        issuesAdapter = IssuesAdapter(requireContext(), accessIssueClick = { issueItem ->
            startActivity(
                Intent(requireContext(), WebViewActivity::class.java).apply {
                    putExtra("URL", issueItem.html_url)
                }
            )
        })

        val recyclerView = binding.issuesRecyclerView
        recyclerView.adapter = issuesAdapter
    }

    private fun getIssues() {

        mainViewModel.getRepoIssues(owner = owner, repositoryName = repositoryName)
        observeResult()
    }

    private fun observeResult() {
        mainViewModel.repoIssues.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    issuesAdapter.setItems(response.data!!)
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