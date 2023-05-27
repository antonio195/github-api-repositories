package com.antoniocostadossantos.githubapirepositories.ui.fragments.descriptionFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.antoniocostadossantos.githubapirepositories.R
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentDescriptionRepositoryBinding
import com.antoniocostadossantos.githubapirepositories.model.repo.ItemResponse
import com.antoniocostadossantos.githubapirepositories.ui.adapter.ViewPagerAdapter
import com.antoniocostadossantos.githubapirepositories.ui.fragments.commitsFragment.CommitsFragment
import com.antoniocostadossantos.githubapirepositories.ui.fragments.issuesFragment.IssuesFragment
import com.antoniocostadossantos.githubapirepositories.ui.fragments.licenseFragment.LicenseFragment
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionRepositoryFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionRepositoryBinding
    private val descriptionViewModel: DescriptionViewModel by viewModel()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var owner: String
    private lateinit var repositoryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionRepositoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRepository()
        initViewPager()
    }

    private fun initViewPager() {

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewpager

        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            fragments = setDataFragments(owner = owner, repoName = repositoryName)
        )

        viewPager2.adapter = viewPagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    private fun setDataFragments(owner: String, repoName: String): List<Fragment> {
        val fragments = listOf(
            CommitsFragment.instance(owner = owner, repositoryName = repositoryName),
            IssuesFragment.instance(owner = owner, repositoryName = repositoryName),
            LicenseFragment.instance(owner = owner, repositoryName = repositoryName),
        )
        for (i in fragments) {
            i.arguments = Bundle().apply {
                putString("OWNER_REPOSITORY", owner)
                putString("NAME_REPOSITORY", repoName)
            }
        }
        return fragments
    }

    private fun getRepository() {
        owner = arguments?.getString("OWNER_REPOSITORY")!!
        repositoryName = arguments?.getString("NAME_REPOSITORY")!!

        descriptionViewModel.getUserRepository(owner = owner, repositoryName = repositoryName)
        observeResults()
    }

    private fun observeResults() {
        descriptionViewModel.userRepository.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResource.Success -> {
                    setupData(response.data)
                }

                is StateResource.Loading -> {
                }

                is StateResource.Error -> {
                    Log.e("ERRO_REQUEST", response.message.toString())
                }
            }
        }
    }

    private fun setupData(item: ItemResponse?) {
        binding.repositoryImage.load(item?.owner?.avatarUrl)
        binding.repositoryName.text = item?.name
        binding.repositoryNameAndOwner.text = item?.fullName
        binding.repositoryAbout.text = item?.description
        binding.repositoryScore.text =
            getString(R.string.repository_score).format(item?.score)
        binding.repositoryWatchers.text =
            getString(R.string.repository_watchers).format(item?.watchersCount)
        binding.repositoryOpenIssues.text =
            getString(R.string.repository_open_issues).format(item?.openIssuesCount)
        binding.repositoryStars.text =
            getString(R.string.stars).format(item?.starsCount)

    }

}