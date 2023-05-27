package com.antoniocostadossantos.githubapirepositories.ui.fragments.searchUserFragment

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
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentSearchUserBinding
    private lateinit var usersAdapter: UsersAdapter
    private val searchUserViewModel: SearchUserViewModel by viewModel()


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
        observeResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.usersRecyclerView.adapter = null
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
                    searchUserViewModel.getUser(
                        owner = searchInput
                    )
                }
            }
        }
    }

    private fun observeResult() {
        searchUserViewModel.users.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    usersAdapter.updateData(listOf(response.data!!))
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
        usersAdapter = UsersAdapter(accessUserClick = { user ->
            startLink(user.htmlUrl)
        })
        val recyclerView = binding.usersRecyclerView
        recyclerView.adapter = usersAdapter
    }

}