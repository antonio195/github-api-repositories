package com.antoniocostadossantos.githubapirepositories.di

import android.app.Application
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.viewModel.MainViewModel
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

            modules(
                mainDependency
            )
        }
    }
}

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