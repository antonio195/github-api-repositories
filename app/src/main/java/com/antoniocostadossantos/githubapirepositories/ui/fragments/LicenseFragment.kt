package com.antoniocostadossantos.githubapirepositories.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentLicenseBinding
import com.antoniocostadossantos.githubapirepositories.model.license.LicenseRequest
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startLink
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LicenseFragment : Fragment() {

    private lateinit var binding: FragmentLicenseBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var owner: String
    private lateinit var repositoryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLicenseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owner = arguments?.getString("OWNER_REPOSITORY")!!
        repositoryName = arguments?.getString("NAME_REPOSITORY")!!
        getLicense()
    }

    private fun viewLicense(item: LicenseRequest) {
        binding.licenseTitle.setOnClickListener {
            startLink(item.html_url)
        }
    }

    private fun getLicense() {

        mainViewModel.getLicense(owner = owner, repositoryName = repositoryName)
        observeResult()
    }

    private fun observeResult() {
        mainViewModel.repoLicense.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.licenseTitle.visibility = View.VISIBLE
                    binding.licenseTitle.text = response.data?.license?.name
                    viewLicense(response.data!!)
                }

                is StateResource.Loading -> {
                    Toast.makeText(requireContext(), "Carregando", Toast.LENGTH_SHORT).show()
                }

                is StateResource.Error -> {
                    binding.licenseTitle.visibility = View.GONE
                    binding.noLicenses.visibility = View.VISIBLE
                }
            }
        }
    }

}