package com.antoniocostadossantos.githubapirepositories.di

import android.app.Application
import com.antoniocostadossantos.githubapirepositories.remote.RetrofitClient
import com.antoniocostadossantos.githubapirepositories.remote.api.GithubAPI
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.ui.fragments.commitsFragment.CommitsViewModel
import com.antoniocostadossantos.githubapirepositories.ui.fragments.descriptionFragment.DescriptionViewModel
import com.antoniocostadossantos.githubapirepositories.ui.fragments.homeFragment.HomeViewModel
import com.antoniocostadossantos.githubapirepositories.ui.fragments.issuesFragment.IssuesViewModel
import com.antoniocostadossantos.githubapirepositories.ui.fragments.licenseFragment.LicenseViewModel
import com.antoniocostadossantos.githubapirepositories.ui.fragments.searchUserFragment.SearchUserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(mainDependency)
        }
    }
}

val mainDependency = module {

    single { RetrofitClient.getRetrofitInstance() }
    single { RetrofitClient.getService(GithubAPI::class.java, get()) }
    single { MainRepository(githubAPI = get()) }

    viewModel { CommitsViewModel(mainRepository = get()) }
    viewModel { DescriptionViewModel(mainRepository = get()) }
    viewModel { HomeViewModel(mainRepository = get()) }
    viewModel { IssuesViewModel(mainRepository = get()) }
    viewModel { LicenseViewModel(mainRepository = get()) }
    viewModel { SearchUserViewModel(mainRepository = get()) }
}