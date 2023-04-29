package com.antoniocostadossantos.githubapirepositories.di

import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainDependency = module {

    single {
        MainRepository()
    }

    viewModel {
        MainViewModel(
            mainRepository = get()
        )
    }
}