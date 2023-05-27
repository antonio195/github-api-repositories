package com.antoniocostadossantos.githubapirepositories.ui.fragments.commitsFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentCommitsBinding
import com.antoniocostadossantos.githubapirepositories.ui.adapter.CommitsAdapter
import com.antoniocostadossantos.githubapirepositories.util.Constants.Companion.REPOSITORY_NAME
import com.antoniocostadossantos.githubapirepositories.util.Constants.Companion.REPOSITORY_OWNER
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startLink
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommitsFragment private constructor() : Fragment() {

    private lateinit var binding: FragmentCommitsBinding
    private val commitsViewModel: CommitsViewModel by viewModel()
    private lateinit var commitsAdapter: CommitsAdapter
    private lateinit var owner: String
    private lateinit var repositoryName: String
    private var pageIndex = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommitsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.commitRecyclerView.adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owner = arguments?.getString("OWNER_REPOSITORY")!!
        repositoryName = arguments?.getString("NAME_REPOSITORY")!!
        commitsViewModel.getRepoCommits(owner, repositoryName, 1)
        getCommits()
        initRecyclerView()
        paging()
    }

    private fun initRecyclerView() {
        commitsAdapter = CommitsAdapter(acessCommitClick = { commitItem ->
            startLink(commitItem.htmlUrl)
        })

        val recyclerView = binding.commitRecyclerView
        recyclerView.adapter = commitsAdapter
    }

    companion object {
        fun instance(owner: String, repositoryName: String): CommitsFragment {
            return CommitsFragment().apply {
                arguments = bundleOf(
                    REPOSITORY_OWNER to owner,
                    REPOSITORY_NAME to repositoryName
                )
            }
        }
    }

    private fun paging() {
        val recyclerView = binding.commitRecyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1 && totalItemCount >= 15) {

                    commitsViewModel.getRepoCommits(
                        owner = owner,
                        repositoryName = repositoryName,
                        page = pageIndex
                    )
                    pageIndex++

//                    homeViewModel.getRepositoriesInPage()
                }
            }
        })
    }

    private fun getCommits() {

        commitsViewModel.repoCommits.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
//                    (binding.commitRecyclerView.adapter as? CommitsAdapter)
//                        ?.updateData(response.data!!)
                    commitsAdapter.updateData(response.data!!)
                }

                is StateResource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                is StateResource.Error -> {
                    Log.e("ERRO_REQUEST", response.message.toString())
                }
            }
        }
    }
}