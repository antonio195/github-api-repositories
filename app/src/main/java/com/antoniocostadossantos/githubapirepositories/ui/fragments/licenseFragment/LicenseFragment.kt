package com.antoniocostadossantos.githubapirepositories.ui.fragments.licenseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentLicenseBinding
import com.antoniocostadossantos.githubapirepositories.model.license.LicenseResponse
import com.antoniocostadossantos.githubapirepositories.util.Constants
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.startLink
import org.koin.androidx.viewmodel.ext.android.viewModel

class LicenseFragment : Fragment() {

    private lateinit var binding: FragmentLicenseBinding
    private val licenseViewModel: LicenseViewModel by viewModel()
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

    private fun viewLicense(item: LicenseResponse) {
        binding.licenseTitle.setOnClickListener {
            startLink(item.htmlUrl)
        }
    }

    private fun getLicense() {

        licenseViewModel.getLicense(owner = owner, repositoryName = repositoryName)
        observeResult()
    }

    private fun observeResult() {
        licenseViewModel.repoLicense.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.licenseTitle.visibility = View.VISIBLE
                    binding.licenseTitle.text = response.data?.license?.name
                    viewLicense(response.data!!)
                }

                is StateResource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                is StateResource.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.licenseTitle.visibility = View.GONE
                    binding.noLicenses.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        fun instance(owner: String, repositoryName: String): LicenseFragment {
            return LicenseFragment().apply {
                arguments = bundleOf(
                    Constants.REPOSITORY_OWNER to owner,
                    Constants.REPOSITORY_NAME to owner
                )
            }
        }
    }

}