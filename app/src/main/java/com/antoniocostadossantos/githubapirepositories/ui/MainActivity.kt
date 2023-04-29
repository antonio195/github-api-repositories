package com.antoniocostadossantos.githubapirepositories.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.githubapirepositories.R
import com.antoniocostadossantos.githubapirepositories.databinding.ActivityMainBinding
import com.antoniocostadossantos.githubapirepositories.ui.fragments.HomeFragment
import com.antoniocostadossantos.githubapirepositories.ui.fragments.SearchUserFragment
import com.antoniocostadossantos.githubapirepositories.util.startFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startFragment(HomeFragment())

        binding.mainBottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> startFragment(HomeFragment())
                R.id.search_user_menu -> startFragment(SearchUserFragment())
                else -> {
                    startFragment(HomeFragment())
                }
            }
        }
    }
}