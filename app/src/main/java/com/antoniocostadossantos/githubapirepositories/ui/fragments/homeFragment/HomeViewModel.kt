package com.antoniocostadossantos.githubapirepositories.ui.fragments.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.repo.RepositoriesResponse
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.handleRespons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _repositories = MutableLiveData<StateResource<RepositoriesResponse>>()
    val repositories: LiveData<StateResource<RepositoriesResponse>> = _repositories

    fun getAllRepositories(
        language: String,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _repositories.postValue(StateResource.Loading())
        val response = mainRepository.getRepositories(
            language = language,
            page = page
        )
        _repositories.postValue(handleRespons(response))
    }
}