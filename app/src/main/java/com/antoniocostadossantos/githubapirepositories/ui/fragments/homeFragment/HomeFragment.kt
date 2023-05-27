package com.antoniocostadossantos.githubapirepositories.ui.fragments.homeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentHomeBinding
import com.antoniocostadossantos.githubapirepositories.model.repo.ItemResponse
import com.antoniocostadossantos.githubapirepositories.ui.adapter.RepositoriesAdapter
import com.antoniocostadossantos.githubapirepositories.ui.fragments.descriptionFragment.DescriptionRepositoryFragment
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startFragment
import com.antoniocostadossantos.githubapirepositories.validation.ModelValidation
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var pageIndex = 2
    private lateinit var binding: FragmentHomeBinding
    private lateinit var repositoriesAdapter: RepositoriesAdapter
    private val homeViewModel: HomeViewModel by viewModel()
    private val modelValidation = ModelValidation()
    private var language: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        searchBar()
        paging()
        observeResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.homeRecyclerView.adapter = null
    }

    private fun paging() {
        val recyclerView = binding.homeRecyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1 && totalItemCount >= 15) {

                    homeViewModel.getAllRepositories(
                        language = language,
                        page = pageIndex
                    )
                    pageIndex++

//                    homeViewModel.getRepositoriesInPage()
                }
            }
        })
    }

    private fun searchBar() {
        binding.searchLabel.setEndIconOnClickListener {

            val searchInput = binding.searchInput.text.toString()

            when (modelValidation.checkLanguage(searchInput)) {
                true -> {
                    Toast.makeText(requireContext(), "Digite a linguagem", Toast.LENGTH_SHORT)
                        .show()
                    binding.searchLabel.errorIconDrawable = null
                    binding.searchLabel.error = "Digite a linguagem"

                }

                false -> {
//                    homeViewModel.language = searchInput
                    language = searchInput
                    binding.searchLabel.error = null
                    homeViewModel.getAllRepositories(
                        language = searchInput,
                        page = 1
                    )

                }
            }
        }
    }

    private fun observeResult() {
        homeViewModel.repositories.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
//                    (binding.homeRecyclerView.adapter as? RepositoriesAdapter)
//                        ?.updateData(response.data!!.items)

                    repositoriesAdapter.updateData(response.data!!.items)
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

    private fun initRecyclerView() {
        repositoriesAdapter = RepositoriesAdapter() { itemClick ->
            showDescription(itemClick)
        }
        val recyclerView = binding.homeRecyclerView
        recyclerView.adapter = repositoriesAdapter
    }

    private fun showDescription(item: ItemResponse) {
        startFragment(DescriptionRepositoryFragment(), Bundle().apply {
            putString("OWNER_REPOSITORY", item.owner.login)
            putString("NAME_REPOSITORY", item.name)
        })
    }

}