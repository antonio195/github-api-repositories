package com.antoniocostadossantos.githubapirepositories.ui.fragments.issuesFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentIssuesBinding
import com.antoniocostadossantos.githubapirepositories.ui.adapter.IssuesAdapter
import com.antoniocostadossantos.githubapirepositories.util.Constants
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startLink
import org.koin.androidx.viewmodel.ext.android.viewModel

class IssuesFragment : Fragment() {

    private lateinit var binding: FragmentIssuesBinding
    private val issuesViewModel: IssuesViewModel by viewModel()
    private lateinit var issuesAdapter: IssuesAdapter
    private lateinit var owner: String
    private lateinit var repositoryName: String
    private var pageIndex = 2

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

        initRecyclerView()
        getIssues()
        paging()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.issuesRecyclerView.adapter = null
    }

    private fun initRecyclerView() {
        issuesAdapter = IssuesAdapter(accessIssueClick = { issueItem ->
            startLink(issueItem.htmlUrl)
        })
        val recyclerView = binding.issuesRecyclerView
        recyclerView.adapter = issuesAdapter
    }

    private fun paging() {
        val recyclerView = binding.issuesRecyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1 && totalItemCount >= 15) {

                    issuesViewModel.getRepoIssues(
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

    private fun getIssues() {
        issuesViewModel.getRepoIssues(owner = owner, repositoryName = repositoryName, 1)
        observeResult()
    }

    private fun observeResult() {
        issuesViewModel.repoIssues.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    (binding.issuesRecyclerView.adapter as? IssuesAdapter)
                        ?.updateData(response.data!!.toList())
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

    companion object {
        fun instance(owner: String, repositoryName: String): IssuesFragment {
            return IssuesFragment().apply {
                arguments = bundleOf(
                    Constants.REPOSITORY_OWNER to owner,
                    Constants.REPOSITORY_NAME to owner
                )
            }
        }
    }

}