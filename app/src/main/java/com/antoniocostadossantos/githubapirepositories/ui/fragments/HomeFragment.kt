package com.antoniocostadossantos.githubapirepositories.ui.fragments

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
import com.antoniocostadossantos.githubapirepositories.model.repo.Item
import com.antoniocostadossantos.githubapirepositories.ui.adapter.RepositoriesAdapter
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startFragment
import com.antoniocostadossantos.githubapirepositories.validation.ModelValidation
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var pageIndex = 2
    private lateinit var binding: FragmentHomeBinding
    private lateinit var repositoriesAdapter: RepositoriesAdapter
    private val mainViewModel: MainViewModel by viewModel()
    private val modelValidation = ModelValidation()

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
    }

    private fun paging() {
        val recyclerView = binding.homeRecyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1 && totalItemCount >= 20) {

                    mainViewModel.getAllRepositories(
                        language = "Kotlin",
                        page = pageIndex
                    )
                    observeResult()
                    pageIndex++
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
                    binding.searchLabel.error = null
                    repositoriesAdapter.clearItems()
                    mainViewModel.getAllRepositories(
                        language = searchInput,
                        page = 1
                    )
                    observeResult()
                }
            }
        }
    }

    private fun observeResult() {
        mainViewModel.repositories.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    repositoriesAdapter.updateData(response.data!!.items)
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

    private fun initRecyclerView() {
        repositoriesAdapter = RepositoriesAdapter(requireContext(), itemClick = { repository ->
            showDescription(repository)
        })
        val recyclerView = binding.homeRecyclerView
        recyclerView.adapter = repositoriesAdapter
    }

    private fun showDescription(item: Item) {
        startFragment(DescriptionRepositoryFragment(), Bundle().apply {
            putString("OWNER_REPOSITORY", item.owner.login)
            putString("NAME_REPOSITORY", item.name)
        })
    }

}