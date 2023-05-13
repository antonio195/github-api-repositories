package com.antoniocostadossantos.githubapirepositories.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentSearchUserBinding
import com.antoniocostadossantos.githubapirepositories.ui.adapter.UsersAdapter
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startLink
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentSearchUserBinding
    private lateinit var usersAdapter: UsersAdapter
    private val mainViewModel: MainViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        searchBar()

    }

    private fun searchBar() {
        binding.searchLabel.setEndIconOnClickListener {
            val searchInput = binding.searchInput.text.toString()
            when (searchInput.isEmpty()) {
                true -> {
                    Toast.makeText(requireContext(), "Digite a linguagem", Toast.LENGTH_SHORT)
                        .show()
                    binding.searchLabel.errorIconDrawable = null
                    binding.searchLabel.error = "Digite a linguagem"
                }

                false -> {
                    binding.searchLabel.error = null
                    mainViewModel.getUser(
                        owner = searchInput
                    )
                    observeResult()
                }
            }
        }
    }

    private fun observeResult() {
        mainViewModel.users.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    usersAdapter.setItems(listOf(response.data!!))
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
        usersAdapter = UsersAdapter(requireContext(), accessUserClick = { user ->
            startLink(user.html_url)
        })
        val recyclerView = binding.usersRecyclerView
        recyclerView.adapter = usersAdapter
    }

}