package com.antoniocostadossantos.githubapirepositories.ui.fragments.descriptionFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniocostadossantos.githubapirepositories.model.repo.ItemResponse
import com.antoniocostadossantos.githubapirepositories.repository.MainRepository
import com.antoniocostadossantos.githubapirepositories.util.StateResource
import com.antoniocostadossantos.githubapirepositories.util.handleRespons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _userRepository = MutableLiveData<StateResource<ItemResponse>>()
    val userRepository: LiveData<StateResource<ItemResponse>> = _userRepository

    fun getUserRepository(
        owner: String,
        repositoryName: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _userRepository.postValue(StateResource.Loading())
        val response = mainRepository.getRepository(
            owner = owner,
            repositoryName = repositoryName
        )
        _userRepository.postValue(handleRespons(response))
    }

}