package com.antoniocostadossantos.githubapirepositories.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.antoniocostadossantos.githubapirepositories.R
import com.antoniocostadossantos.githubapirepositories.databinding.ActivityMainBinding
import com.antoniocostadossantos.githubapirepositories.ui.fragments.HomeFragment
import com.antoniocostadossantos.githubapirepositories.ui.fragments.SearchUserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startNavigation(HomeFragment())

        binding.mainBottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> startNavigation(HomeFragment())
                R.id.search_user_menu -> startNavigation(SearchUserFragment())
                else -> {
                    startNavigation(HomeFragment())
                }
            }
        }
    }

    private fun startNavigation(fragment: Fragment): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        return true
    }
}