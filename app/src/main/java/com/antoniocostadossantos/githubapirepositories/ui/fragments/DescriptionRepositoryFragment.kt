package com.antoniocostadossantos.githubapirepositories.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.antoniocostadossantos.githubapirepositories.R
import com.antoniocostadossantos.githubapirepositories.databinding.FragmentDescriptionRepositoryBinding
import com.antoniocostadossantos.githubapirepositories.model.repo.Item
import com.antoniocostadossantos.githubapirepositories.ui.adapter.ViewPagerAdapter
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionRepositoryFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionRepositoryBinding
    private val mainViewModel: MainViewModel by viewModel()
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

        setData()
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
            CommitsFragment(),
            IssuesFragment(),
            LicenseFragment()
        )
        for (i in fragments) {
            i.arguments = Bundle().apply {
                putString("OWNER_REPOSITORY", owner)
                putString("NAME_REPOSITORY", repoName)
            }
        }
        return fragments
    }

    private fun setData() {
        owner = arguments?.getString("OWNER_REPOSITORY")!!
        repositoryName = arguments?.getString("NAME_REPOSITORY")!!

        mainViewModel.getUserRepository(owner = owner, repositoryName = repositoryName)
        getRepository()
    }

    private fun getRepository() {
        mainViewModel.userRepository.observe(requireActivity()) { response ->
            when (response) {
                is StateResource.Success -> {
                    setupData(response.data)
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

    private fun setupData(item: Item?) {
        binding.repositoryImage.load(item?.owner?.avatar_url)
        binding.repositoryName.text = item?.name
        binding.repositoryNameAndOwner.text = item?.full_name
        binding.repositoryAbout.text = item?.description
        binding.repositoryScore.text =
            getString(R.string.repository_score).format(item?.score)
        binding.repositoryWatchers.text =
            getString(R.string.repository_watchers).format(item?.watchers_count)
        binding.repositoryOpenIssues.text =
            getString(R.string.repository_open_issues).format(item?.open_issues_count)
        binding.repositoryStars.text =
            getString(R.string.stars).format(item?.stargazers_count)
    }

}